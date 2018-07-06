/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixingfa
 * @date 2018年6月21日下午5:42:57
 * 
 */
public class SelectModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	/**多选*/
	private String multiple;//可以多选
	/**key就是text**/
	private Map<String, String> option = new HashMap<String, String>();
	
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
	
}
