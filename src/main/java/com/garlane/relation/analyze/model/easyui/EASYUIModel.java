package com.garlane.relation.analyze.model.easyui;

import java.util.List;

/**
 * 页面上与逻辑有关的，EASYUI对象的集合
 * @author lingxingfa
 *
 */
public class EASYUIModel {

	private List<DataGridModel> dataGridModels;
	private List<TreeGridModel> treeGridModels;
	
	/******************************/
	public List<DataGridModel> getDataGridModels() {
		return dataGridModels;
	}
	public void setDataGridModels(List<DataGridModel> dataGridModels) {
		this.dataGridModels = dataGridModels;
	}
	public List<TreeGridModel> getTreeGridModels() {
		return treeGridModels;
	}
	public void setTreeGridModels(List<TreeGridModel> treeGridModels) {
		this.treeGridModels = treeGridModels;
	}	
}
