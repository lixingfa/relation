package com.garlane.relation.analyze.model.mvc;
/**
 * 类的属性
 * @author lingxingfa
 *
 */
public class ClassProperty {

	/**类型，String\int之类的*/
	private String type;
	/**属性的名字*/
	private String propertyName;
	/**名称，中文注释*/
	private String title;
	
	/**************************************/
	public ClassProperty(String type,String propertyName){
		this.type = type;
		this.propertyName = propertyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
