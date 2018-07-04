/**
 * 
 */
package com.garlane.relation.analyze.model.easyui;

/**
 * @author lixingfa
 * @date 2018年7月3日下午2:10:14
 * 
 */
public class TreeGridModel extends DataGridModel{

	/**子节点的数据请求，与根节点的不一定一样*/
	private String subUrl;
	/**定义树节点字段*/
	private String treeField;
	/**areaSeq,areaName*/
	public TreeGridModel(String idField,String treeField){
		this.idField = idField;
		this.treeField = treeField;
	}
	
	/******************************/
	public String getTreeField() {
		return treeField;
	}
}
