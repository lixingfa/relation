/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;

/**
 * @author lixingfa
 * @date 2018年6月11日下午5:29:52
 * 
 */
public class FormModel extends TableModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String url;
	/*********************************/
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
