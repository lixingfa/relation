/**
 * 
 */
package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.easyui.ActionModel;
import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.model.easyui.GridModel;
import com.garlane.relation.analyze.model.easyui.TreeModel;
import com.garlane.relation.analyze.model.mvc.ControllerModel;
import com.garlane.relation.analyze.model.mvc.MethodModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.service.BuildService;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**
 * @author lixingfa
 * @date 2019年1月3日下午5:28:22
 * 
 */
@Service("buildService")
public class BuildServiceImpl implements BuildService{
	
	/**本来应该从底层开始构建的，目前条件不足，走一步看一步了
	 * build:(构建项目)
	 * @author lixingfa
	 * @date 2019年1月3日下午5:26:46
	 * @param htmlModels
	 * @param folderPath 选择的页面文件夹目录
	 * @throws SuperServiceException
	 */
	public void build(List<HTMLModel> htmlModels,String folderPath) throws SuperServiceException{
		List<ControllerModel> controllerModels = new ArrayList<ControllerModel>();
		//html有多少个actionModel，就有多少个请求方法
		//项目名称，包名的格式为com.项目名称.文件路径
		String[] folders = folderPath.split("/");
		String projectName = folders[folders.length - 2];//倒数第二个才是项目名
		folderPath = folderPath.replace("/", "\\");
		for (HTMLModel htmlModel : htmlModels) {
			String htmlPath = htmlModel.getPath().replace(folderPath + "\\WEB-INF\\jsp\\", "");
			String packePath = htmlPath.substring(0, htmlPath.lastIndexOf("\\"));
			String packeName = "com." + projectName + "." + packePath.replace("\\", ".");
			//controllerName一般取共性
			
			//从action提取方法
			List<ActionModel> actionModels = new ArrayList<ActionModel>();
			EASYUIModel easyuiModel = htmlModel.getEasyuiModel();
			for (GridModel gridModel : easyuiModel.getGridModels()) {
				for (ActionModel actionModel : gridModel.getActionModels()) {
					actionModels.add(actionModel);
				}
			}
			for (TreeModel treeModel : easyuiModel.getTreeModels()) {
				for (ActionModel actionModel : treeModel.getActionModels()) {
					actionModels.add(actionModel);
				}
			}
			ControllerModel controllerModel = null;
			for (ActionModel actionModel : actionModels) {
				MethodModel methodModel = null;
				//先拿url
				String url = actionModel.getUrl();
				if (url.contains(packePath)) {//同一个controller
					//查找是否有这个请求了
					methodModel = getMethodModelByRequest(url, controllerModels);
					if (methodModel == null) {
						methodModel = getMethodModelByAction(actionModel);
					}else {
						//比较不同，增减参数
						
					}
				}else {
					controllerModel = new ControllerModel();
					String[] urls = url.split("/");
					controllerModel.setClassName(urls[urls.length - 2]);
					controllerModel.setRequestMapping(urls[urls.length - 2]);
					controllerModel.setPackageValue(packeName);					
					methodModel = getMethodModelByAction(actionModel,urls[urls.length - 1].substring(0,urls[urls.length - 1].indexOf(".")));
				}
				controllerModel.addMethodModel(methodModel);
			}
			//每个htmlModel都有一个toPage方法
			controllerModel.addToPageMethod(htmlPath);
		}
	}
	
	/**
	 * getMethodModelByAction:(根据action获取请求方法)
	 * @author lixingfa
	 * @date 2019年1月4日下午3:02:14
	 * @param actionModel
	 * @return MethodModel
	 */
	private MethodModel getMethodModelByAction(ActionModel actionModel){
		return getMethodModelByAction(actionModel,null);
	}
	
	/**
	 * getMethodModelByAction:(根据action获取请求方法)
	 * @author lixingfa
	 * @date 2019年1月4日下午3:03:27
	 * @param actionModel
	 * @param requestMapping
	 * @return MethodModel
	 */
	private MethodModel getMethodModelByAction(ActionModel actionModel,String requestMapping){
		MethodModel methodModel = new MethodModel();
		if (requestMapping == null) {
			String[] urls = actionModel.getUrl().split("/");
			requestMapping = urls[urls.length - 1].substring(0,urls[urls.length - 1].indexOf("."));
		}
		methodModel.setRequestMapping(requestMapping);
		methodModel.setName(requestMapping);
		//请求方式
		if(actionModel.getReqType() == ActionModel.REQ_TYPE_POST){
			//TODO
		}
		//返回的数据类型
		if (actionModel.getResultType() == ActionModel.RESULT_TYPE_JSON) {
			
		}else if (actionModel.getResultType() == ActionModel.RESULT_TYPE_PAGE) {
			//TODO 有了toPage方法，应该不需要这段了
		}
		
		
		
		return methodModel;
	}
	
	/**
	 * getMethodModelByRequest:(根据请求链接找出请求方法)
	 * @author lixingfa
	 * @date 2019年1月4日下午2:59:22
	 * @param url
	 * @param controllerModels
	 * @return MethodModel
	 */
	private MethodModel getMethodModelByRequest(String url,List<ControllerModel> controllerModels){
		for (ControllerModel controllerModel : controllerModels) {
			if (url.contains(controllerModel.getRequestMapping())) {//在这个controller里
				for (MethodModel model : controllerModel.getMethodModels()) {
					if (url.contains(model.getRequestMapping())) {
						return model;
					}
				}
			}
		}
		return null;
	}
}
