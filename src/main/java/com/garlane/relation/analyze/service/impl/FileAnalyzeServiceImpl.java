/**
 * 
 */
package com.garlane.relation.analyze.service.impl;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.service.FileAnalyzeService;
import com.garlane.relation.common.constant.FileConstant;
import com.garlane.relation.common.utils.exception.SuperServiceException;
import com.garlane.relation.common.utils.file.FilePathUtil;
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
		try {
			Document doc = Jsoup.parse(content);
			Elements forms = doc.select("form");
			
			Elements inputs = forms.select("input");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
