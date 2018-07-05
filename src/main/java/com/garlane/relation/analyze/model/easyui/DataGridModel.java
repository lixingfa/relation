/**
 * 
 */
package com.garlane.relation.analyze.model.easyui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lixingfa
 * @date 2018年7月3日下午2:09:49
 * 
 */
public class DataGridModel {

	protected List<ActionModel> actionModels;
	/**列，包含frozenColumns，只收录与后台相关， checkbox不为true的列*/
	protected List<ColumnModel> columns = new ArrayList<ColumnModel>();
	/**标识字段，往往是数据表的主键*/
	protected String idField;
	/**动态系统中很少用到，静态demo中使用，可以获得返回的数据结果、初始数据*/
	protected List<Object> data;
	/**单选，如果可以多选，需要留心传给后台的值如何处理*/
	protected boolean singleSelect = false;
	/**是否分页，影响后台方法*/
	protected boolean pagination = false;
	/**初始化页码*/
	//protected int pageNumber = 1;
	/**初始化页面大小*/
	//protected int pageSize = 10;
	
	
	/*********************************/
	public List<ColumnModel> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}
	public String getIdField() {
		return idField;
	}
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	public boolean isSingleSelect() {
		return singleSelect;
	}
	public void setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
	}
	public boolean isPagination() {
		return pagination;
	}
	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}
	public List<ActionModel> getActionModels() {
		return actionModels;
	}
	public void setActionModels(List<ActionModel> actionModels) {
		this.actionModels = actionModels;
	}
	
}
