package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/seckill") //url:/模块/资源/{id}/细分  /seckill/list
public class SeckillController {

	//日志的加载
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;

	/**
	 * 获取列表页 /seckill/list
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method= RequestMethod.GET)
	public String list(Model model){
		//list.jsp  +model  = ModelAndView
		List<Seckill> list =seckillService.getSeckillList();
		model.addAttribute("seckillList", list);
		return "/list";//list.jsp
	}
	/*
	 * 详情
	 */
	@RequestMapping(value="/{seckillId}/detail",method= RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Integer seckillId,Model model){
		if(seckillId == null){
			return "redirect:/seckill/list";//重定向
		}
		Seckill seckill = seckillService.getById(seckillId);
		if(seckill==null){
			return "forward:/seckill/list";//转发
		}
		model.addAttribute("seckill", seckill);
		return "/detail";
	}


	//ajax接口  返回是json
	@RequestMapping(value="{seckillId}/exposer",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody//看到这个注解 会将实体封装成json格式
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Integer seckillId){
		SeckillResult<Exposer> result ;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true,exposer);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result = new SeckillResult<Exposer>(false,e.getMessage());
		}
		System.out.println("~~~~~~~~~~"+result);
		return result;
	}

	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param md5
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/{md5}/execution",
			method=RequestMethod.POST,
			produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable Integer seckillId,
												   @PathVariable("md5") String md5,
												   @CookieValue(value="killPhone",required=false) Long phone){
		SeckillResult<SeckillExecution> result ;
		if(phone==null){
			return new SeckillResult<SeckillExecution>(false,"未注册");
		}
		try {
			SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch (RepeatKillException e) {//重复秒杀异常
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch (SeckillCloseException e) {//秒杀关闭 结束了异常
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true,execution);
		}catch (SeckillException e) {//秒杀通用异常
			logger.error(e.getMessage());
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true,execution);
		}
	}

	/**
	 * 获取系统时间
	 */
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public  SeckillResult<Long> time (){
		Date now = new Date();
		return new SeckillResult(true,now.getTime());
	}

}
