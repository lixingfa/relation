/**
 * 
 */
package com.garlane.relation.analyze.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.AModel;
import com.garlane.relation.analyze.model.BLModel;
import com.garlane.relation.analyze.model.FormModel;
import com.garlane.relation.analyze.model.HTMLModel;
import com.garlane.relation.analyze.model.InputModel;
import com.garlane.relation.analyze.service.FileAnalyzeService;
import com.garlane.relation.common.constant.FileConstant;
import com.garlane.relation.common.constant.PageConstant;
import com.garlane.relation.common.utils.exception.SuperServiceException;
import com.garlane.relation.common.utils.file.FileUtils;

/**
 * @author lixingfa
 * @date 2018年5月28日下午7:16:54
 * 
 */
@Service("fileAnalyzeService")
public class FileAnalyzeServiceImpl implements FileAnalyzeService {

	private static final Logger log = Logger.getLogger(FileAnalyzeServiceImpl.class);
	
	private static final String JSSRC = "<script [ \"/a-zA-Z=.]+ [ \"/a-zA-Z=.]+></script>";
	private static final String HTMLBL = "<!---[^(-->)]+-->";
	private static final String JSBL = "/\\*\\*\\*[^(\\*\\*/)]+\\*\\*/";
	/**
	 * getFileLogic:(获取项目业务逻辑)
	 * @author lixingfa
	 * @date 2018年5月28日下午7:19:41
	 * @param path 项目静态页面路径
	 * @throws SuperServiceException
	 */
	public void getFileLogic(String path) throws SuperServiceException{
		File prototype = new File(path);
		if (prototype.isDirectory()) {
			try {
				log.info("读取html数据");
				Map<String, String> htmlContents = FileUtils.getDirectoryContent(path + "/" + FileConstant.HTML + "/",FileConstant.HTML);
				List<HTMLModel> htmlModels = htmlAnalyzes(htmlContents);			
				log.info("解析HTML里引用的js");
				
				
			} catch (Exception e) {
				throw new SuperServiceException("读取文件数据出现错误", e);
			}
		}else {
			throw new SuperServiceException(path + "不是文件夹！");
		}
	}
	
	/**
	 * htmlAnalyzes:(HTML页面分析)
	 * @author lixingfa
	 * @date 2018年6月13日下午7:31:59
	 * @param htmlContents
	 */
	private List<HTMLModel> htmlAnalyzes(Map<String, String> htmlContents){
		List<HTMLModel> htmlModels = new ArrayList<HTMLModel>();
		for (String path : htmlContents.keySet()) {
			String content = htmlContents.get(path);
			htmlModels.add(htmlAnalyze(path,content));
		}
		return htmlModels;
	}
	
	/**
	 * htmlAnalyze:(分析单个html页面)
	 * @author lixingfa
	 * @date 2018年6月13日下午7:32:22
	 * @param content
	 */
	private HTMLModel htmlAnalyze(String path,String content){
		HTMLModel htmlModel = new HTMLModel(path);
		try {
			Document doc = Jsoup.parse(content);
			List<FormModel> formModels = new ArrayList<FormModel>();
			List<AModel> aModels = new ArrayList<AModel>();
			List<BLModel> blModels = new ArrayList<BLModel>();
			log.info("处理表单");
			Elements forms = doc.select(PageConstant.FORM);
			for (Element form : forms) {
				FormModel formModel = new FormModel();
				Elements inputs = form.select(PageConstant.INPUT);
				for (Element input : inputs) {
					InputModel inputModel = new InputModel();
					inputModel.setId(input.attr(PageConstant.ID));
					inputModel.setMaxlength(input.attr(PageConstant.MAXLENGTH));
					inputModel.setName(input.attr(PageConstant.NAME));
					inputModel.setPlaceholder(input.attr(PageConstant.PLACEHOLDER));
					inputModel.setValue(input.attr(PageConstant.VALUE));
					formModel.getInputs().add(inputModel);
				}
				formModels.add(formModel);
			}
			htmlModel.setFormModels(formModels);
			log.info("处理bl标签标注的动态内容");
			Elements bls = doc.getElementsByAttribute("bl");
			for (Element bl : bls) {
				blModels.add(new BLModel(bl.attr("bl"), bl.text()));
			}			
			log.info("获取html里的业务语言");
			Map<String, Integer> BLs = new HashMap<String, Integer>();
			Pattern pattern = Pattern.compile(HTMLBL);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				String bl = matcher.group();
				BLs.put(bl,content.indexOf(bl));		
			}
			blModels.addAll(htmlJsAnalyze(content));
			htmlModel.setBls(blModels);
			log.info("a标签与业务语言关联");
			Elements as = doc.select("a");
			for (Element a : as) {
				AModel aModel = new AModel(a.attr(PageConstant.HREF),a.text());
				String outerHtml = a.outerHtml();
				int i = content.indexOf(outerHtml);
				for (String key : BLs.keySet()) {
					if (BLs.get(key) - (i + outerHtml.length()) <= 2) {
						aModel.setBL(key);//业务语言与链接关联
						BLs.remove(key);
						break;
					}
				}
				aModels.add(aModel);
			}
			htmlModel.setaModels(aModels);
			log.info("获取js引用");
			pattern = Pattern.compile(JSSRC);
			matcher = pattern.matcher(content);
			while (matcher.find()) {
				String string = matcher.group();
				htmlModel.getJsSrc().add(string);
			}
			return htmlModel;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("处理文件出错。", e);
			throw new SuperServiceException("处理文件出错。", e);
		}
	}
	
	/**
	 * 处理html中JS脚本的业务语言
	 * @param content
	 * @return
	 */
	private List<BLModel> htmlJsAnalyze(String content){
		List<BLModel> blModels = new ArrayList<BLModel>();
		Pattern pattern = Pattern.compile(JSBL);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String string = matcher.group();
			blModels.add(new BLModel(null, string));
		}
		return blModels;
	}
}
