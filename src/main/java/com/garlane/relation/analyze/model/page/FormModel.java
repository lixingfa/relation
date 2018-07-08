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
	//表单提交一般只是要成果或失败的结果，或者查询……
	/*********************************/
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
