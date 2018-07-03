/**
 * 
 */
package com.garlane.relation.analyze.service.impl;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.service.EASYUIAnalyzeService;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**转成JSON，easyUI基本是用了JSON的思想
 * @author lixingfa
 * @date 2018年7月3日下午2:06:56
 * 
 */
@Service("EASYUIAnalyzeService")
public class EASYUIAnalyzeServiceImpl implements EASYUIAnalyzeService{
	/**
	 * 从内容中获取EASYUI的所有对象信息
	 * @param content html\jsp\js文件内容
	 * @return EASYUIModel
	 * @throws SuperServiceException
	 */
	public EASYUIModel getEasyuiModel(String content) throws SuperServiceException{
		EASYUIModel easyuiModel = new EASYUIModel();
		
		return easyuiModel;
	}
//
	public static void main(String[] args) {
		String content = "{    "+
					"fit: false,"+
					"nowrap: true, "+
					"scrollbarSize: 0,"+
					"autoRowHeight: true,"+
					"animate:true,"+
					"scrollbarSize: 0,"+
					"striped: true,"+
					"collapsible:true,"+
					"fitColumns:true,"+
					"singleSelect:true,"+
					"rownumbers:true,"+
					"url:'${root}/admin/sysinterface/area/loadListAreaAll.do',"+
					"idField:'areaSeq',"+
					"treeField:'areaName',"+
					"columns:[["+
						"{field:'ck', checkbox: true},"+
						"{field:'parentAreaSeq', hidden:true},"+
						"{field:'areaName',title:'地区名称',width:250},"+
						"{field:'jcAreaSeq',title:'行政区划代码',width:300},"+
						"{field:'monitorPhone',title:'监督电话',width:150, align: 'center'}"+
					"]],"+
					/*"onBeforeLoad:function(row,param){//加载之前"+
						"if(row){"+
							"$(this).treegrid('options').url = '${root}/admin/sysinterface/area/loadListSubArea.do?parentAreaSeq=' + row.areaSeq;"+
						"}else{"+
							"$(this).treegrid('options').url = '${root}/admin/sysinterface/area/loadListAreaAll.do';"+
						"}"+
					"}"+*/
				"}";
		
		JSONObject jsonObject = JSONObject.parseObject(content);
		System.out.println(jsonObject.getString("treeField"));
		JSONArray arrays = jsonObject.getJSONArray("columns");
		for (int i = 0; i < arrays.size(); i++) {
			JSONArray subArray = arrays.getJSONArray(i);//列属性是数组
			for (int j = 0; j < subArray.size(); j++) {
				JSONObject object = subArray.getJSONObject(j);
				System.out.println(object.get("field"));
			}
		}
	}
}
