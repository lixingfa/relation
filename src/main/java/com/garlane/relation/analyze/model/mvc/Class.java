package com.garlane.relation.analyze.model.mvc;

import java.util.HashSet;
import java.util.Set;

import com.garlane.relation.common.utils.change.StringUtil;

public class Class {
	/**类名*/
	private String className;
	/**类的属性*/
	private Set<ClassProperty> properties = new HashSet<ClassProperty>();
	//属于哪个页面的，可以看其属于哪个htmlModel
	/********************************************/
	public Class(String className){
		this.className = className;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public Set<ClassProperty> getProperties() {
		return properties;
	}

	public void setProperties(Set<ClassProperty> properties) {
		this.properties = properties;
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer("public class " + className + "{");
		StringBuffer getSetBuffer = new StringBuffer();
		for (ClassProperty property : properties) {
			if (property.getTitle() != null) {
				s.append("/**").append(property.getTitle()).append("*/");
			}
			s.append("private ").append(property.getType()).append(" ").append(property.getPropertyName()).append(";\n");
			
			getSetBuffer.append("\npublic ").append(property.getType()).append(" get").append(StringUtil.upFirst(property.getPropertyName())).append("(){")
			.append("return ").append(property.getPropertyName()).append(";").append("}");
			
			getSetBuffer.append("\npublic void ").append(" set").append(StringUtil.upFirst(property.getPropertyName()))
			.append("(").append(property.getType()).append(" ").append(property.getPropertyName()).append("){")
			.append("this.").append(property.getPropertyName()).append(" = ").append(property.getPropertyName()).append(";").append("}");
		}
		return s.append("\n").append(getSetBuffer).append("}").toString();		
	}	
}
