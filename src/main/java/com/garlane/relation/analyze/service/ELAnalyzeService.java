package com.garlane.relation.analyze.service;

import java.util.List;

import com.garlane.relation.analyze.model.el.ELModel;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**
 * JSTL标签解析服务
 * @author lingxingfa
 *
 */
public interface ELAnalyzeService {

	/**
	 * analyze:(提取页面内容中的el属性)
	 * @author lixingfa
	 * @date 2018年7月4日下午6:20:41
	 * @param content 页面内容
	 * @return List<ELModel> 整理好层级关系的el属性
	 * @throws SuperServiceException
	 */
	public List<ELModel> analyze(String content) throws SuperServiceException;
	
	/**
	 * getElModels:(从属性列表里组装EL集合)
	 * @author lixingfa
	 * @date 2018年7月5日下午7:30:12
	 * @param propertys
	 * @return
	 * @throws SuperServiceException
	 */
	public List<ELModel> getElModels(String[] propertys) throws SuperServiceException;
}
