/**
 * 
 */
package com.garlane.relation.analyze.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.garlane.relation.common.utils.change.StringUtil;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**转成JSON，easyUI基本是用了JSON的思想
 * @author lixingfa
 * @date 2018年7月3日下午2:06:56
 * 
 */
@Service("easyuiAnalyzeService")
public class EASYUIAnalyzeServiceImpl implements EASYUIAnalyzeService{
	private static final String URL = "\\w[ =:]+['\"\\w/.\\?=\\+&$\\{\\}\\[\\]]+;";//好多转意
	
	@Autowired
	private ELAnalyzeService elAnalyzeService;
	
	/**
	 * 从内容中获取EASYUI的所有对象信息
	 * @param content html\jsp\js文件内容
	 * @return EASYUIModel
	 * @throws SuperServiceException
	 */
	public EASYUIModel getEasyuiModel(String content) throws SuperServiceException{
		EASYUIModel easyuiModel = new EASYUIModel();
		//Grid
		easyuiModel.setGridModels(getGridModels(content));
		//tree
		easyuiModel.setTreeModels(getTreeModels(content));
		
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
		if (content.contains(EASYUIConstant.TREEGRID_BEGIN) || content.contains(EASYUIConstant.DATAGRID_BEGIN)) {
			List<GridModel> gridModels = new ArrayList<GridModel>();
			int index = -1;
			do {
				index = content.indexOf("grid({");
				if (index == -1) {
					return null;
				}
				String grid = StringUtil.getSubStringByLR(index, '{', '}', content);//确保从第一个花括号开始
				gridModels.add(analyzeGridStr(grid));
				content = content.substring(index + grid.length());			
			} while (index != -1);
			return gridModels;
		}else {
			return null;
		}
	}
	
	/**
	 * analyzeGridStr:(解析grid字符串)
	 * @author lixingfa
	 * @date 2018年7月6日上午10:31:06
	 * @param grid
	 * @return GridModel
	 */
	private GridModel analyzeGridStr(String grid){
		List<ActionModel> actionModels = new ArrayList<ActionModel>();
		//处理onBeforeLoad之类的属性，后面跟的是函数，因为无法转成json，需要处理
		List<JSFunctionModel> jsFunctionModels = analyzeInserFunctions(grid);
		//替换掉内置函数，避免转换JSON出错
		for (JSFunctionModel jsFunctionModel : jsFunctionModels) {
			grid = grid.replace(jsFunctionModel.getFunctionString(), "''");//替换成空值
			actionModels.addAll(jsFunctionModel.getActionModels());//获取内置函数的action
		}
		//grid转成JSON
		JSONObject gridObj = JSONObject.parseObject(grid);
		GridModel gridModel;
		if (gridObj.containsKey(EASYUIConstant.TREE_FIELD)) {//treegrid里idField和treeField是必须的，代表节点流水号和名称
			gridModel = new GridModel(gridObj.getString(EASYUIConstant.ID_FIELD), gridObj.getString(EASYUIConstant.TREE_FIELD));			
		}else if(gridObj.containsKey(EASYUIConstant.ID_FIELD)){//datagrid
			gridModel = new GridModel(gridObj.getString(EASYUIConstant.ID_FIELD));
		}else {//datagrid不强制idField
			gridModel = new GridModel();
		}
		gridModel.setActionModels(actionModels);
		//树形组件本身的action 和结果 el
		List<ELModel> columns = new ArrayList<ELModel>();
		JSONArray columnObj = gridObj.getJSONArray(EASYUIConstant.COLUMNS);
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
		String url = gridObj.getString(EASYUIConstant.URL);
		ActionModel actionModel = new ActionModel(url);
		actionModel.setElModels(columns);
		gridModel.getActionModels().add(actionModel);
		//JSONArray columnObj 与 data是对应的，可以获取数据及初始化脚本
		if (gridObj.containsKey(EASYUIConstant.DATA)) {
			JSONObject data = gridObj.getJSONObject(EASYUIConstant.DATA);
			if (data.containsKey(EASYUIConstant.ROWS)) {
				gridModel.setDataObject(data);
			}else {
				gridModel.setDataArray(gridObj.getJSONArray(EASYUIConstant.DATA));				
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
		int index = 0;
		while (true) {			
			int begin = grid.indexOf("function(");//TODO 这里有坑，什么样的页面代码都可能存在，在提取的时候把空格去掉，换行要保留
			if (begin == -1) {
				break;
			}
			String func = StringUtil.getSubStringByLR(begin, '{', '}', grid);
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
		//优先处理ajax类型
		if (func.contains(EASYUIConstant.AJAX_BEGIN)) {
			String ajaxString = StringUtil.getSubStringByLR(0, '{', '}', func);
			actionModels.add(analyzeAJAX(ajaxString,func));//TODO url变量会超出函数范围
		}else {
			List<String> urls = StringUtil.getMatchers(URL, func);
			for (String s : urls) {
				//找到链接
				String url;
				Map<String, String> params = null;
				if (s.contains("?")) {
					//带参数
					params = new HashMap<String, String>();
					int index = s.indexOf("?");
					url = s.substring(0,index);
					String paramString[] = s.substring(index).split("&");
					for (String string : paramString) {
						String[] param = string.split("=");
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
		String url = StringUtil.getSubString("url:", ",", ajaxString);
		if (url.contains("'") || url.contains("\"")) {
			url = StringUtil.findTheVariate(url, ajaxString, content);
		}
		ActionModel actionModel = new ActionModel(url);
		//type
		String type = StringUtil.getSubString("type:", ",", ajaxString);
		if (type != null && type.toLowerCase().contains("post")) {
			actionModel.setReqType(ActionModel.REQ_TYPE_POST);
		}
		//data
		if (ajaxString.contains("data:")) {
			String data = StringUtil.getSubString("data:{", "}", ajaxString);
			if (data == null) {
				data = StringUtil.getSubString("data:", ",", ajaxString);
				data = StringUtil.findTheVariate(data, ajaxString, content);
			}		
			data = data.replace("'", "").replace("\"", "");
			String[] paramStr = data.split(",");
			Map<String, String> params = new HashMap<String,String>();
			for (String string : paramStr) {
				String[] kv = string.split(":");
				params.put(kv[0].trim(), kv[1].trim());
			}
		}
		//success
		String funcParam = StringUtil.getSubStringByLR(ajaxString.indexOf("success:"), '(', ')', ajaxString);
		String succFunc = StringUtil.getSubStringByLR(ajaxString.indexOf("success:"), '(', ')', ajaxString);
		Pattern pattern = Pattern.compile(funcParam + ".\\w");
		Matcher matcher = pattern.matcher(succFunc);
		StringBuffer kv = new StringBuffer();
		while (matcher.find()) {
			kv.append(matcher.group()).append(".");
		}
		String[] propertys = kv.toString().split(".");
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
		List<String> trees = StringUtil.getMatchers(EASYUIConstant.TREE_DEF, content);
		if (trees.size() > 0) {
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			for (String treeStr : trees) {
				//将字符串不断往后移，还是先找相同
			}
			return treeModels;
		}else {
			return null;			
		}
		if (content.contains(EASYUIConstant.TREE_BEGIN)) {
			int index = -1;
			do {
				index = content.indexOf(EASYUIConstant.TREE_BEGIN);
				if (index == -1) {
					return null;
				}
				String tree = StringUtil.getSubStringByLR(index, '{', '}', content);//确保从第一个花括号开始
				treeModels.add(analyzeGridStr(tree));
				content = content.substring(index + tree.length());			
			} while (index != -1);
		}else {
		}
	}
	
	
}
