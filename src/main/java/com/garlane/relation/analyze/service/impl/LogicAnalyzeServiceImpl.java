package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.analyze.model.easyui.ActionModel;
import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.model.easyui.GridModel;
import com.garlane.relation.analyze.model.easyui.TreeModel;
import com.garlane.relation.analyze.model.el.ELModel;
import com.garlane.relation.analyze.model.logic.PropertyIntimacyModel;
import com.garlane.relation.analyze.model.logic.PropertyModel;
import com.garlane.relation.analyze.model.logic.PropertyTreeModel;
import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.FormModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.service.LogicAnalyzeService;
import com.garlane.relation.common.constant.EASYUIConstant;
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

		for (HTMLModel htmlModel : htmlModels) {
			//1、处理EL表达式
			getPropertyModelsOfEL(htmlModel.getElModels());
			//2、easyuiModel
			getPropertyModelsOfEASYUI(htmlModel.getEasyuiModel());
			//3、BL
			
		}
		
		List<PropertyModel> propertyModels = new ArrayList<PropertyModel>();
		return propertyModels;
	}
	
	/**
	 * getPropertyModelsOfEL:(这里用一句话描述这个方法的作用)
	 * @author lixingfa
	 * @date 2018年7月10日上午11:27:20
	 * @param elModels
	 */
	private void getPropertyModelsOfEL(List<ELModel> elModels){
		if (elModels != null) {
			List<String> propertyList = new ArrayList<String>();
			for (ELModel elModel : elModels) {
				propertyList.add(elModel.getName());
			}
			PropertyIntimacyModel.getInstance().addImpeachs(propertyList);
		}
	}
	
	/**
	 * getPropertyModelsOfEASYUI:(从easyui中获取属性)
	 * @author lixingfa
	 * @date 2018年7月10日上午11:40:58
	 * @param easyuiModel
	 */
	private void getPropertyModelsOfEASYUI(EASYUIModel easyuiModel){
		List<String> propertyList = new ArrayList<String>();
		List<TreeModel> treeModels = easyuiModel.getTreeModels();
		if (treeModels != null) {
			for (TreeModel treeModel : treeModels) {
				getPropertyModelsOfTreeModel(treeModel, propertyList);
			}
		}
		List<GridModel> gridModels = easyuiModel.getGridModels();
		if (gridModels != null) {
			for (GridModel gridModel : gridModels) {
				getPropertyModelsOfTreeModel(gridModel, propertyList);
				//处理datagrid特有的dataObject
				JSONObject dataObject = gridModel.getDataObject();
				if (dataObject != null) {
					JSONArray array = dataObject.getJSONArray(EASYUIConstant.ROWS);
					if (array.size() > 0) {
						JSONObject object = array.getJSONObject(0);
						propertyList.addAll(object.keySet());
					}
				}
			}
		}
	}
	
	/**
	 * getPropertyModelsOfTreeModel:(从TreeModel中获取属性)
	 * @author lixingfa
	 * @date 2018年7月10日上午11:40:39
	 * @param treeModel
	 * @param propertyList
	 */
	private void getPropertyModelsOfTreeModel(TreeModel treeModel,List<String> propertyList){
		JSONArray dataArray = treeModel.getDataArray();
		if (dataArray != null) {
			//每一条数据的格式都是一样的，分析一条即可
			JSONObject object = dataArray.getJSONObject(0);
			propertyList.addAll(object.keySet());
		}
		getPropertyModelsOfActionModel(treeModel, propertyList);
	}
	
	/**
	 * getPropertyModelsOfActionModel:(从ActionModel中获取属性)
	 * @author lixingfa
	 * @date 2018年7月10日下午12:00:18
	 * @param treeModel
	 * @param propertyList
	 */
	private void getPropertyModelsOfActionModel(TreeModel treeModel,List<String> propertyList){
		List<ActionModel> actionModels = treeModel.getActionModels();
		if (actionModels != null) {
			for (ActionModel actionModel : actionModels) {
				if (actionModel.getParams() != null) {
					propertyList.addAll(actionModel.getParams().keySet());
				}
				//处理action的结果
				getPropertyModelsOfEL(actionModel.getElModels());
			}
		}
	}
	
	/**
	 * getPropertyModelsOfBL:(从业务语言中获取属性)
	 * @author lixingfa
	 * @date 2018年7月10日下午2:05:45
	 * @param treeModel
	 * @param propertyList
	 */
	private void getPropertyModelsOfBL(List<BLModel> htmlBls,List<BLModel> jsBls){
		
	}
}
