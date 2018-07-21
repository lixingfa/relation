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
	/**EL*/
	public static final String EL = "\\$\\{[ a-zA-Z.]+\\}";
	/**JSTL*/
	public static final String JSTL = "<c:[\\w =\"${}.>=<]+>|</c:[\\w]+>";
	/**js*/
	public static final String AJAX_DEF = "\\$\\.ajax\\([ ]*\\{";
	public static final String JS_FUNCTION_DEF = "function\\([\\w, ]*\\)";
	/**url定义*/
	public static final String URL_DEF = "[url]?[ ]*[=:]?[ ]*['\"]{1}([${}\\w]+/)+[\\w]+[.]?[\\w]*['\"?=+&${}\\w.]*";//后面一段没有逗号，可以避免越界
	/**url用变量赋值*/
	public static final String URL_VARIABLE = "url[ ]*:[ ]*[\\w]+[ ]*[,]?";//url变量
	/**+号，用于字符串链接*/
	public static final String PLUS_SIGN = "['\"]?[ ]*[\\+]{1}[ ]*['\"]?";
	/**EASYUI js方式定义*/
	public static final String EASYUI_JS_DEF = "\\$\\([ ]*['\"]{1}#[\\w]+['\"]{1}[ ]*\\)\\.";
	public static final String GRID_DEF = EASYUI_JS_DEF + "(tree|data|combo){1}grid\\([ ]*\\{";
	public static final String TREE_DEF = EASYUI_JS_DEF + "(combo)?tree\\([ ]*\\{";//?变*

	public static void main(String[] args) {
		String content = "'userRole':'${userModel.userRole}',						<c:if test=\"${showSelectAreaBtn}\">							'areaSeq': $(\"#areaSeq\").combobox('getValue')						</c:if>					}				});			},			/* 清空 */			doClean:function(){				$('#userCode').val(\"\");				$('#userName').val(\"\");				<c:if test=\"${showSelectAreaBtn}\">					$(\"#areaSeq\").combotree(\"setValue\",\"\");				</c:if>				if (isSelectUnit) {				    if (unitName != 'null' &&  unitName != '') {";
		System.out.println(StringUtil.replaceMatchers(JSTL, "", content));
	}
}
