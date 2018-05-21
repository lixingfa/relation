package com.garlane.relation.common.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.garlane.relation.common.constant.ConfigConstant;


/**
 *	基础控制类
 *	@author hefule
 *	@date 2017年1月12日 上午12:48:10
 *
 */
@ControllerAdvice
public class BaseController{

	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 *	错误时返回500页面
	 *	@author hefule
	 *	@date 2017年4月27日 下午5:11:39
	 *
	 */
	protected ModelAndView errorModelAndView(){
		return new ModelAndView(ConfigConstant.ERRORPAGE);
	}
	
	/**
	 * 增加统一controller处理
	 * @author hefule
	 * @date 2017年6月7日 下午3:10:59
	 *
	 */
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(Exception e) {
		logger.error("全局controller层捕获访问异常：",e);
		/*Map<String,Object> model = new HashMap<String,Object>();
		model.put("status",HttpStatus.BAD_REQUEST);
		model.put("error", e.getLocalizedMessage());
		return new ResponseEntity<Object>(model,HttpStatus.OK);*/
	}
	
}
