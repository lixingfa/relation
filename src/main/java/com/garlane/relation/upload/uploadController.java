package com.garlane.relation.upload;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.garlane.relation.common.constant.ConfigConstant;
import com.garlane.relation.common.controller.BaseController;

/**
 * 对或错判断
 * @author lixingfa
 * @date 2017年11月29日下午5:55:27
 *
 */
@Controller
@RequestMapping("upload")
public class uploadController extends BaseController {
		
	/**
	 * toUpload:(到上传界面)
	 * @author lixingfa
	 * @date 2017年11月29日下午6:24:25
	 * @param request
	 * @return
	 */
	@RequestMapping("toUpload")
	public ModelAndView toUpload(HttpServletRequest request) {
		try {
			logger.info("跳转到上传界面");			
			ModelAndView modelAndView = new ModelAndView("upload/upload");
			modelAndView.addObject("uploadFileMaxSize",ConfigConstant.UPLOADFILE_MAX_SIZE );
			modelAndView.addObject("uploadFileMaxNum",ConfigConstant.UPLOADFILE_MAX_NUM );
			return modelAndView;
		} catch (Exception e) {
			logger.error("跳转失败", e);
			return errorModelAndView();
		}
	}
}
