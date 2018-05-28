package com.garlane.relation.common.model.dataBase;

import java.util.ArrayList;

/**
 * 数据表模型
 * @author Lixingfa
 *
 */
public class DataTableModel {
	String tableName;
	/**数据列*/
	ArrayList<ColumnModel> columnModels = new ArrayList<ColumnModel>();
	
	public DataTableModel(String tableName){
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return tableName;
	}
	public ArrayList<ColumnModel> getColumnModels() {
		return columnModels;
	}
	public void setColumnModels(ArrayList<ColumnModel> columnModels) {
		this.columnModels = columnModels;
	}	
}
