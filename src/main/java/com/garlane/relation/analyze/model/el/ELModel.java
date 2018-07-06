package com.garlane.relation.analyze.model.el;

import java.io.Serializable;

import com.garlane.relation.common.utils.change.StringUtil;

/**
 * EL表达式里的属性，代表变量.通常会用.分隔，也有的没有点，或多个点
 * @author lingxingfa
 *
 */
public class ELModel implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String parentId;
	/**本身的名称*/
	private String name;
	/**中文名称*/
	private String title;
	
	public ELModel(String name){
		this.id = StringUtil.getUUID();
		this.name = name;
	}
	/****************************/
	public String getId() {
		return id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
}
