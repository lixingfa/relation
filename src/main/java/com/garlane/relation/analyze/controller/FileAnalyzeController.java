package com.garlane.relation.analyze.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.garlane.relation.common.controller.BaseController;
import com.garlane.relation.common.model.page.PageDataModel;
import com.garlane.relation.common.model.page.PageModel;
import com.garlane.relation.common.model.result.ResultModel;
import com.garlane.relation.common.model.tree.WebConfigTreeModel;
import com.garlane.relation.truefalse.model.TrueFalseModel;
import com.garlane.relation.truefalse.model.WebConfigConditionModel;
import com.garlane.relation.truefalse.service.TrueFalseService;
import com.garlane.relation.truefalse.service.WebConfigConditionService;

/**
 * 对或错判断
 * @author lixingfa
 * @date 2017年11月29日下午5:55:27
 *
 */
@Controller
@RequestMapping("fileAnalyze")
public class FileAnalyzeController extends BaseController {
		
	/**
	 * toUpload:(到上传界面)
	 * @author lixingfa
	 * @date 2017年11月29日下午6:24:25
	 * @param request
	 * @return
	 */
	@RequestMapping("getFileLogic")
	@ResponseBody
	public void getFileLogic(String path) {
		try {
			logger.info("跳转到上传界面");
			
		} catch (Exception e) {
			logger.error("跳转失败", e);
			
		}
	}
}