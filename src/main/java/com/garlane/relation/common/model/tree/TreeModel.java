package com.garlane.relation.common.model.tree;


import java.io.Serializable;

/**
 *	导航js解析对象
 *	@author hefule
 *	@date 2017年1月16日 下午7:06:00
 *
 */
public class TreeModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private String state;
	private String icon;
	private String href;
	
	/*************************/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
}
