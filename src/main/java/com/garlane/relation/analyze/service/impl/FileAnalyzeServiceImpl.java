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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.page.AModel;
import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.FormModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.model.page.InputModel;
import com.garlane.relation.analyze.service.FileAnalyzeService;
import com.garlane.relation.analyze.service.LogicAnalyzeService;
import com.garlane.relation.common.constant.ConfigConstant;
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
	@Autowired
	private LogicAnalyzeService logicAnalyzeService;
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
				int htmlCount = 0;
				int jspCount = 0;
				int jsCount = 0;
				//TODO MVC实时查看日志
				Map<String, String> htmlContents = new HashMap<String, String>();
				String htmlPath;
				if (path.endsWith(FileConstant.WEBAPP)) {//基于maven的老项目，把webapp目录拿过来
					String jspPath = path + File.separator + FileConstant.WEBINF + File.separator + FileConstant.JSP + File.separator;
					log.info("读取jsp数据：" + jspPath);
					//老项目也能通过原有JSP+BL+html实现项目拓展，目前只支持maven格式的
					htmlContents.putAll(FileUtils.getDirectoryContent(jspPath,FileConstant.JSP));
					jspCount = htmlContents.size();
					htmlPath = jspPath;//每次只读取一种文件类型，jsp里可能新加入html
				}else {//新项目
					htmlPath = path + File.separator + FileConstant.HTML + File.separator;
				}
				log.info("读取html数据:" + htmlPath);
				htmlContents.putAll(FileUtils.getDirectoryContent(htmlPath,FileConstant.HTML));
				htmlCount = htmlContents.size() - jspCount;					
				
				//TODO 敏感信息监控，报警，并记录上传人的ip、身份证等
				
				
				String jsPath = path + File.separator + FileConstant.STATIC + File.separator + FileConstant.JS + File.separator;
				log.info("解析" + jsPath + "下的JS文件，非业务JS不要放在这里。" );
				Map<String, String> jsContents = FileUtils.getDirectoryContent(jsPath,FileConstant.JS);
				jsCount = jsContents.size();
				if (htmlCount + jspCount + jsCount > ConfigConstant.ANALYZE_MAX_NUM) {
					throw new SuperServiceException("html页面总数("+ htmlCount +")+jsp页面总数("+ jspCount
							+")+js文件总数("+ jsCount +")  > " + ConfigConstant.ANALYZE_MAX_NUM
							+"<br/>请合理安排文件存放位置，不要把前端框架的文件放到js目录下。" );
				}
				
				List<BLModel> jsBLModels = new ArrayList<BLModel>();
				for (String key : jsContents.keySet()) {
					jsBLModels.addAll(jsAnalyze(jsContents.get(key)));
				}
				List<HTMLModel> htmlModels = htmlAnalyzes(htmlContents);
				log.info("开始分析业务逻辑");
				logicAnalyzeService.LogicAnalyze(htmlModels, jsBLModels);
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
			//TODO 处理EASYUI
			
			Document doc = Jsoup.parse(content);
			log.info("处理表格");
			//form里也会包含表格，不过不会是EASYUI的datagrid
			//TODO EASYUI处理
			
			
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
			log.info("获取HTML里JS脚本的BL语言。");
			blModels.addAll(jsAnalyze(content));
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
				String s = matcher.group();
				s = s.substring(s.indexOf(PageConstant.SRC));
				s = s.substring(0, s.indexOf("\""));
				htmlModel.getJsSrc().add(s);
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
	private List<BLModel> jsAnalyze(String content){
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
