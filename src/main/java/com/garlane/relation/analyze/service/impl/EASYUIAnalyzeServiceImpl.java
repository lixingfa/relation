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

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.analyze.model.easyui.ActionModel;
import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.model.easyui.JSFunctionModel;
import com.garlane.relation.analyze.model.easyui.TreeGridModel;
import com.garlane.relation.analyze.service.EASYUIAnalyzeService;
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
	private static final String URL = "url[ =:]+['\"\\w/.\\?=\\+&$\\{\\}\\[\\]]+";//好多转意
	
	/**
	 * 从内容中获取EASYUI的所有对象信息
	 * @param content html\jsp\js文件内容
	 * @return EASYUIModel
	 * @throws SuperServiceException
	 */
	public EASYUIModel getEasyuiModel(String content) throws SuperServiceException{
		EASYUIModel easyuiModel = new EASYUIModel();
		//TreeGrid
		easyuiModel.setTreeGridModels(getGridModels(content));
		//
		
		return easyuiModel;
	}
	
	/**
	 * getGridModels:(提取内容中的TreeGrid信息)
	 * @author lixingfa
	 * @date 2018年7月4日下午7:48:03
	 * @param content
	 * @return List<TreeGridModel> or null
	 */
	private List<TreeGridModel> getGridModels(String content){
		List<TreeGridModel> treeGridModels = new ArrayList<TreeGridModel>();
		int index = content.indexOf(".treegrid({");
		if (index != -1) {
			List<ActionModel> actionModels = new ArrayList<ActionModel>();
			String grid = StringUtil.getSubStringByLR(index, '{', '}', content);//确保从第一个花括号开始
			//处理onBeforeLoad之类的属性，后面跟的是函数，因为无法转成json，需要处理
			List<JSFunctionModel> jsFunctionModels = analyzeInserFunctions(grid);
			//替换掉内置函数
			for (JSFunctionModel jsFunctionModel : jsFunctionModels) {
				grid = grid.replace(jsFunctionModel.getFunctionString(), "''");//替换成空值
				actionModels.addAll(jsFunctionModel.getActionModels());//获取内置函数的action
			}
			//转成JSON
			JSONObject jsonObject = JSONObject.parseObject(grid);
			TreeGridModel treeGridModel = new TreeGridModel(jsonObject.getString(EASYUIConstant.ID_FIELD), jsonObject.getString(EASYUIConstant.TREE_FIELD));
			treeGridModel.setActionModels(actionModels);
			
			JSONArray arrays = jsonObject.getJSONArray(EASYUIConstant.COLUMNS);
			for (int i = 0; i < arrays.size(); i++) {
				JSONArray subArray = arrays.getJSONArray(i);//列属性是数组
				for (int j = 0; j < subArray.size(); j++) {
					JSONObject object = subArray.getJSONObject(j);
					System.out.println(object.get(EASYUIConstant.FIELD));
				}
			}
			return treeGridModels;
		}else {
			return null;
		}
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
			int begin = grid.indexOf("function(");//TODO 这里有坑，什么样的页面代码都可能存在，可以要求使用者按标准方式使用
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
		Pattern pattern = Pattern.compile(URL);
		Matcher matcher = pattern.matcher(func);
		while (matcher.find()) {
			//找到链接
			String s = matcher.group();
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
			//请求类型 type post
			//返回类型 success
			actionModels.add(actionModel);
		}
		return actionModels;
	}
}
