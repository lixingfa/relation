package com.garlane.relation.analyze.model.easyui;

import java.util.Map;

/**
 * 请求实体
 * @author lingxingfa
 *
 */
public class ActionModel {
	/**请求类型，0get，1post*/
	private Integer reqType = REQ_TYPE_GET;
	/**请求地址*/
	private String url   ;
	/**参数,一般是字符串或数字，都可以统一转换成字符串*/
	private Map<String, String> params   ;
	/**返回结果类型，0页面地址，代码里的路径，1JSON*/
	private Integer resultType;
	/**返回结果，代码里的路径或JSON结果的名称，没有值则表为外部链接*/
	private String results   ;
	
	public static Integer REQ_TYPE_GET = 0;
	public static Integer REQ_TYPE_POST = 0;
	public static Integer RESULT_TYPE_PAGE = 0;
	public static Integer RESULT_TYPE_JSON = 1;
	public static Integer RESULT_TYPE_OUTWEB = 2;
	
	public ActionModel(String url){
		this.url = url;
	}
	
	/*******************************************/
	public Integer getReqType() {
		return reqType;
	}
	public void setReqType(Integer reqType) {
		this.reqType = reqType;
	}
	public String getUrl() {
		return url;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public Integer getResultType() {
		return resultType;
	}
	public void setResultType(Integer resultType) {
		this.resultType = resultType;
	}
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	
}
