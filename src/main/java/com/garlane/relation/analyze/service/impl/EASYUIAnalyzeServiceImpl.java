/**
 * 
 */
package com.garlane.relation.analyze.service.impl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.analyze.model.easyui.ActionModel;
import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.model.easyui.GridModel;
import com.garlane.relation.analyze.model.easyui.TreeModel;
import com.garlane.relation.analyze.model.el.ELModel;
import com.garlane.relation.analyze.model.js.JSFunctionModel;
import com.garlane.relation.analyze.service.EASYUIAnalyzeService;
import com.garlane.relation.analyze.service.ELAnalyzeService;
import com.garlane.relation.common.constant.EASYUIConstant;
import com.garlane.relation.common.constant.RegularConstant;
import com.garlane.relation.common.utils.change.StringUtil;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**转成JSON，easyUI基本是用了JSON的思想
 * @author lixingfa
 * @date 2018年7月3日下午2:06:56
 * 
 */
@Service("easyuiAnalyzeService")
public class EASYUIAnalyzeServiceImpl implements EASYUIAnalyzeService{
	private static final Logger log = Logger.getLogger(EASYUIAnalyzeServiceImpl.class);
	
	@Autowired
	private ELAnalyzeService elAnalyzeService;
	
	/**
	 * 从内容中获取EASYUI的所有对象信息
	 * @param content html\jsp\js文件内容
	 * @return EASYUIModel
	 * @throws SuperServiceException
	 */
	public EASYUIModel getEasyuiModel(String content) throws SuperServiceException{
		//去掉+以后要补引号${root}/admin/system/user/loadUserByUnit.do?unitSubCode='+unitCode,
		content = StringUtil.replaceMatchers(RegularConstant.PLUS_SIGN, "", content);
		
		
		EASYUIModel easyuiModel = new EASYUIModel();
		//tree、combotree
		easyuiModel.setTreeModels(getTreeModels(content));
		//Grid、combogrid
		easyuiModel.setGridModels(getGridModels(content));
		//combobox 都是静态写死的下拉
		
		
		
		return easyuiModel;
	}
	
	/**
	 * getGridModels:(提取内容中的TreeGrid信息)
	 * @author lixingfa
	 * @date 2018年7月4日下午7:48:03
	 * @param content
	 * @return List<GridModel> or null
	 */
	private List<GridModel> getGridModels(String content){
		List<String> grids = StringUtil.getMatchers(RegularConstant.GRID_DEF, content);
		List<GridModel> gridModels = new ArrayList<GridModel>();
		if (grids.size() > 0) {
			for (String gridStr : grids) {
				int index = content.indexOf(gridStr) + gridStr.length();
				String grid = StringUtil.getSubStringByLR(index - 1, '{', '}', content);//确保从第一个花括号开始
				String id = getIdBySubString(gridStr);
				//检查id是否存在了
				GridModel gridModel = null;
				for (GridModel model : gridModels) {
					if (id.equals(model.getId())) {
						gridModel = model;
						break;
					}
				}
				//先将grid里的url变量替换，否则转JSON会报错
				List<String> urls = StringUtil.getMatchers(RegularConstant.URL_VARIABLE, grid);
				for (String url : urls) {
					String d = "";
					String urlVariable = url.substring(url.indexOf(":") + 1).trim();
					if (urlVariable.contains(",")) {
						urlVariable = urlVariable.replace(",", "").trim();
						d = ",";
					}
					urlVariable = StringUtil.findTheVariate(urlVariable, content.indexOf(url), content);
					grid = grid.replace(url, "url:" + urlVariable + d);
				}
				gridModels.add(analyzeGridStr(grid,id,gridModel));//TODO gridModel != null，就应该是替换 
				content = content.substring(index + grid.length());
			}
		}
		return gridModels;
	}
	
