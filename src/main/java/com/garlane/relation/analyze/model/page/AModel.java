/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;

/**网页的a标签
 * @author lixingfa
 * @date 2018年6月12日下午3:48:18
 * 
 */
public class AModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String href;
	private String text;
	private String BL;
	
	public AModel(String href,String text){
		this.href = href;
		this.text = text;
	}
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
