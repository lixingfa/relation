package com.garlane.relation.common.model.tree;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *	导航js解析对象
 *	@author hefule
 *	@date 2017年1月16日 下午7:06:00
 *
 */
public class TreeModel<T extends TreeModel<T>> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private String icon;
	private String href;
	private String home_page;
	private List<T> menu = new ArrayList<T>(); //用于主界面的菜单
	private List<T> children = new ArrayList<T>(); //用户菜单管理
	private String message;
	private boolean closeable;  //用于主界面的菜单
	private String state;   //用户菜单管理
	private String parId;
	private int level;
	
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
	public String getHome_page() {
		return home_page;
	}
	public void setHome_page(String homePage) {
		home_page = homePage;
	}
	
	public List<T> getMenu() {
		return menu;
	}
	public void setMenu(List<T> menu) {
		this.menu = menu;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isCloseable() {
		return closeable;
	}
	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}
	public String getParId() {
		return parId;
	}
	public void setParId(String parId) {
		this.parId = parId;
	}
	public List<T> getChildren() {
		return children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
