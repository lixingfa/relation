package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.el.ELModel;
import com.garlane.relation.analyze.service.ELAnalyzeService;
import com.garlane.relation.common.utils.exception.SuperServiceException;

@Service("elAnalyzeService")
public class ELAnalyzeServiceImpl implements ELAnalyzeService{
	private static final String EL = "${[ a-zA-Z.]+}";
	
	public void analyze(String content) throws SuperServiceException{
		List<ELModel> elModels = new ArrayList<ELModel>();
		Pattern pattern = Pattern.compile(EL);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String s = matcher.group();
			s = s.replace("${", "").replace(" ", "").replace("}", "");
			String[] el = s.split(".");
			String parentId = null;
			for (int i = 0; i < el.length; i++) {
				String id = getELId(elModels, el[i]);
				if (id != null) {
					parentId = id;
					continue;
				}else {
					ELModel elModel = new ELModel(el[i]);
					elModel.setParentId(parentId);
					elModels.add(elModel);
				}
			}
		}
	}
	
	private String getELId(List<ELModel> elModels,String el){
		for (ELModel elModel : elModels) {
			if(elModel.getName().equals(el)){
				return elModel.getId();
			}
		}
		return null;
	}
}
