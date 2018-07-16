/**
 * 
 */
package com.garlane.relation.analyze.model.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixingfa
 * @date 2018年7月7日下午4:15:57
 * 
 */
public class TableModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	List<InputModel> inputs;

	List<SelectModel> selectModels;
	
	List<TextareaModel> textareaModels;
	/*********************************/
	public List<InputModel> getInputs() {
		return inputs;
	}

	public void setInputs(List<InputModel> inputs) {
		this.inputs = inputs;
	}

	public List<SelectModel> getSelectModels() {
		return selectModels;
	}

	public void setSelectModels(List<SelectModel> selectModels) {
		this.selectModels = selectModels;
	}

	public List<TextareaModel> getTextareaModels() {
		return textareaModels;
	}

	public void setTextareaModels(List<TextareaModel> textareaModels) {
		this.textareaModels = textareaModels;
	}
	
}
