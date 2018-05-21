package com.garlane.relation.truefalse.service;

import com.garlane.relation.common.service.BaseService;
import com.garlane.relation.common.utils.exception.SuperServiceException;
import com.garlane.relation.truefalse.model.TrueFalseModel;

public interface TrueFalseService extends BaseService<TrueFalseModel>{

	/**
	 * 覆写父类方法,处理业务逻辑
	 * @author pangyc
	 */
	@Override
	public void add(TrueFalseModel actionModel) throws SuperServiceException;
	
	/**
	 * 覆写父类方法处理业务逻辑
	 */
	@Override
	public void updateSelective(TrueFalseModel trueFalseModel) throws SuperServiceException;
	
}
