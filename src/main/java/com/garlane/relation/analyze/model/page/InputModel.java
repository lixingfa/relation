/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;

import com.garlane.relation.common.constant.PageConstant;

/**
 * @author lixingfa
 * @date 2018年6月11日下午5:28:50
 * 
 */
public class InputModel implements Serializable{
	private static final long serialVersionUID = 1L;

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
	/**值的类型*/
	private PageConstant.VALUETYPE valueType;
	/**数据类型*/
	private PageConstant.INPUTTYPE type;
	
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
	public PageConstant.INPUTTYPE getType() {
		return type;
	}
	public void setType(PageConstant.INPUTTYPE type) {
		this.type = type;
	}
	public PageConstant.VALUETYPE getValueType() {
		return valueType;
	}
	public void setValueType(PageConstant.VALUETYPE valueType) {
		this.valueType = valueType;
	}
	
}
