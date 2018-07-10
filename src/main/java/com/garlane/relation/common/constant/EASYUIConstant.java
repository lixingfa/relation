/**
 * 
 */
package com.garlane.relation.common.constant;

/**
 * @author lixingfa
 * @date 2018年7月5日上午10:57:56
 * 
 */
public class EASYUIConstant {

	//EASYUI 用到的常量字符，每个Constant各司其职，只关注自身，避免使用的时候混用
	public static final String URL = "url";
	//通用
	public static final String AJAX_BEGIN = "\\$\\.ajax\\(\\{";
	
	//grid
	public static final String GRID_DEF = "\\$\\([ ]?['\"]{1}[ ]?#\\w[ ]?['\"]{1}[ ]?\\)\\.[tree|data|combo]{1}grid\\([ ]?\\{";
	public static final String TREE_FIELD = "treeField";
	public static final String ID_FIELD = "idField";
	public static final String COLUMNS = "columns";
	public static final String FIELD = "field";
	public static final String TITLE = "title";
	public static final String CHECKBOX = "checkbox";
	public static final String DATA = "data";
	public static final String ROWS = "rows";
	
	//tree 在请求远程数据的时候增加查询参数
	public static final String TREE_DEF = "\\$\\([ ]?['\"]{1}[ ]?#\\w[ ]?['\"]{1}[ ]?\\)\\.[combo]?tree\\([ ]?\\{";//完全是为了预防奇葩写法
	public static final String QUERYPARAMS = "queryParams";
	
}
