package com.garlane.relation.analyze.model.logic;

import java.util.HashSet;
import java.util.Set;

public class Class {
	/**类名*/
	private String className;
	/**类的属性*/
	private Set<ClassProperty> properties = new HashSet<ClassProperty>();
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
		for (ClassProperty property : properties) {
			if (property.getTitle() != null) {
				s.append("/**").append(property.getTitle()).append("*/");
			}
			s.append("private ").append(property.getType()).append(" ").append(property.getPropertyName()).append(";\n");
		}
		return s.append("}").toString();
		
	}
}
