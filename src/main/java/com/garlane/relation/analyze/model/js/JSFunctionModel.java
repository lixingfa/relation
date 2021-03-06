/**
 * 
 */
package com.garlane.relation.analyze.model.js;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.garlane.relation.analyze.model.easyui.ActionModel;

/**解析EASYUI内置函数时的装载实体
 * @author lixingfa
 * @date 2018年7月5日上午10:34:35
 * 
 */
public class JSFunctionModel implements Serializable,Comparable<JSFunctionModel>{
	private static final long serialVersionUID = 1L;

	/**函数的内容，从function(开始*/
	private String functionString;
	/**函数触发的链接，一般是一个，也有两个的*/
	private List<ActionModel> actionModels;
	
	public JSFunctionModel(String functionString){
		this.functionString = functionString;
	}

	/****************************************/
	public String getFunctionString() {
		return functionString;
	}
	public List<ActionModel> getActionModels() {
		return actionModels;
	}
	public void setActionModels(List<ActionModel> actionModels) {
		this.actionModels = actionModels;
	}
	
	//实现Collections.sort()排序
	@Override
	public int compareTo(JSFunctionModel jsFunctionModel) {
		return jsFunctionModel.getFunctionString().length() - functionString.length();//降序，反过来就是升序
	}
}
