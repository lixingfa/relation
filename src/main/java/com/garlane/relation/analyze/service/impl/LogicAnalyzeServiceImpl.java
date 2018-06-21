package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.FormModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.service.LogicAnalyzeService;
import com.garlane.relation.common.utils.exception.SuperServiceException;

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
		log.info("处理form");//同一个form表示一起完成一个事务，查询、修改比较多
		List<FormModel> formModels = new ArrayList<FormModel>();
		
	}
}