	/**
	 * analyzeGridStr:(解析grid字符串)
	 * @author lixingfa
	 * @date 2018年7月6日上午10:31:06
	 * @param grid
	 * @param id
	 * @return GridModel
	 */
	private GridModel analyzeGridStr(String grid,String id,GridModel gridModel){
		List<ActionModel> actionModels = new ArrayList<ActionModel>();
		//处理onBeforeLoad之类的属性，后面跟的是函数，因为无法转成json，需要处理
		List<JSFunctionModel> jsFunctionModels = analyzeInserFunctions(grid);
		//需要把长的放在前面，否则短的会破坏长的
		Collections.sort(jsFunctionModels);
		//替换掉内置函数，避免转换JSON出错
		for (JSFunctionModel jsFunctionModel : jsFunctionModels) {
			grid = grid.replace(jsFunctionModel.getFunctionString(), "''");//替换成空值
			actionModels.addAll(jsFunctionModel.getActionModels());//获取内置函数的action
		}		
		//grid转成JSON
		JSONObject gridObj = getJsonObject(grid);
		if (gridModel == null) {
			if (gridObj.containsKey(EASYUIConstant.TREE_FIELD)) {//treegrid里idField和treeField是必须的，代表节点流水号和名称
				gridModel = new GridModel(gridObj.getString(EASYUIConstant.ID_FIELD), gridObj.getString(EASYUIConstant.TREE_FIELD));			
			}else if(gridObj.containsKey(EASYUIConstant.ID_FIELD)){//datagrid
				gridModel = new GridModel(gridObj.getString(EASYUIConstant.ID_FIELD));
			}else {//datagrid不强制idField
				gridModel = new GridModel();
			}
			gridModel.setId(id);//js定义的绝对有id，html的未必有
			gridModel.setActionModels(actionModels);
		}else {
			gridModel.getActionModels().addAll(actionModels);
		}
		//树形组件本身的action 和结果 el
		String url = gridObj.getString(EASYUIConstant.URL);
		if (url != null) {
			List<ELModel> columns = new ArrayList<ELModel>();
			JSONArray columnObj = gridObj.getJSONArray(EASYUIConstant.COLUMNS);
			if (columnObj != null) {
				for (int i = 0; i < columnObj.size(); i++) {
					JSONArray subArray = columnObj.getJSONArray(i);//列属性是数组
					for (int j = 0; j < subArray.size(); j++) {
						JSONObject object = subArray.getJSONObject(j);
						if (object.containsKey(EASYUIConstant.CHECKBOX) && object.getBooleanValue(EASYUIConstant.CHECKBOX)) {
							continue;
						}
						ELModel elModel = new ELModel(object.getString(EASYUIConstant.FIELD));
						elModel.setTitle(object.getString(EASYUIConstant.TITLE));
						columns.add(elModel);						
					}
				}
			}
			ActionModel actionModel = new ActionModel(url);
			actionModel.setElModels(columns);
			gridModel.getActionModels().add(actionModel);			
		}
		//JSONArray columnObj 与 data是对应的，可以获取数据及初始化脚本
		if (gridObj.containsKey(EASYUIConstant.DATA)) {
			JSONObject data = gridObj.getJSONObject(EASYUIConstant.DATA);
			if (data.containsKey(EASYUIConstant.ROWS)) {//数据形式，分页
				if (gridModel.getDataObject() == null) {
					gridModel.setDataObject(data);					
				}else {
					gridModel.getDataObject().putAll(data);
				}
			}else {//数组形式，不分页
				JSONArray array = gridObj.getJSONArray(EASYUIConstant.DATA);
				if (gridModel.getDataArray() == null) {
					gridModel.setDataArray(array);
				}else {
					gridModel.getDataArray().addAll(array);
				}
			}
		}
		return gridModel;
	}
	
	/**
	 * analyzeInserFunctions:(解析内置函数,获取函数内容和请求信息)
	 * @author lixingfa
	 * @date 2018年7月5日上午11:04:18
	 * @param grid
	 * @return
	 */
	private List<JSFunctionModel> analyzeInserFunctions(String grid){
		List<JSFunctionModel> jsFunctionModels = new ArrayList<JSFunctionModel>();
		List<String> functions = StringUtil.getMatchers(RegularConstant.JS_FUNCTION_DEF, grid);
		int index = 0;
		for (int i = 0;i < functions.size(); i++) {
			String function = functions.get(i);
			int begin = grid.indexOf(function);
			String func = StringUtil.getSubStringByLR(begin, '{', '}', grid);
			//func 里还包含func,EASYUI 的组件里再来个top.dialog之类的。需要嵌套处理
			List<JSFunctionModel> subFunctionModels = analyzeInserFunctions(func);
			if (subFunctionModels.size() > 0) {
				jsFunctionModels.addAll(subFunctionModels);
				i = i + subFunctionModels.size();//有几个函数声明，就会有几个model
			}
			//处理自己
			JSFunctionModel jsFunctionModel = new JSFunctionModel(func);
			//抽取url
			jsFunctionModel.setActionModels(getActionModels(func));
			jsFunctionModels.add(jsFunctionModel);
			//移动索引
			index = begin + func.length();
			grid = grid.substring(index);
		}
		return jsFunctionModels;
	}
	
