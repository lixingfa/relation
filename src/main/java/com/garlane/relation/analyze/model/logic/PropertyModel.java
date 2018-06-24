package com.garlane.relation.analyze.model.logic;
/**
 * 属性
 * @author lingxingfa
 *
 */
public class PropertyModel {

	/**name属性与后台代码相关，进而与数据库相关*/
	private String name;
	/**lable、文字提示、BL标签的文字*/
	private String BLText;
	/**规定输入字段中的字符的最大长度*/
	private String maxlength;
	/**值*/
	private String value;
	
	public PropertyModel(String BLText,String value) {
		this.BLText = BLText;
		this.value = value;
	}
	/************************************/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBLText() {
		return BLText;
	}
	public void setBLText(String bLText) {
		BLText = bLText;
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
