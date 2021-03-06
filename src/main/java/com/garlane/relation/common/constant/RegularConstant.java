/**
 * 
 */
package com.garlane.relation.common.constant;

import java.util.List;

import com.garlane.relation.common.utils.change.StringUtil;

/**正则表达式常量
 * @author lixingfa
 * @date 2018年7月20日下午5:20:27
 * 
 */
public class RegularConstant {
	public static final String HTML = "</?[^>]+/?>";
	/**EL*/
	public static final String EL = "\\$\\{[ a-zA-Z.]+\\}";
	/**JSTL*/
	public static final String JSTL = "<c:[\\w ='\"${}.>=<]+>|</c:[\\w]+>";
	public static final String JSTL_END_BEGIN = "</c:[\\w]+>[ \n\t]*<c:[\\w ='\"${}.>=<]+>";
	/**url定义*/
	public static final String URL_DEF = "(url)?[ \n\t]*[=:]?[ \n\t]*['\"]{1}([${}\\w]+/)+[\\w]+[.]?[\\w]*['\"?=+&${}\\w.]*";//后面一段没有逗号，可以避免越界
	/**url用变量赋值*/
	public static final String URL_VARIABLE = "url[ \n\t]*:[ \n\t]*[\\w]+[ \n\t]*[,]?";//url变量
	/**+号，用于字符串链接*/
	public static final String PLUS_SIGN = "['\"]?[ \n\t]*[\\+]{1}[ \n\t]*['\"]?";
	/**EASYUI js方式定义*/
	public static final String EASYUI_JS_DEF = "\\$\\([ \n\t]*['\"]{1}#[\\w]+['\"]{1}[ \n\t]*\\)\\.";
	public static final String GRID_DEF = EASYUI_JS_DEF + "(tree|data|combo){1}grid\\([ \n\t]*\\{";
	public static final String TREE_DEF = EASYUI_JS_DEF + "(combo)?tree\\([ \n\t]*\\{";//$(\"#areaSeq\").combobox('getValue')
	public static final String EASYUI_GETVALUE = EASYUI_JS_DEF + "[\\w]+\\([ \n\t]*['\"]{1}getValue['\"]{1}[ \n\t]*\\)";
	/**EASYUI变量定义*///变量也能以 $ 和 _ 符号开头，只是不推荐。注意不匹配truefalse的写法  'dictionarySeq': datagridTable.loadControlSeq("dataGridTable")
	public static final String EASYUI_PROPERTY_VARIABLE = "[:]{1}[ ]*[_$]?(?!(true|false))[a-zA-Z]{1}[a-zA-Z0-9.()'\"]+[ ]*[,]?";
	/**js*/
	public static final String AJAX_DEF = "\\$\\.ajax[\\w]*[ \n\t]*\\([ \n\t]*\\{";
	public static final String JS_FUNCTION_DEF = "function[ \n\t]*\\([\\w, ]*\\)";
	public static final String JQ_VALUE = EASYUI_JS_DEF + "val\\([ \n\t]*\\)";
	public static final String TYPE = "type[ \n\t]*:";
	public static final String DATA = "data[ \n\t]*:[ \n\t]*\\{";
	
	/*
	(?!abc) 的意思是 ， 不匹配abc
	那么((?!abc).)*  的意思就是匹配  不含abc的字符(?!abc) 跟 任意字符. 的组合，出现任何次* 
	((?!abc).)* 的结果就是 匹配了不包含abc的字符串
	加上首尾  ^((?!abc).)*$ 表示匹配一行
	 */
	
	public static void main(String[] args) {
		String content = "{field:'bookCode',title:'字典值<span style=\"color:red\"> * </span>',width:120,align: 'center',editor:'text'},";
		System.out.println(StringUtil.getMatchers(HTML, content).size());
	}
}
