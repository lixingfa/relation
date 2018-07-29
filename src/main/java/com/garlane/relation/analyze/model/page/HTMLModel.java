package com.garlane.relation.analyze.model.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.model.el.ELModel;

public class HTMLModel implements Serializable{
	private static final long serialVersionUID = 1L;

	private String path;
	private List<String> jsSrc = null;
	
	/**页面的a标签，后面往往会跟随BL，表示该锚点跳转的页面里希望实现的功能*/
	private List<AModel> aModels = new ArrayList<AModel>();
	
	/**表格，input和select元素的集合，被form包围的table不在此列*/
	private List<TableModel> tableModels = null;
	
	/**表单，input和select元素的集合，有一个提交地址，可以推测一个请求*/
	private List<FormModel> formModels = null;
	
	/**BL语言的占位符，表示希望实现的功能*/
	private List<BLModel> bls = null;
	
	/**easyui相关的有用信息*/
	private EASYUIModel easyuiModel = null;
	
	/**EL表达式进入这个页面时就已经组装好的数据。一个EL就代表一个后台传过来的属性，有子属性的表示一个集合类。EL经常与table和form混在一起*/
	private List<ELModel> elModels = null;
	private List<String> elModelsClass;
	
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

	public List<TableModel> getTableModels() {
		return tableModels;
	}

	public void setTableModels(List<TableModel> tableModels) {
		this.tableModels = tableModels;
	}

	public List<String> getElModelsClass() {
		return elModelsClass;
	}

	public void setElModelsClass(List<String> elModelsClass) {
		this.elModelsClass = elModelsClass;
	}
	
}
