package com.garlane.relation.analyze.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garlane.relation.analyze.service.FileAnalyzeService;
import com.garlane.relation.common.controller.BaseController;
import com.garlane.relation.common.model.result.ResultModel;

/**
 * 对或错判断
 * @author lixingfa
 * @date 2017年11月29日下午5:55:27
 *
 */
@Controller
@RequestMapping("fileAnalyze")
public class FileAnalyzeController extends BaseController {
	
	@Autowired
	private FileAnalyzeService fileAnalyzeService;
		
	/**
	 * getFileLogic:(获取项目业务逻辑)
	 * @author lixingfa
	 * @date 2017年11月29日下午6:24:25
	 * @param request
	 * @return
	 */
	@RequestMapping("getFileLogic")
	@ResponseBody
	public ResultModel getFileLogic(String path) {
		try {
			logger.info("解析项目的逻辑。");
			fileAnalyzeService.getFileLogic("E:/WorkSpase/relation/ApprBase/webapp");
			return new ResultModel(true, "解析项目逻辑成功，即将进行组合优化……",null);
		} catch (Exception e) {
			logger.error("跳转失败", e);
			return new ResultModel(false, "解析项目逻辑失败！", e);
		}
	}
}
