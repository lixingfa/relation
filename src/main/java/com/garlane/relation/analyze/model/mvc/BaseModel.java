/**
 * 
 */
package com.garlane.relation.analyze.model.mvc;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lixingfa
 * @date 2019年1月3日下午4:15:40
 * 
 */
public class BaseModel {
	
	/**包的路径**/
	private String packageValue;
	/**类名**/
	private String className;
	/**方法**/
	protected Set<MethodModel> methodModels = new HashSet<MethodModel>();
	
	public String getPackageValue() {
		return packageValue;
	}
	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Set<MethodModel> getMethodModels() {
		return methodModels;
	}
	public void setMethodModels(Set<MethodModel> methodModels) {
		this.methodModels = methodModels;
	}
	public void addMethodModel(MethodModel model){
		methodModels.add(model);
	}
}
