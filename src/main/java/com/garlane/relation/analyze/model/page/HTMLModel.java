package com.garlane.relation.analyze.model.page;

import java.util.ArrayList;
import java.util.List;

import com.garlane.relation.analyze.model.easyui.EASYUIModel;

public class HTMLModel {

	private String path;
	private List<String> jsSrc = new ArrayList<String>();
	private List<AModel> aModels = new ArrayList<AModel>();
	private List<FormModel> formModels = new ArrayList<FormModel>();
	private List<BLModel> bls = new ArrayList<BLModel>();
	
	private EASYUIModel easyuiModel;
	
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
	
}
