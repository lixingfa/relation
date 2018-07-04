/**
 * 
 */
package com.garlane.relation.analyze.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.analyze.model.easyui.TreeGridModel;
import com.garlane.relation.analyze.service.EASYUIAnalyzeService;
import com.garlane.relation.common.utils.change.StringUtil;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**转成JSON，easyUI基本是用了JSON的思想
 * @author lixingfa
 * @date 2018年7月3日下午2:06:56
 * 
 */
@Service("easyuiAnalyzeService")
public class EASYUIAnalyzeServiceImpl implements EASYUIAnalyzeService{
	/**
	 * 从内容中获取EASYUI的所有对象信息
	 * @param content html\jsp\js文件内容
	 * @return EASYUIModel
	 * @throws SuperServiceException
	 */
	public EASYUIModel getEasyuiModel(String content) throws SuperServiceException{
		EASYUIModel easyuiModel = new EASYUIModel();
		//TreeGrid
		easyuiModel.setTreeGridModels(getGridModels(content));
		
		return easyuiModel;
	}
	
	/**
	 * getGridModels:(提取内容中的TreeGrid信息)
	 * @author lixingfa
	 * @date 2018年7月4日下午7:48:03
	 * @param content
	 * @return List<TreeGridModel> or null
	 */
	private List<TreeGridModel> getGridModels(String content){
		List<TreeGridModel> treeGridModels;
		int index = content.indexOf(".treegrid({");
		if (index != -1) {
			treeGridModels = new ArrayList<TreeGridModel>();
			String grid = StringUtil.getSubStringByLR(index, '{', '}', content);//确保从第一个花括号开始
			//转成JSON
			JSONObject jsonObject = JSONObject.parseObject(grid);
			
			System.out.println(jsonObject.getString("treeField"));
			JSONArray arrays = jsonObject.getJSONArray("columns");
			for (int i = 0; i < arrays.size(); i++) {
				JSONArray subArray = arrays.getJSONArray(i);//列属性是数组
				for (int j = 0; j < subArray.size(); j++) {
					JSONObject object = subArray.getJSONObject(j);
					System.out.println(object.get("field"));
				}
			}
			return treeGridModels;
		}else {
			return null;
		}
	}
	
}
