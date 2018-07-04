package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.el.ELModel;
import com.garlane.relation.analyze.service.ELAnalyzeService;
import com.garlane.relation.common.constant.ELConstant;
import com.garlane.relation.common.utils.exception.SuperServiceException;

@Service("elAnalyzeService")
public class ELAnalyzeServiceImpl implements ELAnalyzeService{
	private static final String EL = "${[ a-zA-Z.]+}";
	
	public void analyze(String content) throws SuperServiceException{
		List<ELModel> elModels = new ArrayList<ELModel>();
		Pattern pattern = Pattern.compile(EL);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String el = matcher.group();
			String[] propertys = extractProperty(el);
			String parentId = null;
			for (int i = 0; i < propertys.length; i++) {
				String id = getELId(elModels, propertys[i]);
				if (id != null) {
					parentId = id;
					continue;
				}else {
					ELModel elModel = new ELModel(propertys[i]);
					elModel.setParentId(parentId);
					elModels.add(elModel);
				}
			}
		}
	}
	
	/**
	 * getELId:(获取EL属性的id)
	 * @author lixingfa
	 * @date 2018年7月4日上午10:37:49
	 * @param elModels
	 * @param el
	 * @return String
	 */
	private String getELId(List<ELModel> elModels,String el){
		for (ELModel elModel : elModels) {
			if(elModel.getName().equals(el)){
				return elModel.getId();
			}
		}
		return null;
	}
	
	/**
	 * extractProperty:(从EL表达式中提取属性)
	 * @author lixingfa
	 * @date 2018年7月4日上午10:52:15
	 * @param el EL表达式 
	 * @return String[] 属性字符串组，一般是model名称、属性名称的组合 
	 */
	private String[] extractProperty(String el){
		//去除两边引号
		el = el.replace(ELConstant.CLOSE_BRACE, "")
				.replace(ELConstant.DOLLAR_MARK, "")
				.replace(ELConstant.OPEN_BRACE, "");
		//去除表达式等
		//TODO 需要完善
		if (el.contains(ELConstant.EQ)) {
			String[] temp = el.split(ELConstant.EQ);
			//变量与常量比较，取变量，可以了解到常量的取值
			//变量与变量，两个都取
		}
		return el.split(".");
	}
}
