/**
 * 
 */
package com.garlane.relation.analyze.model.easyui;

/**
 * @author lixingfa
 * @date 2018年7月3日下午2:10:14
 * 
 */
public class TreeGrid extends DataGrid{

	/**定义树节点字段*/
	private String treeField;
	/**areaSeq,areaName*/
	public TreeGrid(String idField,String treeField){
		this.idField = idField;
		this.treeField = treeField;
	}
	
	/******************************/
	public String getTreeField() {
		return treeField;
	}
}
