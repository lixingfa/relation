/**
 * 
 */
package com.garlane.relation.common.model.page;

/**网页的a标签
 * @author lixingfa
 * @date 2018年6月12日下午3:48:18
 * 
 */
public class AModel {
	public AModel(String href,String text){
		this.href = href;
		this.text = text;
	}
	
	private String href;
	private String text;
	private String BL;
	
	/**********************/
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getBL() {
		return BL;
	}
	public void setBL(String bL) {
		BL = bL;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
