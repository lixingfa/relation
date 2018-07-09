package com.garlane.relation.analyze.model.logic;

import java.io.Serializable;

import com.garlane.relation.common.utils.change.StringUtil;

/**
 * 属性
 * @author lingxingfa
 *
 */
public class PropertyModel implements Serializable{
	private static final long serialVersionUID = 1L;

	/**id*/
	private String id;
	/**父属性*/
	private String parentId;
	/**name属性与后台代码相关，进而与数据库相关*/
	private String name;
	/**lable、文字提示、BL标签的文字*/
	private String title;
	/**规定输入字段中的字符的最大长度*/
	private String maxlength;
	/**值*/
	private String value;
	
	public PropertyModel(String title,String name) {
		this.id = StringUtil.getUUID();
		this.title = title;
		this.name = name;
	}
	/************************************/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
