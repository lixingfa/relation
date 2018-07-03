/**
 * 
 */
package com.garlane.relation.analyze.service;

import com.garlane.relation.analyze.model.easyui.EASYUIModel;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**EASYUI 分析类
 * @author lixingfa
 * @date 2018年7月3日下午2:06:20
 * 
 */
public interface EASYUIAnalyzeService {

	/**
	 * 从内容中获取EASYUI的所有对象信息
	 * @param content html\jsp\js文件内容
	 * @return EASYUIModel
	 * @throws SuperServiceException
	 */
	public EASYUIModel getEasyuiModel(String content) throws SuperServiceException;
}
