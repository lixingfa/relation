package com.garlane.relation.common.model.dataBase;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 数据库模型
 * @author Lixingfa
 *
 */
public class DataBaseModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**数据库名*/
	private String name;
	/**表*/
	ArrayList<DataTableModel> tables = new ArrayList<DataTableModel>();
	
	public DataBaseModel(String name){
		this.name = name;
	}
	
	/**
	 * 获取表
	 * @param tableName 表名
	 * @return 数据表模型
	 */
	public DataTableModel getBaseModel(String tableName){
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).getTableName().equals(tableName)) {
				return tables.get(i);
			}
		}
		return null;
	}

	public ArrayList<DataTableModel> getTables() {
		return tables;
	}

	public void setTables(ArrayList<DataTableModel> tables) {
		this.tables = tables;
	}

	public String getName() {
		return name;
	}
}
