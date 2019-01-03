/**
 * 
 */
package com.garlane.relation.analyze.model.mvc;

import java.util.Set;

import com.garlane.relation.config.Constant;

/**
 * @author lixingfa
 * @date 2019年1月3日下午4:07:13
 * 
 */
public class MethodModel {

	/**请求类型**/
	private Constant.methodType methodType;
	/**返回类型**/
	private String returnType;
	/**方法名称**/
	private String name;
	/**参数**/
	private Set<ClassProperty> properties;
	
	/************************************/
	public Constant.methodType getMethodType() {
		return methodType;
	}
	public void setMethodType(Constant.methodType methodType) {
		this.methodType = methodType;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<ClassProperty> getProperties() {
		return properties;
	}
	public void setProperties(Set<ClassProperty> properties) {
		this.properties = properties;
	}
}
