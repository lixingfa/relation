package com.garlane.relation.analyze.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.analyze.model.easyui.ActionModel;
import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.model.easyui.GridModel;
import com.garlane.relation.analyze.model.easyui.TreeModel;
import com.garlane.relation.analyze.model.el.ELModel;
import com.garlane.relation.analyze.model.logic.Class;
import com.garlane.relation.analyze.model.logic.ClassProperty;
import com.garlane.relation.analyze.model.logic.PropertyIntimacyModel;
import com.garlane.relation.analyze.model.logic.PropertyModel;
import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.FormModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.model.page.InputModel;
import com.garlane.relation.analyze.model.page.SelectModel;
import com.garlane.relation.analyze.model.page.TableModel;
import com.garlane.relation.analyze.service.BLService;
import com.garlane.relation.analyze.service.LogicAnalyzeService;
import com.garlane.relation.common.constant.EASYUIConstant;
import com.garlane.relation.common.utils.change.StringUtil;
import com.garlane.relation.common.utils.exception.SuperServiceException;
import com.garlane.relation.common.utils.file.FileUtils;
@Service("logicAnalyzeService")
public class LogicAnalyzeServiceImpl implements LogicAnalyzeService{

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private BLService blService;
	/**
	 * LogicAnalyze:(web系统里，所有的行为都会被简化成请求-获取)
	 * @author lixingfa
	 * @date 2018年6月21日下午5:55:34
	 * @param htmlModels 页面元素集合
	 * @param jsBLModels JS业务语言集合
	 * @throws SuperServiceException
	 */
	public void LogicAnalyze(List<HTMLModel> htmlModels,List<BLModel> jsBLModels)throws SuperServiceException{
		//一、从EL里获取Class的组合
		List<Class> allClasses = getClasses(htmlModels);
		//二、从属性的亲密度获取表的组合
		//获取属性关系
		List<PropertyModel> propertyModels = getPropertyModels(htmlModels, jsBLModels);//经常一起出现的属性
//		
//		//1、先将结果归类
//		log.info("处理BL语言");//BL语言里有很多属性，先获取它们
//		
//		getPropertyModels(htmlModels, jsBLModels);
//		//2、将这些归类好的结果转成action，确定各action之间的关系
//		
//		//3、构建请求逻辑网络
//		
//		log.info("处理form");//同一个form表示一起完成一个事务，查询、修改比较多
//		List<FormModel> formModels = new ArrayList<FormModel>();
		
	}
	
