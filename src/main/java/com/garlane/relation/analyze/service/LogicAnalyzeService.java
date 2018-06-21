package com.garlane.relation.analyze.service;

import java.util.List;

import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.common.utils.exception.SuperServiceException;

public interface LogicAnalyzeService {

	/**
	 * 获取业务逻辑
	 * LogicAnalyze:(这里用一句话描述这个方法的作用)
	 * @author lixingfa
	 * @date 2018年6月21日下午5:55:34
	 * @param htmlModels 页面元素集合
	 * @param jsBLModels JS业务语言集合
	 * @throws SuperServiceException
	 */
	void LogicAnalyze(List<HTMLModel> htmlModels,List<BLModel> jsBLModels)throws SuperServiceException;
}
