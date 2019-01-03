/**
 * 
 */
package com.garlane.relation.analyze.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.analyze.model.page.AModel;
import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.FormModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.model.page.InputModel;
import com.garlane.relation.analyze.model.page.SelectModel;
import com.garlane.relation.analyze.model.page.TableModel;
import com.garlane.relation.analyze.model.page.TextareaModel;
import com.garlane.relation.analyze.service.EASYUIAnalyzeService;
import com.garlane.relation.analyze.service.ELAnalyzeService;
import com.garlane.relation.analyze.service.FileAnalyzeService;
import com.garlane.relation.analyze.service.LogicAnalyzeService;
import com.garlane.relation.common.constant.ConfigConstant;
import com.garlane.relation.common.constant.FileConstant;
import com.garlane.relation.common.constant.PageConstant;
import com.garlane.relation.common.constant.PageConstant.INPUTTYPE;
import com.garlane.relation.common.constant.RegularConstant;
import com.garlane.relation.common.utils.change.StringUtil;
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
	@Autowired
	private ELAnalyzeService elAnalyzeService;
	@Autowired
	private EASYUIAnalyzeService easyuiAnalyzeService;
	/**
	 * getFileLogic:(解析项目)
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
				//TODO 兼容小程序，这是一个风口，以人为维度的个性化，就从小程序开始，是入口，也是风口，要快
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
				//获取完页面所有信息后，开始对信息进行逻辑处理
				log.info("开始分析业务逻辑");
				logicAnalyzeService.LogicAnalyze(htmlModels, jsBLModels);
				System.out.println("输出html页面模型");
				for (HTMLModel htmlModel : htmlModels) {
					String modelString = JSONObject.toJSONString(htmlModel);
					path = htmlModel.getPath();
					path = path.substring(path.indexOf("\\jsp"));
					System.out.println(path);
					//格式化，便于阅读
					modelString = StringUtil.getJsonFormat(modelString);
					//将对象写入文本，对比提前结果
					FileUtils.writeTxtFile(modelString, "E:/htmlModels/" + path + ".txt");
				}				
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
			log.info("解析页面：" + path);
			if (path.endsWith("unitEdit.jsp")) {
				System.out.println();
			}
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
			log.info("处理el表达式");
			//每一种类型的处理都是相对分开的，避免相互干扰，尽管有重叠的处理动作
			htmlModel.setElModels(elAnalyzeService.analyze(content));
			//处理EASYUI
			htmlModel.setEasyuiModel(easyuiAnalyzeService.getEasyuiModel(content));
			//清理JSTL，避免对TML解析产生影响
			content = StringUtil.replaceMatchers(RegularConstant.JSTL, "", content);
			//HTML解析
			Document doc = Jsoup.parse(content);
			log.info("处理表格");
			Elements tables = doc.select(PageConstant.TABLE);
			if (tables.size() > 0) {
				List<TableModel> tableModels = new ArrayList<TableModel>();
				for (Element table : tables) {
					Elements pElements = table.parents();
					for (Element p : pElements) {
						if (p.tagName().equals(PageConstant.FORM)) {
							continue;//不处理包含在表单内的
						}					
					}
					//处理table
					TableModel tableModel = new TableModel();
					tableModel.setInputs(getInputModels(table));
					tableModel.setSelectModels(getSelectModels(table));
					tableModel.setTextareaModels(getTextareaModels(table));
					tableModels.add(tableModel);
				}
				htmlModel.setTableModels(tableModels);
			}
			log.info("处理表单");
			Elements forms = doc.select(PageConstant.FORM);
			if (forms.size() > 0) {
				List<FormModel> formModels = new ArrayList<FormModel>();
				
				for (Element form : forms) {
					String url = form.attr(PageConstant.ACTION);
					if (url == null) {
						//本体没有就只能去ajaxSubmit之类的找了
						String id = form.attr(PageConstant.ID);
						//$("#submitForm").ajaxSubmit({和easyui的两种需要考虑
						url = getFormUrl(id, content);
						//如果连这个都没有，就是走了AJAX，后面会处理
					}
					FormModel formModel = new FormModel();
					formModel.setUrl(url);
					formModel.setInputs(getInputModels(form));
					formModel.setSelectModels(getSelectModels(form));
					formModel.setTextareaModels(getTextareaModels(form));
					formModels.add(formModel);
				}
				htmlModel.setFormModels(formModels);
			}
			
			log.info("处理bl标签标注的动态内容");
			Elements bls = doc.getElementsByAttribute("bl");
			List<BLModel> blModels = new ArrayList<BLModel>();
			for (Element bl : bls) {
				blModels.add(new BLModel(bl.attr("bl"), bl.text()));
			}
			log.info("获取html里的业务语言");
			Map<String, Integer> BLs = new HashMap<String, Integer>();
			List<String> htmlBLs = StringUtil.getMatchers(HTMLBL, content);
			if(htmlBLs.size() > 0){
				for (String bl : htmlBLs) {
					BLs.put(bl,content.indexOf(bl));//TODO ？
				}				
			}
			log.info("获取HTML里JS脚本的BL语言。");
			blModels.addAll(jsAnalyze(content));
			htmlModel.setBls(blModels);
			log.info("a标签与业务语言关联");
			Elements as = doc.select("a");
			if (as.size() > 0) {
				List<AModel> aModels = new ArrayList<AModel>();
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
			}
			log.info("获取js引用");
			List<String> jsSrcs = StringUtil.getMatchers(JSSRC, content);
			if (jsSrcs.size() > 0) {
				htmlModel.setJsSrc(new ArrayList<String>());
				for (String s : jsSrcs) {
					s = s.substring(s.indexOf(PageConstant.SRC));
					s = s.substring(0, s.indexOf("\""));
					htmlModel.getJsSrc().add(s);
				}				
			}
			return htmlModel;
		} catch (Exception e) {
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
		List<String> jsBLs = StringUtil.getMatchers(JSBL, content);
		List<BLModel> blModels = new ArrayList<BLModel>();
		for (String string : jsBLs) {
			blModels.add(new BLModel(null, string));			
		}
		return blModels;
	}
	
	/**
	 * getValueType:(获取输入的类型，主要是对EASYUI的处理)
	 * @author lixingfa
	 * @date 2018年7月7日下午3:31:24
	 * @param input
	 * @return
	 */
	private PageConstant.VALUETYPE getValueType(Element input){
		String inputStr = input.toString();
		if (inputStr.contains("number")) {
			return PageConstant.VALUETYPE.number;
		}else if (inputStr.contains("date")) {
			if (inputStr.contains("datetime")) {
				return PageConstant.VALUETYPE.datatime;
			}else {
				return PageConstant.VALUETYPE.data;
			}
		}
		return PageConstant.VALUETYPE.string;
	}
	
	/**
	 * getInputModels:(获取输入)
	 * @author lixingfa
	 * @date 2018年7月7日下午4:11:37
	 * @param inputs
	 * @return List<InputModel>
	 */
	private List<InputModel> getInputModels(Element element){
		Elements inputs = element.select(PageConstant.INPUT);
		if (inputs.size() > 0) {
			List<InputModel> inputModels = new ArrayList<InputModel>();
			for (Element input : inputs) {
				InputModel inputModel = new InputModel();
				inputModel.setId(input.attr(PageConstant.ID));
				inputModel.setMaxlength(input.attr(PageConstant.MAXLENGTH));
				inputModel.setName(input.attr(PageConstant.NAME));
				inputModel.setPlaceholder(input.attr(PageConstant.PLACEHOLDER));
				if (StringUtils.isBlank(inputModel.getPlaceholder())) {
					inputModel.setPlaceholder(input);
				}
				inputModel.setValue(input.attr(PageConstant.VALUE));
				String type = input.attr(PageConstant.TYPE);
				if ("".equals(type)) {
					inputModel.setType(INPUTTYPE.text);
				}else {
					inputModel.setType(INPUTTYPE.valueOf(type));
				}
				inputModel.setValueType(getValueType(input));
				inputModels.add(inputModel);
			}
			return inputModels;			
		}else {
			return null;
		}
	}
	
	private List<TextareaModel> getTextareaModels(Element element){
		Elements textareas = element.select(PageConstant.TEXTAREA);
		if (textareas.size() > 0) {
			List<TextareaModel> textareaList = new ArrayList<TextareaModel>();
			for (Element textarea : textareas) {
				TextareaModel textareaModel  = new TextareaModel();
				textareaModel.setId(textarea.attr(PageConstant.ID));
				textareaModel.setName(textarea.attr(PageConstant.NAME));
				textareaModel.setPlaceholder(textarea.attr(PageConstant.PLACEHOLDER));
				if (StringUtils.isBlank(textareaModel.getPlaceholder())) {
					textareaModel.setPlaceholder(textarea);
				}
				textareaModel.setValue(textarea.html());//值不一样
				textareaModel.setType(INPUTTYPE.text);
				textareaModel.setValueType(PageConstant.VALUETYPE.string);
				textareaList.add(textareaModel);
			}
			return textareaList;			
		}else {
			return null;
		}
	}
	
	/**
	 * getSelectModels:()
	 * @author lixingfa
	 * @date 2018年7月7日下午4:27:15
	 * @param element
	 * @return List<SelectModel>
	 */
	private List<SelectModel> getSelectModels(Element element){
		Elements selects = element.select(PageConstant.SELECT);
		if (selects.size() > 0) {
			List<SelectModel> selectModels = new ArrayList<SelectModel>();
			for (Element select : selects) {
				SelectModel selectModel = new SelectModel(select.attr(PageConstant.ID),select.attr(PageConstant.NAME));
				selectModel.setMultiple(select.attr(PageConstant.MULTIPLE));
				selectModel.setPlaceholder(select);
				Elements options = select.select(PageConstant.OPTION);
				Map<String, String> map = new HashMap<String, String>();
				for (Element option : options) {
					if (option.text().contains("请")) {
						continue;
					}
					map.put(option.text(), option.attr(PageConstant.VALUE));
				}
				selectModel.setOption(map);
				selectModels.add(selectModel);
			}
			return selectModels;
		}else {
			return null;
		}
	}
	
	/**
	 * getFormUrl:(获取表单的请求链接)
	 * @author lixingfa
	 * @date 2018年7月7日下午4:54:43
	 * @param formId
	 * @param content
	 * @return
	 */
	private String getFormUrl(String formId,String content){
		//该表单的定义，ajaxSubmit\easyui……
		String url = null;
		List<String> formDefs = StringUtil.getMatchers("$\\([ ]*[\"']{1}#" + formId + "[\"']{1}[ ]*\\).", content);
		for (String formDef : formDefs) {
			int begin = content.indexOf(formDef);
			String string = StringUtil.getSubStringByLR(begin, '{', '}', content);
			List<String> urls = StringUtil.getMatchers("url[ ]*:[ ]*['\"]{1}[$\\w./?=&]+['\"]{1},", string);
			for (String temp : urls) {
				temp = temp.substring(temp.indexOf(":"));
				url = temp.replace("'", "").replace("\"", "");
			}
		}
		return url;
	}
	
	
	
}
