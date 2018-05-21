package com.garlane.relation.common.model.tree;


public class WebConfigTreeModel extends TreeModel<WebConfigTreeModel>{
	private static final long serialVersionUID = 1L;
	
	private String absolutePath;
	private String relativePath;
	
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
}
