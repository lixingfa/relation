package com.garlane.relation.common.model.tree;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 
 * @author zhouwx
 * @date 2017年11月9日 下午4:13:30
 * @param 
 */
public class Node<T extends TreeModel<T>> extends TreeModel<T> implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<T> treeModels = new LinkedList<T>();
	
	private File file;
	private Node<T> parNode = null;
	private List<Node<T>> childNodes = new LinkedList<Node<T>>();
	private Integer readIndex = 0;
	private Boolean abandon = new Boolean(false);

//	private Node<T> baseNode =  null;
	
//	private T content;
	
	final public File getFile() {
		return file;
	}
	final public void setFile(File file) {
		this.file = file;
	}
	final public Node<T> getParNode() {
		return parNode;
	}
	final public void setParNode(Node<T> parNode) {
		this.parNode = parNode;
	}
	final public List<Node<T>> getChildNodes() {
		return childNodes;
	}
	final public void setChildNodes(List<Node<T>> childNodes) {
		this.childNodes = childNodes;
	}
	final public Integer getReadIndex() {
		return readIndex;
	}
	final public void setReadIndex(Integer readIndex) {
		this.readIndex = readIndex;
	}
//	final public T getContent() {
//		return content;
//	}
//	final public void setContent(T content) {
//		this.content = content;
//	}
//	final public Node<T> getBaseNode() {
//		return baseNode;
//	}
//	final public void setBaseNode(Node<T> baseNode) {
//		this.baseNode = baseNode;
//	}
	public Boolean getAbandon() {
		return abandon;
	}
	public void setAbandon(Boolean abandon) {
		this.abandon = abandon;
	}
	public List<T> getTreeModels() {
		return treeModels;
	}
	public void setTreeModels(List<T> treeModels) {
		this.treeModels = treeModels;
	}
	
	
}
