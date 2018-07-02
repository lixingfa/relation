/**
 * 
 */
package com.garlane.relation.analyze.model.logic;

import java.util.HashSet;
import java.util.Set;

import com.garlane.relation.common.utils.change.StringUtil;

/**属性分布树，属性在系统根目录、不同模块、文件夹、页面、表单、表格里的分布形成一棵树
 * @author lixingfa
 * @date 2018年7月2日下午7:51:15
 * 
 */
public class PropertyTree {
	private String id;
	private String parentId;
	/**零散的属性，一个页面里单独存在的属性，包含在表格、表单里的不在此列*/
	private Set<PropertyModel> scatteredPropertys = new HashSet<PropertyModel>();
	
	public PropertyTree(String parentId){
		this.parentId = parentId;
		id = StringUtil.getUUID();
	}

	public String getId() {
		return id;
	}

	public String getParentId() {
		return parentId;
	}

	public Set<PropertyModel> getScatteredPropertys() {
		return scatteredPropertys;
	}

	public void setScatteredPropertys(Set<PropertyModel> scatteredPropertys) {
		this.scatteredPropertys = scatteredPropertys;
	}
}
