/**
 * 
 */
package com.garlane.relation.analyze.model.easyui;

/**datagrid中的列，属性很多，但与后台相关的不多
 * @author lixingfa
 * @date 2018年7月3日下午2:22:44
 * 
 */
public class Column {

	/**列标题文本*/
	private String title;
	/**列字段名称*/
	private String field;
	
	public Column(String title,String field){
		this.title = title;
		this.field = field;
	}
	
	/************************/
	public String getTitle() {
		return title;
	}
	public String getField() {
		return field;
	}
}