	/**
	 * getActionModels:(获取js函数中的请求)
	 * @author lixingfa
	 * @date 2018年7月5日上午11:36:28
	 * @param func
	 * @return
	 */
	private List<ActionModel> getActionModels(String func){
		List<ActionModel> actionModels = new ArrayList<ActionModel>();
		List<String> ajaxMatchers = StringUtil.getMatchers(RegularConstant.AJAX_DEF, func);
		//优先处理ajax类型
		if (ajaxMatchers.size() > 0) {
			for (String ajaxMatcher : ajaxMatchers) {
				int beginIndex = func.indexOf(ajaxMatcher);
				String ajaxString = StringUtil.getSubStringByLR(beginIndex, '{', '}', func);
				actionModels.add(analyzeAJAX(ajaxString,func));//TODO url变量会超出函数范围
				func = func.substring(beginIndex);
			}
		}else {
			List<String> urls = StringUtil.getMatchers(RegularConstant.URL_DEF, func);
			for (String s : urls) {
				if (s.contains(":")) {
					s = s.substring(s.indexOf(":") + 1);
				}else if(s.indexOf("=") < s.indexOf("?")){//=号在问号之前，否则会直接截取参数的，EASYUI的回调方法会出现这种情况
					s = s.substring(s.indexOf("=") + 1);
				}
				s = s.replace("'", "").replace("\"", "");
				//找到链接
				String url;
				Map<String, String> params = null;
				if (s.contains("?")) {
					//带参数
					params = new HashMap<String, String>();
					int index = s.indexOf("?");
					url = s.substring(0,index);
					String paramString[] = s.substring(index + 1).split("&");
					for (String string : paramString) {
						String[] param = string.replace("+", "").split("=");
						params.put(param[0].trim(), param[1].trim());//TODO value是否需要处理？
					}
				}else {
					url = s;
				}
				ActionModel actionModel = new ActionModel(url);
				actionModel.setParams(params);
				actionModels.add(actionModel);				
			}
		}
		return actionModels;
	}
	
	/**
	 * analyzeAJAX:(解析ajax)
	 * @author lixingfa
	 * @date 2018年7月5日下午4:20:39
	 * @param ajaxString
	 * @return ActionModel
	 */
	private ActionModel analyzeAJAX(String ajaxString,String content){
		//url
		String url = null;
		List<String> matchers = StringUtil.getMatchers(RegularConstant.URL_DEF, ajaxString);
		if (matchers.size() > 0) {
			url = matchers.get(0);
			url = url.substring(url.indexOf(":") + 1).trim().replace("'", "").replace("\"", "");
		}else {
			matchers = StringUtil.getMatchers(RegularConstant.URL_VARIABLE, ajaxString);
			url = StringUtil.findTheVariate(matchers.get(0), content.indexOf(ajaxString), content);			
		}
		ActionModel actionModel = new ActionModel(url);
		//type
		List<String> types = StringUtil.getMatchers(RegularConstant.TYPE, content);
		if (types.size() > 0) {
			String type = StringUtil.getSubStringByLR(content.indexOf(types.get(0)), ':', ',', content);
			if (type != null && type.toLowerCase().contains("post")) {
				actionModel.setReqType(ActionModel.REQ_TYPE_POST);
			}
		}
		//data
		List<String> datas = StringUtil.getMatchers(RegularConstant.DATA, content);
		if (datas.size() > 0) {
			String data = StringUtil.getSubStringByLR(content.indexOf(datas.get(0)), '{', '}', content);
			String[] paramStr = data.replace("{", "").replace("}", "").trim().split(",");
			Map<String, String> params = new HashMap<String,String>();
			for (String string : paramStr) {
				if (string.contains(":")) {
					String[] kv = string.split(":");
					params.put(kv[0].replace("'", "").replace("\"", "").trim(), kv[1].trim());					
				}else {
					//没有冒号，说明是,出现了例如'keys': configs.join(",")
					//貌似也不用处理
				}
			}
		}
		//success
		//回调参数，不要低估了那些人乱七八糟的写法……
		List<String> success = StringUtil.getMatchers("success[ \t\n]*:[ \t\n]*", ajaxString);
		//ajax如果没有success，那就逆天了
		String funcParam = StringUtil.getSubStringByLR(ajaxString.indexOf(success.get(0)), '(', ')', ajaxString);//success : 
		funcParam = funcParam.replace("(", "").replace(")", "");
		String succFunc = StringUtil.getSubStringByLR(ajaxString.indexOf(success.get(0)), '{', '}', ajaxString);
		//函数里用到回调参数的属性
		List<String> ks = StringUtil.getMatchers(funcParam + "[.]{1}[\\w]+", succFunc);
		StringBuffer kv = new StringBuffer();
		for (String k : ks) {
			kv.append(k).append(".");			
		}
		String[] propertys = kv.toString().split("\\.");
		actionModel.setElModels(elAnalyzeService.getElModels(propertys));
		return actionModel;
	}
	
