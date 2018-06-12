/**
 * 
 */
package com.garlane.relation.common.model.page;

/**
 * @author lixingfa
 * @date 2018年6月11日下午5:28:50
 * 
 */
public class InputModel {

	private String id;
	/**name属性与后台代码相关，进而与数据库相关*/
	private String name;
	/**未输入值时输入框的提示*/
	private String placeholder;
	/**输入框的名称标准方式下是用lable标签定义的，其for属性指向input的id*/
	private String lableText;
	/**规定输入字段中的字符的最大长度*/
	private String maxlength;
	/**值*/
	private String value;
	
	/*****************************/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public String getLableText() {
		return lableText;
	}
	public void setLableText(String lableText) {
		this.lableText = lableText;
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
}
