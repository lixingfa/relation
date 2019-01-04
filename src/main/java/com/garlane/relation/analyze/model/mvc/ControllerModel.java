/**
 * 
 */
package com.garlane.relation.analyze.model.mvc;

import java.util.Set;

/**
 * @author lixingfa
 * @date 2019年1月3日下午4:01:53
 * 
 */
public class ControllerModel extends BaseModel{
	/**类的请求地址**/
	private String requestMapping;
	/**service**/
	private Set<ServiceModel> serviceModels;
	
	/**********************************************/
	public String getRequestMapping() {
		return requestMapping;
	}
	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}
	public Set<ServiceModel> getServiceModels() {
		return serviceModels;
	}
	public void setServiceModels(Set<ServiceModel> serviceModels) {
		this.serviceModels = serviceModels;
	}
	public void addToPageMethod(String pageUrl){
		MethodModel model = new MethodModel();
		model.setReturnType("String");//TODO 换成常量
		pageUrl = pageUrl.substring(pageUrl.indexOf("WEB-INF\\jsp"));
		model.setRetrunValue(pageUrl);
		pageUrl = pageUrl.substring(pageUrl.lastIndexOf("\\"), pageUrl.indexOf("."));
		model.setName(pageUrl);		
		getMethodModels().add(model);
	}
}
