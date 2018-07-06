/**
 * 
 */
package com.garlane.relation.analyze.model.easyui;
import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * @author lixingfa
 * @date 2018年7月3日下午2:09:49
 * 
 */
public class TreeModel implements Serializable{
	private static final long serialVersionUID = 1L;

	/**元素id*/
	protected String id;
	/**抽离action*/
	protected List<ActionModel> actionModels;
	/**数组形式的数据，用于没有分页和treegrid，treegrid有children的子列*/
	protected JSONArray dataArray = null;
	
	/******************************/
	public List<ActionModel> getActionModels() {
		return actionModels;
	}
	public void setActionModels(List<ActionModel> actionModels) {
		this.actionModels = actionModels;
	}
	public JSONArray getDataArray() {
		return dataArray;
	}
	public void setDataArray(JSONArray dataArray) {
		this.dataArray = dataArray;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/*
	 * data: [{
		text: 'Item1',
		state: 'closed',
		children: [{
			text: 'Item11'
		},{
			text: 'Item12'
		}]
	},{
		text: 'Item2'
	}]

	 */
}
