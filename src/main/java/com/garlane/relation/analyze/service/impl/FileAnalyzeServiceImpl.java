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

import com.garlane.relation.analyze.service.FileAnalyzeService;
import com.garlane.relation.common.constant.FileConstant;
import com.garlane.relation.common.constant.PageConstant;
import com.garlane.relation.common.model.page.AModel;
import com.garlane.relation.common.model.page.FormModel;
import com.garlane.relation.common.model.page.InputModel;
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
	
	private static final String JS_PATH = "static/js/";
	private static final String BLPat = "<!---[^(-->)]+-->";
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
				htmlAnalyzes(htmlContents);
				log.info("解析html文件里的业务语言");
				
				log.info("读取js数据");
				htmlContents.putAll(FileUtils.getDirectoryContent(path + "/" + JS_PATH,FileConstant.JS));
				log.info("读取数据完成，开始解析。");
				
			} catch (Exception e) {
				throw new SuperServiceException("读取文件数据出现错误", e);
			}
		}else {
			throw new SuperServiceException(path + "不是文件夹！");
		}
	}
	
	
	private void htmlAnalyzes(Map<String, String> htmlContents){
		for (String path : htmlContents.keySet()) {
			String content = htmlContents.get(path);
			htmlAnalyze(content);
		}
	}
	
	private void htmlAnalyze(String content){
		//这个方法应该是产出业务逻辑模型了
		try {
			Document doc = Jsoup.parse(content);
			List<FormModel> formModels = new ArrayList<FormModel>();
			List<AModel> aModels = new ArrayList<AModel>();
			//处理表单
			Elements forms = doc.select("form");
			for (Element form : forms) {
				FormModel formModel = new FormModel();
				Elements inputs = form.select("input");
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
			//获取html里的业务语言
			Map<String, Integer> BLs = new HashMap<String, Integer>();
			Pattern pattern = Pattern.compile(BLPat);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				String bl = matcher.group();
				BLs.put(bl,content.indexOf(bl));		
			}
			//处理业务语言 TODO 待优化
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String s = "立即注册</a><!---进入注册页，填写公司名称、法人信息、登录用户名、密码、注册邮箱，选择供应商还是采购商，上传公司资质-->>登录</a><!---验证码、账号、密码均正确-->";
		Pattern pattern = Pattern.compile(BLPat);
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String string = matcher.group();
			System.out.println(string);			
		}
	}
}
