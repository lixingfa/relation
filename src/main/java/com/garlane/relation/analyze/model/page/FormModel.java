/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixingfa
 * @date 2018年6月11日下午5:29:52
 * 
 */
public class FormModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	List<InputModel> inputs;

	List<SelectModel> selectModels;
	/*********************************/
	public List<InputModel> getInputs() {
		return inputs;
	}

	public void setInputs(List<InputModel> inputs) {
		this.inputs = inputs;
	}
}
