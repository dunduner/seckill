package cn.yunhe.seckill.controller;

import cn.yunhe.seckill.dto.Exposer;
import cn.yunhe.seckill.dto.SeckillExecution;
import cn.yunhe.seckill.dto.SeckillResult;
import cn.yunhe.seckill.entity.Seckill;
import cn.yunhe.seckill.enumpack.SeckillStateEnum;
import cn.yunhe.seckill.exception.RepeatKillException;
import cn.yunhe.seckill.exception.SeckillCloseException;
import cn.yunhe.seckill.exception.SeckillException;
import cn.yunhe.seckill.service.SeckillService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    // 日志的加载
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    /**
     * 获取列表页 http://localhost:8080/seckill/seckill/list
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        // list.jsp +model = ModelAndView
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("seckillList", list);
        return "list";// WEB-INF/jsp/list.jsp
    }

    /**
     * 根据id展示详情
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(Model model, @PathVariable("seckillId") Integer seckillId) {
        if (seckillId == null) {
            return "redirect:/seckill/list";// 重定向 是url请求路径
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";// 转发oneFileUploadToFastDfs
        }
        model.addAttribute("seckill", seckill);
        return "detail";// WEB-INF/jsp/detail.jsp
    }

    /**
     * http://localhost:8080/seckill/seckill/1/exposer
     *
     * @param seckillId
     * @return
     */
    // ajax接口 返回是json
    // 秒杀开启时候 。输出秒杀接口的地址 否则输出系统时间和秒杀时间
    @RequestMapping(value = "{seckillId}/exposer", produces = {"application/json;charset=UTF-8"})
    @ResponseBody // 看到这个注解 会将实体封装成json格式
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Integer seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        System.out.println("~~~~~~~~~~" + result);
        return result;
    }

    /**
     * 执行秒杀
     *
     * @param seckillId
     * @param md5
     * @param phone
     * @return method=RequestMethod.POST
     * @CookieValue(value="killPhone",required=false) Long phone
     * http://localhost:8080/seckill/seckill/2/9a369e7bce7097ae2f9c097b66cdfc01/execution
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execution(@PathVariable Integer seckillId, @PathVariable("md5") String md5,
                                                     @CookieValue(value = "killPhoneKey", required = false) Long phone) {
        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册手机号");
        }
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, 13623865089l, md5);
            // 调用存储过程
//			SeckillExecution execution = seckillService.executeSeckillByProcedure(seckillId, 13623865089l, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false, execution);
        } catch (SeckillCloseException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(false, execution);
        } catch (SeckillException e) {
            logger.error(e.getMessage());
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false, execution);
        }
    }

    // 获取系统时间 http://localhost:8080/seckill/seckill/time
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> execution() {
        Date date = new Date();
        long time = date.getTime();
        return new SeckillResult<Long>(true, time);
    }

    /**
     * 功能描述:通过request的方式来获取到json数据<br/>
     * JSONObject 这个是阿里的 fastjson对象
     *
     * @RequestBody JSONObject jsonParam 直接接收，不需要任何实体类封装
     * {"sysCode":"ETS","processNo":"154064418","processTitle":"【合同付款】11\\12月生活垃圾付款","processTypeName":"合同付款","processState":"审批中","applyLoginName":"zhangmengxi","applyName":"张梦曦","applyDay":"2020-03-18","isEnd":"0","accessUrl":"http://budget.xinchengyue.com","pcAccessUrl":"http://budget.xinchengyue.com","approvedAccessUrl":"http://budget.xinchengyue.com","approvedPcAccessUrl":"http://budget.xinchengyue.com","approvals":[{"nodeName":"111分公司总经理","loginName":"wangyi4","name":"王义"},{"nodeName":"222分公司总经理","loginName":"wangyi4","name":"王义"}]}
     */
    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getByJSON(@RequestBody JSONObject jsonParam) {
        // 直接将json信息打印出来
        System.out.println("直接将json信息打印出来" + jsonParam.toJSONString());

        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("method", "json");
        result.put("result", "返回的结果");
        result.put("data", jsonParam);

        return result.toJSONString();
    }

    /**
     * 单图片上传
     *
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/onefileupload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createArticle(@RequestParam(value = "file", required = false) MultipartFile file,
                                             HttpServletRequest request) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        // 返回信息集合
        Map<String, Object> resultMap = new HashMap<String, Object>();

        if (!file.isEmpty()) {
            //拿到file
            String path = request.getSession().getServletContext().getRealPath("/upload");
            // 定义文件
            File parent = new File(path);
            if (!parent.exists()) {
                parent.mkdirs();//路径不存在就创建
            }
            // 获取文件名
            String oldName = file.getOriginalFilename();
            long size = file.getSize();
            // 获取图片后缀
            String extName = oldName.substring(oldName.lastIndexOf("."));
            String picName = UUID.randomUUID().toString();
            String newFileName = picName + extName;
            String url = "upload/" + picName + extName;

            // 文件传输，parent文件
            file.transferTo(new File(parent, newFileName));

            resultMap.put("result", true);
            Map<String, Object> resImg = new HashMap<String, Object>();
            resImg.put("filename", oldName);
            resImg.put("newFileName", newFileName);
            resImg.put("filesize", size);
            resImg.put("fileUrl", url);
            resultMap.put("data", resImg);
            resultMap.put("isSuccess", true);

        } else {
            return null;
        }

        return resultMap;
    }



    /**
     * 多文件上传
     *
     * @param files
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mutlUpload", method = RequestMethod.POST)
    @ResponseBody
    public static List<HashMap<String, Object>> mutlUpload(@RequestParam(value = "doc", required = false) MultipartFile[] files, HttpServletRequest request) throws Exception {
        System.out.println("多文件上传");
        if (files.length > 0) {
            String path = request.getSession().getServletContext().getRealPath("/upload");
            // 定义文件
            File parent = new File(path);
            if (!parent.exists())
                parent.mkdirs();

            // 创建这个集合保存所有文件的信息
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

            // 循环多次上传多个文件
            for (MultipartFile file : files) {

                // 创建map对象保存每一个文件的信息
                HashMap<String, Object> map = new HashMap<String, Object>();

                String oldName = file.getOriginalFilename();

                long size = file.getSize();

                // 使用TmFileUtil文件上传工具获取文件的各种信息
                // 优化文件大小
//				String sizeString = TmFileUtil.countFileSize(size);
                // 获取文件后缀名
//				String ext = TmFileUtil.getExtNoPoint(oldName);
                // 随机重命名，10位时间字符串
//				String newFileName = TmFileUtil.generateFileName(oldName, 10, "yyyyMMddHHmmss");
                // 获取图片后缀
                String extName = oldName.substring(oldName.lastIndexOf("."));
                String picName = UUID.randomUUID().toString();
                String newFileName = picName + extName;
                String url = "upload/" + picName + extName;

                // 文件传输，parent文件
                file.transferTo(new File(parent, newFileName));

                map.put("oldname", oldName);// 文件原名称
                map.put("name", newFileName);// 文件新名称
                map.put("url", url);
                map.put("size", size);

                listMap.add(map);
            }

            // 以json方式输出到页面
            return listMap;
        } else {
            return null;
        }
    }

    //普通文件下载
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(String fileName, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/upload");
        File file = new File(path + "/" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

}
