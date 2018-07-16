/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.garlane.relation.common.constant.PageConstant;

/**
 * @author lixingfa
 * @date 2018年6月21日下午5:42:57
 * 
 */
public class SelectModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	/**下拉框的名字*/
	private String placeholder;
	/**多选*/
	private String multiple;//可以多选
	/**key就是text**/
	private Map<String, String> option = new HashMap<String, String>();
	
	public SelectModel(String id,String name){
		this.id = id;
		this.name = name;
	}
	/**********************************/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getOption() {
		return option;
	}
	public void setOption(Map<String, String> option) {
		this.option = option;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public void setPlaceholder(Element element){
		Element td = element.parent();//父节点
		Element preBrother = td.previousElementSibling();//前一个兄弟对象
		if (preBrother != null) {
			//前一个也是td且没有任何内容
			this.placeholder = preBrother.text().replace("：", "").replace(":", "");			
		}
	}
}
