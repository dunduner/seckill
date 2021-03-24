package com.seazen.productservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seazen.productservice.entity.ActiveRequest;
import com.seazen.productservice.entity.Approval;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/json")
public class JsonController {

    //localhost:2100/json/active
    //方式一：直接返回的参数 是实体类，用RequestBody接
    @ResponseBody
    @RequestMapping(value = "/active", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ActiveRequest active(@RequestBody ActiveRequest request) {
        System.out.println(request.toString());
        return request;
    }

    /**
     * 功能描述:通过request的方式来获取到json数据<br/>
     * JSONObject 这个是阿里的 fastjson对象
     * @RequestBody JSONObject jsonParam 直接接收，不需要任何实体类封装
     * {"sysCode":"ETS","processNo":"154064418","processTitle":"【合同付款】11\\12月生活垃圾付款","processTypeName":"合同付款","processState":"审批中","applyLoginName":"zhangmengxi","applyName":"张梦曦","applyDay":"2020-03-18","isEnd":"0","accessUrl":"http://budget.xinchengyue.com","pcAccessUrl":"http://budget.xinchengyue.com","approvedAccessUrl":"http://budget.xinchengyue.com","approvedPcAccessUrl":"http://budget.xinchengyue.com","approvals":[{"nodeName":"111分公司总经理","loginName":"wangyi4","name":"王义"},{"nodeName":"222分公司总经理","loginName":"wangyi4","name":"王义"}]}
     *
     */
    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getByJSON(@RequestBody JSONObject jsonParam) {
        // 直接将json信息打印出来
        System.out.println("直接将json信息打印出来"+jsonParam.toJSONString());

        JSONArray approvals = jsonParam.getJSONArray("approvals");
        for (int i = 0; i < approvals.size(); i++) {
            Approval approval = approvals.getObject(i, Approval.class);
            System.out.println("approval.toString():"+approval.toString());
        }
        
        System.out.println("approvals[0]"+approvals.get(0).toString());
        System.out.println("applyName:"+jsonParam.get("applyName"));
        // 将获取的json数据封装一层，然后在给返回
        JSONObject result = new JSONObject();
        result.put("msg", "ok");
        result.put("method", "json");
        result.put("result", "返回的结果");
        result.put("data", jsonParam);

        return result.toJSONString();
    }


}