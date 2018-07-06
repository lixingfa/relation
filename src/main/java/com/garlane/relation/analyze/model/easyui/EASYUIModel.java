package com.garlane.relation.analyze.model.easyui;

import java.io.Serializable;
import java.util.List;

/**
 * 页面上与逻辑有关的，EASYUI对象的集合
 * @author lingxingfa
 *
 */
public class EASYUIModel implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<GridModel> gridModels;

	private List<TreeModel> treeModels;
	
	/******************************/
	public List<GridModel> getGridModels() {
		return gridModels;
	}
	
	public void setGridModels(List<GridModel> gridModels) {
		this.gridModels = gridModels;
	}

	public List<TreeModel> getTreeModels() {
		return treeModels;
	}

	public void setTreeModels(List<TreeModel> treeModels) {
		this.treeModels = treeModels;
	}
	
}