	/**
	 * getClasses:(根据属性组装Class)
	 * @author lixingfa
	 * @date 2018年8月4日下午4:00:39
	 * @param htmlModels
	 * @return List<Class>
	 */
	private List<Class> getClasses(List<HTMLModel> htmlModels){
		List<Class> allClasses = new ArrayList<Class>();
		for (HTMLModel htmlModel : htmlModels) {
			//需要将构建的Class序列化存到服务器，用户确认后，重新读取，优化，进行下一步动作
			List<Class> classes = new ArrayList<Class>();
			//页面加载时一起返回的属性
			List<ELModel> elModels = new ArrayList<ELModel>();
			elModels.addAll(htmlModel.getElModels());
			
			//Action里的class
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			treeModels.addAll(htmlModel.getEasyuiModel().getTreeModels());
			treeModels.addAll(htmlModel.getEasyuiModel().getGridModels());
			List<ActionModel> actionModels = new ArrayList<ActionModel>();
			for (TreeModel treeModel : treeModels) {
				actionModels.addAll(treeModel.getActionModels());
			}
			for (ActionModel actionModel : actionModels) {
				if (actionModel.getElModels() != null) {
					elModels.addAll(actionModel.getElModels());					
				}
			}
			getClassesFromELModels(elModels, classes);
			//给htmlModel添加class，但页面里的可能是不全的
			htmlModel.setClasses(classes);
			//添加到总列表
			classesAdd(allClasses, classes);
		}		
		//打印，方便对比
		System.out.println("打印class，方便对比。这里是总的class，页面里的可能只是其中一部分。");
		for (Class c : allClasses) {
			//格式化，便于阅读
			String modelString = StringUtil.getJsonFormat(c.toString());
			//将对象写入文本，对比提前结果
			try {
				FileUtils.writeTxtFile(modelString, "E:/htmlModels/class/" + c.getClassName() + ".txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//
		return allClasses;
	}
	
	/**
	 * classesAdd:(给列表增加新的Class)
	 * @author lixingfa
	 * @date 2018年8月3日下午5:46:13
	 * @param allClasses
	 * @param classes
	 */
	private void classesAdd(List<Class> allClasses, List<Class> classes){
		for (Class c : classes) {
			boolean notHasClass = true;
			for (int i = 0; i < allClasses.size(); i++) {
				//类名相同
				if (c.getClassName().equals(allClasses.get(i).getClassName())) {
					notHasClass = false;
					//比较属性
					for (ClassProperty property : c.getProperties()) {
						boolean notHas = true;
						for (ClassProperty p : allClasses.get(i).getProperties()) {
							if (p.getPropertyName().equals(property.getPropertyName())) {
								notHas = false;
								break;
							}
						}
						if (notHas) {
							allClasses.get(i).getProperties().add(property);
						}
					}
				}
			}
			if (notHasClass) {
				allClasses.add(c);
			}
		}
	}
	
	/**
	 * getClassesFromELModels:(从EL表达式中提取Class)
	 * @author lixingfa
	 * @date 2018年8月3日下午5:41:21
	 * @param elModels
	 * @param classes
	 */
	private void getClassesFromELModels(List<ELModel> elModels, List<Class> classes){
		//排序，让parentId相同的在一起
//		Collections.sort(elModels);
		int index = -1;
		for (ELModel elModel : elModels) {
			String elName = elModel.getName();
			//类
			if (elModel.getParentId() == null) {
				//TODO 目前只是写死的，不合理
				if ("root".equals(elName) || "static".equals(elName) ||"css".equals(elName) ||
						"images".equals(elName) ||"js".equals(elName) ||"resource".equals(elName)) {
					continue;						
				}
				//查找是否已经了这个类
				for (int i = 0; i < classes.size();i++) {
					if (classes.get(i).getClassName().equals(elName)) {
						index = i;
						break;
					}
				}				
				//还没有这个类
				if (index == -1) {
					classes.add(new Class(elName));
					index = classes.size() - 1;
				}
			}else {
				//像data.success这种，一个页面会出现很多次
				boolean NotHas = true;
				//TODO 要确保子项是紧跟父项的
				for (ClassProperty property : classes.get(index).getProperties()) {
					if (property.getPropertyName().equals(elName)) {
						NotHas = false;
						break;
					}
				}
				if (NotHas) {
					ClassProperty property = new ClassProperty("String", elName);
					property.setTitle(elModel.getTitle());
					classes.get(index).getProperties().add(property);					
				}
			}
			
		}
		//去除空的，倒序
		for (int i = classes.size() - 1; i >= 0; i--) {
			if (classes.get(i).getProperties().size() == 0) {
				classes.remove(i);
			}
		}
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
			//blService.getPropertyModelsOfBL(htmlModels, jsBLModels);
			//4、forms
			if (htmlModel.getFormModels() != null) {
				for (FormModel formModel : htmlModel.getFormModels()) {
					getPropertyModelsOfTable(formModel);
				}				
			}
			//5、table
			if (htmlModel.getTableModels() != null) {
				for (TableModel tableModel : htmlModel.getTableModels()) {
					getPropertyModelsOfTable(tableModel);
				}				
			}
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
				PropertyIntimacyModel.getInstance().addImpeachs(propertyList);
				//处理action的结果
				getPropertyModelsOfEL(actionModel.getElModels());
			}
		}
	}
	
	/**
	 * getPropertyModelsOfTable:(从表格中获取属性)
	 * @author lixingfa
	 * @date 2018年7月11日下午4:10:50
	 * @param formModels
	 */
	private void getPropertyModelsOfTable(TableModel tableModel){
		List<String> names = new ArrayList<String>();
		if (tableModel.getInputs() != null) {
			for (InputModel inputModel : tableModel.getInputs()) {
				names.add(inputModel.getName());
			}			
		}
		if (tableModel.getSelectModels() != null) {
			for (SelectModel selectModel : tableModel.getSelectModels()) {
				names.add(selectModel.getName());
			}			
		}
		if (names.size() > 0) {
			PropertyIntimacyModel.getInstance().addImpeachs(names);
		}
	}
	
}
