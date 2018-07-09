package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.el.ELModel;
import com.garlane.relation.analyze.model.logic.PropertyModel;
import com.garlane.relation.analyze.model.logic.PropertyTreeModel;
import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.FormModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.service.LogicAnalyzeService;
import com.garlane.relation.common.utils.exception.SuperServiceException;
@Service("logicAnalyzeService")
public class LogicAnalyzeServiceImpl implements LogicAnalyzeService{

	private Logger log = Logger.getLogger(getClass());
	/**
	 * LogicAnalyze:(web系统里，所有的行为都会被简化成请求-获取)
	 * @author lixingfa
	 * @date 2018年6月21日下午5:55:34
	 * @param htmlModels 页面元素集合
	 * @param jsBLModels JS业务语言集合
	 * @throws SuperServiceException
	 */
	public void LogicAnalyze(List<HTMLModel> htmlModels,List<BLModel> jsBLModels)throws SuperServiceException{
		//1、先将结果归类
		log.info("处理BL语言");//BL语言里有很多属性，先获取它们
		
		getPropertyModels(htmlModels, jsBLModels);
		//2、将这些归类好的结果转成action，确定各action之间的关系
		
		//3、构建请求逻辑网络
		
		log.info("处理form");//同一个form表示一起完成一个事务，查询、修改比较多
		List<FormModel> formModels = new ArrayList<FormModel>();
		
	}
	
	/**
	 * getPropertyModels:(把零散的信息组装成有层次的信息)
	 * @author lixingfa
	 * @date 2018年7月9日下午4:32:58
	 * @param htmlModels
	 * @param jsBLModels
	 * @return
	 * @throws SuperServiceException
	 */
	private List<PropertyModel> getPropertyModels(List<HTMLModel> htmlModels,List<BLModel> jsBLModels) throws SuperServiceException{
		List<PropertyTreeModel> propertyTreeModels = new ArrayList<PropertyTreeModel>(htmlModels.size());
		for (HTMLModel htmlModel : htmlModels) {
			PropertyTreeModel propertyTreeModel = new PropertyTreeModel(null);
			//1、处理EL表达式
			PropertyTreeModel elPropertyTree = new PropertyTreeModel(propertyTreeModel.getParentId());
			List<ELModel> elModels = htmlModel.getElModels();
			for (ELModel elModel : elModels) {
				if (condition) {//这算是好办法吗？
					
				}
			}
			
			propertyTreeModels.add(propertyTreeModel);
		}
		
		List<PropertyModel> propertyModels = new ArrayList<PropertyModel>();
		return propertyModels;
	}
	
	
	
}
