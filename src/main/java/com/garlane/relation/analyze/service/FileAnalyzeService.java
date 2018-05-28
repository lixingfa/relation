/**
 * 
 */
package com.garlane.relation.analyze.service;

import com.garlane.relation.common.utils.exception.SuperServiceException;

/**
 * @author lixingfa
 * @date 2018年5月28日下午7:15:13
 * 
 */
public interface FileAnalyzeService {

	/**
	 * getFileLogic:(获取项目业务逻辑)
	 * @author lixingfa
	 * @date 2018年5月28日下午7:19:41
	 * @param path 项目静态页面路径
	 * @throws SuperServiceException
	 */
	void getFileLogic(String path) throws SuperServiceException;
}
