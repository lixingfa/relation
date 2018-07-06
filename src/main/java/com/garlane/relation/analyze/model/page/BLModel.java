/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;

/**
 * @author lixingfa
 * @date 2018年6月12日下午3:57:35
 * 
 */
public class BLModel implements Serializable{
	private static final long serialVersionUID = 1L;

	private String bl;
	private String text;
	
	public BLModel(String bl,String text){
		this.bl = bl;
		this.text = text;
	}
	
	/********************/
	public String getBl() {
		return bl;
	}
	public void setBl(String bl) {
		this.bl = bl;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
