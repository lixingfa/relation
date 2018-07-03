package com.garlane.relation.analyze.model.easyui;
/**
 * 页面上与逻辑有关的，EASYUI对象的集合
 * @author lingxingfa
 *
 */
public class EASYUIModel {

	private DataGridModel dataGridModel;
	private TreeGridModel treeGridModel;
	
	/******************************/
	public DataGridModel getDataGridModel() {
		return dataGridModel;
	}
	public void setDataGridModel(DataGridModel dataGridModel) {
		this.dataGridModel = dataGridModel;
	}
	public TreeGridModel getTreeGridModel() {
		return treeGridModel;
	}
	public void setTreeGridModel(TreeGridModel treeGridModel) {
		this.treeGridModel = treeGridModel;
	}
	
}
