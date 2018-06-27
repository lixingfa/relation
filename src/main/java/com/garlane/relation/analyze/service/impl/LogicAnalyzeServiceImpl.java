package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.logic.PropertyModel;
import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.FormModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.service.LogicAnalyzeService;
import com.garlane.relation.common.utils.exception.SuperServiceException;
@Service("logicAnalyzeService")
public class LogicAnalyzeServiceImpl implements LogicAnalyzeService{

	private Logger log = Logger.getLogger(getClass());
	/**
	 * 获取业务逻辑
	 * LogicAnalyze:(这里用一句话描述这个方法的作用)
	 * @author lixingfa
	 * @date 2018年6月21日下午5:55:34
	 * @param htmlModels 页面元素集合
	 * @param jsBLModels JS业务语言集合
	 * @throws SuperServiceException
	 */
	public void LogicAnalyze(List<HTMLModel> htmlModels,List<BLModel> jsBLModels)throws SuperServiceException{
		log.info("处理BL语言");//BL语言里有很多属性，先获取它们
		
		
		log.info("处理form");//同一个form表示一起完成一个事务，查询、修改比较多
		List<FormModel> formModels = new ArrayList<FormModel>();
		
	}
	
	/**
	 * 
	 * @param htmlModels
	 * @param jsBLModels
	 * @return
	 * @throws SuperServiceException
	 */
	private List<PropertyModel> getPropertyModels(List<HTMLModel> htmlModels,List<BLModel> jsBLModels) throws SuperServiceException{
		
		List<PropertyModel> propertyModels = new ArrayList<PropertyModel>();
		//从BL里获取属性，一起出现的亲密度+1
		
		//同一个页面里的属性,同一个表单里的属性，同一个BL的属性……
		//大集合包含小集合
		//要定义一个数据结构来装这个集合，然后再根据集合做成二维关系亲密度表
		return propertyModels;
	}
	//从表单获取属性
	//从datagrigd获取属性
	//从BL获取属性
	//把小集合放到页面集合里，把页面集合放到文件夹里。
}
