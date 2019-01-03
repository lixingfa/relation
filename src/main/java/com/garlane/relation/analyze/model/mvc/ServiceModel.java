/**
 * 
 */
package com.garlane.relation.analyze.model.mvc;

import java.util.Set;

/**
 * @author lixingfa
 * @date 2019年1月3日下午4:05:55
 * 
 */
public class ServiceModel extends BaseModel{

	/**注解名称**/
	private String serviceName;
	/**service也会引用service**/
	private Set<ServiceModel> serviceModels;
	/**引用的dao**/
	private Set<DaoModel> daoModels;
	
	/******************************************/
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Set<ServiceModel> getServiceModels() {
		return serviceModels;
	}
	public void setServiceModels(Set<ServiceModel> serviceModels) {
		this.serviceModels = serviceModels;
	}
	public Set<DaoModel> getDaoModels() {
		return daoModels;
	}
	public void setDaoModels(Set<DaoModel> daoModels) {
		this.daoModels = daoModels;
	}
}
