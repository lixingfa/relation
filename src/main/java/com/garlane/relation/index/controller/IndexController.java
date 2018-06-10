package com.garlane.relation.index.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.garlane.relation.common.controller.BaseController;

/**
 * 对或错判断
 * @author lixingfa
 * @date 2017年11月29日下午5:55:27
 *
 */
@Controller
@RequestMapping("index")
public class IndexController extends BaseController {
		
	/**
	 * 
	 * @author lixingfa
	 * @date 2017年11月29日下午6:24:25
	 * @param request
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index(HttpServletRequest request) {
		try {
			logger.info("跳转界面");
			ModelAndView modelAndView = new ModelAndView("index");
			return modelAndView;
		} catch (Exception e) {
			logger.error("跳转失败", e);
			return errorModelAndView();
		}
	}
}
