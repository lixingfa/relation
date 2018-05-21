package com.garlane.relation.truefalse.dao;

import com.garlane.relation.common.dao.BaseDao;
import com.garlane.relation.truefalse.model.TrueFalseModel;


public interface TrueFalseDao extends BaseDao<TrueFalseModel>{
	
	/**
	 * 覆写父类方法处理业务逻辑
	 * @author pangyc
	 */
	@Override
	public void add(TrueFalseModel model);
	
	/**
	 * 覆写父类方法处理业务逻辑
	 * @author pangyc
	 */
	@Override
	public void updateSelective(TrueFalseModel model);
}
