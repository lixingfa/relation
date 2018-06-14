/**
 * 
 */
package com.garlane.relation.common.model.page;

/**
 * @author lixingfa
 * @date 2018年6月12日下午3:57:35
 * 
 */
public class BLModel {

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
