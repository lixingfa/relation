package com.garlane.relation.analyze.model.page;

import java.util.ArrayList;
import java.util.List;

public class JSModel {

	private String url;
	private List<BLModel> bls = new ArrayList<BLModel>();
	
	/*************************/
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<BLModel> getBls() {
		return bls;
	}
	public void setBls(List<BLModel> bls) {
		this.bls = bls;
	}
}
