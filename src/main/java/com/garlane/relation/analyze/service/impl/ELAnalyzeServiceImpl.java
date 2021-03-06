package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.el.ELModel;
import com.garlane.relation.analyze.service.ELAnalyzeService;
import com.garlane.relation.common.constant.ELConstant;
import com.garlane.relation.common.constant.RegularConstant;
import com.garlane.relation.common.utils.change.StringUtil;
import com.garlane.relation.common.utils.exception.SuperServiceException;

@Service("elAnalyzeService")
public class ELAnalyzeServiceImpl implements ELAnalyzeService{
	
	/**
	 * analyze:(提取页面内容中的el属性)
	 * @author lixingfa
	 * @date 2018年7月4日下午6:20:41
	 * @param content 页面内容
	 * @return List<ELModel> 整理好层级关系的el属性
	 * @throws SuperServiceException
	 */
	public List<ELModel> analyze(String content) throws SuperServiceException{
		List<ELModel> elModels = new ArrayList<ELModel>();
		List<String> els = StringUtil.getMatchers(RegularConstant.EL, content);
		for (String el : els) {
			String[] propertys = extractProperty(el);
			getElModels(elModels,propertys);
		}
		return elModels;
	}
	
	/**
	 * getElModels:(从属性列表里组装EL集合)
	 * @author lixingfa
	 * @date 2018年7月5日下午7:30:12
	 * @param propertys EL表达式以.切割后的数组，前面的是后面的父属性
	 * @return
	 * @throws SuperServiceException
	 */
	public List<ELModel> getElModels(List<ELModel> elModels,String[] propertys) throws SuperServiceException{
		String parentId = null;
		if (elModels == null) {
			elModels = new ArrayList<ELModel>();
		}
		for (int i = 0; i < propertys.length; i++) {
			String id = getELId(elModels, propertys[i]);
			if (id != null) {
				parentId = id;
				continue;
			}else {
				ELModel elModel = new ELModel(propertys[i]);
				elModel.setParentId(parentId);
				elModels.add(elModel);
				//第一个才是空的
				parentId = elModel.getId();
			}
		}
		return elModels;
	}
	
	@Override
	public List<ELModel> getElModels(String[] propertys) throws SuperServiceException {
		return getElModels(null, propertys);
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
				.replace(ELConstant.OPEN_BRACE, "")
				.trim();
		//去除表达式等
		//TODO 需要完善
		if (el.contains(ELConstant.EQ)) {
			String[] temp = el.split(ELConstant.EQ);
			String els = "";
			for (String s : temp) {
				s = s.trim();
				if (!Pattern.matches(s, "\\d")) {//非数字，就是字符串或变量
					if (s.contains("'")) {//字符串
						
					}else {//变量
						els = els + "." + s;//可能两个都是变量
					}
				}
			}
			return els.split(".");
		}
		return el.split("\\.");
	}
}
