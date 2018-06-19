/**
 * 
 */
package com.garlane.relation.analyze.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixingfa
 * @date 2018年6月11日下午5:29:52
 * 
 */
public class FormModel {
	
	List<InputModel> inputs = new ArrayList<InputModel>();

	/*********************************/
	public List<InputModel> getInputs() {
		return inputs;
	}

	public void setInputs(List<InputModel> inputs) {
		this.inputs = inputs;
	}
}
