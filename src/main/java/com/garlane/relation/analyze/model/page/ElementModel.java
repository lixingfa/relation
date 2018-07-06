/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;

import com.garlane.relation.common.constant.DBConstant;

/**页面上承载信息的元素
 * @author lixingfa
 * @date 2018年5月28日下午6:18:34
 * 
 */
public class ElementModel implements Serializable{
	private static final long serialVersionUID = 1L;

	/**元素的id，最好每个元素都有一个*/
	private String id;
	/**元素的名称，一般与数据库对应*/
	private String name;
	/**名字转驼峰后可能数据库名字*/
	private String columnName;
	/**值，最好每个输入都有样例*/
	private String value;
	/**根据值获取数据类型，当有多个值时，取最大的类型*/
	private DBConstant.dataType dataType;
	/**父元素的id*/
	private String parentId;
	/************************************/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public DBConstant.dataType getDataType() {
		return dataType;
	}
	public void setDataType(DBConstant.dataType dataType) {
		this.dataType = dataType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
