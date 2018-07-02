/**
 * 
 */
package com.garlane.relation.analyze.model.logic;

import java.util.ArrayList;
import java.util.List;

/**事务，属性分布树的二维图，便于分组与建表
 * @author lixingfa
 * @date 2018年6月21日下午7:10:29
 * 
 */
public class TransactionModel {

	/**可以确定的属性*/
	private List<PropertyModel> propertyModels = new ArrayList<PropertyModel>();
	
	/**存疑的属性，比如差了一个字母，怀疑与其他的是同一个，返回前端让设计人员审核*/
	private List<PropertyModel> impeachPropertyModels = new ArrayList<PropertyModel>();
	
	/**属性间的亲密度，二维数组的长度由最终确定的属性个数决定
	 * 同在一个表单里，亲密度+1
	 * 要查a与c的亲密度，[a的位置][c的位置]
	 * 查c与a的亲密度[c的位置][a的位置]*/
	private int[][] intimacy;

	/************************************/
	public List<PropertyModel> getPropertyModels() {
		return propertyModels;
	}

	public void setPropertyModels(List<PropertyModel> propertyModels) {
		this.propertyModels = propertyModels;
	}

	public List<PropertyModel> getImpeachPropertyModels() {
		return impeachPropertyModels;
	}

	public void setImpeachPropertyModels(List<PropertyModel> impeachPropertyModels) {
		this.impeachPropertyModels = impeachPropertyModels;
	}

	public int[][] getIntimacy() {
		return intimacy;
	}

	public void setIntimacy(int[][] intimacy) {
		this.intimacy = intimacy;
	}
}