	/**
	 * getTreeModels:(解析树控件)
	 * @author lixingfa
	 * @date 2018年7月6日下午4:25:28
	 * @param content
	 * @return
	 */
	private List<TreeModel> getTreeModels(String content){
		List<String> trees = StringUtil.getMatchers(RegularConstant.TREE_DEF, content);
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		if (trees.size() > 0) {
			for (String treeStr : trees) {
				int index = content.indexOf(treeStr);
				String id = getIdBySubString(treeStr);
				String tree = StringUtil.getSubStringByLR(index, '{', '}', content);//确保从第一个花括号开始
				//检查id是否存在了
				TreeModel treeModel = null;
				for (TreeModel model : treeModels) {
					if (id.equals(model.getId())) {
						treeModel = model;
						break;
					}
				}
				treeModels.add(analyzeGridStr(tree,id,(GridModel) treeModel));
				content = content.substring(index + tree.length());
			}
		}
		return treeModels;
	}
	
	/**
	 * getIdBySubString:(从子串里提前EASYUI的声明id)
	 * @author lixingfa
	 * @date 2018年7月17日上午9:25:30
	 * @param s
	 * @return id
	 */
	private String getIdBySubString(String s){
		return s.substring(s.indexOf("#") + 1,s.indexOf(")")).replace("'", "").replace("\"", "");
	}
	
	/**
	 * getJsonObject:(grid数据处理)
	 * @author lixingfa
	 * @date 2018年7月25日上午11:06:55
	 * @param grid 字符串
	 * @return JSONObject
	 */
	private JSONObject getJsonObject(String grid){
		JSONObject jsonObject = null;
		grid = StringUtil.replaceMatchers(RegularConstant.JS_FUNCTION_DEF, "", grid);
		//JSTL表达式会影响JSON生成，通常是if-else去掉并不影响判断，去掉以后还要检查是否有逗号缺失、重复之类的
		grid = removeJSTL(grid);
		//替换html，经常出现在title 和format里，用来修饰返回的字符串，里面的引号会对JSON有影响
		grid = StringUtil.replaceMatchers(RegularConstant.HTML, "", grid);
		//替换JQ取值
		grid = StringUtil.replaceMatchers(RegularConstant.JQ_VALUE, "''", grid);
		//替换EASYUI取值
		grid = StringUtil.replaceMatchers(RegularConstant.EASYUI_GETVALUE, "''", grid);
		//替换属性定义里的变量
		grid = StringUtil.replaceMatchers(RegularConstant.EASYUI_PROPERTY_VARIABLE, ":'',", grid);//EASYUI里的变量定义
		try {			
			//将参数组装到action里			
			jsonObject = JSONObject.parseObject(grid);			
		} catch (Exception e) {
			log.info("JSON转换出错，将尝试补救。字符串：" + grid);
			//有问题处理问题，别想着通用化
			if (grid.contains("url")) {
				//url字符串未闭合
				String url = StringUtil.getSubStringByLR(grid.indexOf("url"), ':', ',', grid).replace(":", "").trim();
				char[] c = url.toCharArray();
				//TODO 这里应该是数左右括号的数量，判断是否最后一个属性（虽然概率很低）				
				//以，和}结尾的url，未闭合
				if (c[c.length - 2] != c[0] && (c[c.length - 1] == '}' || c[c.length - 1] == ',')) {
					String replace = url.replace(c[c.length - 1] + "", "") + c[0] + c[c.length - 1];
					grid = grid.replace(url, replace);
				}
			}
			jsonObject = JSONObject.parseObject(grid);
		}
		return jsonObject;
	}
	
	/**
	 * removeJSTL:(去除JSTL标签，需要整合if-else移除后，js里的逗号问题)
	 * @author lixingfa
	 * @date 2018年7月25日上午11:09:55
	 * @param content
	 * @return String
	 */
	private String removeJSTL(String content){
		//关注选择类标签结束的地方
		List<String> matchers = StringUtil.getMatchers(RegularConstant.JSTL_END_BEGIN, content);
		StringBuffer sBuffer = new StringBuffer(content);
		String temp = content;
		//<c:if> <c:when> <c:otherwise>
		for (int i = matchers.size() - 1; i >= 0; i--) {
			String matcher = matchers.get(i);
			//单边选择没问题，去掉标签后，容易因为逗号缺失报错
			if (matcher.contains("</c:if>") && matcher.contains("<c:if ") ||
				matcher.contains("</c:when>") && (matcher.contains("<c:otherwise>") || matcher.contains("<c:when>"))) {
				int endIndex = temp.lastIndexOf(matcher);
				temp = temp.substring(0, endIndex);
				//单分支的上一个没有逗号隔开，去掉标签就报错了
				if (!temp.trim().endsWith(",")) {
					sBuffer.insert(temp.length(), ",");
				}
			}
		}
		return StringUtil.replaceMatchers(RegularConstant.JSTL, "", sBuffer.toString());
	}
}
