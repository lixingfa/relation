package com.garlane.relation.analyze.model.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.model.el.ELModel;

public class HTMLModel implements Serializable{
	private static final long serialVersionUID = 1L;

	private String path;
	private List<String> jsSrc;
	private List<AModel> aModels;
	private List<FormModel> formModels;
	private List<BLModel> bls;
	
	private EASYUIModel easyuiModel;
	private List<ELModel> elModels;
	public HTMLModel(String path){
		this.path = path;
	}
	
	/*************************************/
	public List<BLModel> getBls() {
		return bls;
	}
	public void setBls(List<BLModel> bls) {
		this.bls = bls;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getJsSrc() {
		return jsSrc;
	}
	public void setJsSrc(List<String> jsSrc) {
		this.jsSrc = jsSrc;
	}
	public List<AModel> getaModels() {
		return aModels;
	}
	public void setaModels(List<AModel> aModels) {
		this.aModels = aModels;
	}
	public List<FormModel> getFormModels() {
		return formModels;
	}
	public void setFormModels(List<FormModel> formModels) {
		this.formModels = formModels;
	}
	public List<BLModel> getABLs(){
		List<BLModel> blModels = new ArrayList<BLModel>();
		for (AModel aModel : aModels) {
			blModels.add(new BLModel(null, aModel.getBL()));
		}
		return blModels;
	}

	public EASYUIModel getEasyuiModel() {
		return easyuiModel;
	}

	public void setEasyuiModel(EASYUIModel easyuiModel) {
		this.easyuiModel = easyuiModel;
	}

	public List<ELModel> getElModels() {
		return elModels;
	}

	public void setElModels(List<ELModel> elModels) {
		this.elModels = elModels;
	}
	
}
