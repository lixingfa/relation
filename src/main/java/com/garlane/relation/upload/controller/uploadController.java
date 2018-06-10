package com.garlane.relation.upload.controller;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.garlane.relation.common.constant.ConfigConstant;
import com.garlane.relation.common.controller.BaseController;
import com.garlane.relation.common.model.result.ResultModel;
import com.garlane.relation.upload.service.UploadService;

/**
 * 对或错判断
 * @author lixingfa
 * @date 2017年11月29日下午5:55:27
 *
 */
@Controller
@RequestMapping("upload")
public class uploadController extends BaseController {
	@Autowired
	private UploadService uploadService;
		
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
	
	/**
	 * 上传项目
	 * @param files
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("upload")
	@ResponseBody
	public ResultModel upload(@RequestParam("multipartFile") MultipartFile[] files) throws IOException {
		ResultModel result = new ResultModel();
		try {
			String path = uploadService.upload(files);
			result.setSuccessful(true);
			result.setData(path);
			result.setMessage("原型页面上传成功！正在解析项目，请稍后……");
		} catch (Exception e) {
			result.setSuccessful(false);
			result.setMessage(e.getMessage());
		}
		return result;
    }
}
