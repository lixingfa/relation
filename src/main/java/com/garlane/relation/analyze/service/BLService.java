/**
 * 
 */
package com.garlane.relation.analyze.service;

import java.util.List;

import com.garlane.relation.analyze.model.logic.PropertyModel;
import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**
 * @author lixingfa
 * @date 2018年6月23日上午10:08:47
 * 
 */
public interface BLService {

	/**
	 * BLAnalyzes:(分析BL语言，转换成逻辑语言)
	 * @author lixingfa
	 * @date 2018年6月23日上午10:20:37
	 * @param htmlModels
	 * @param jsBLModels
	 * @return List<PropertyModel>
	 * @throws SuperServiceException
	 */
	public List<PropertyModel> BLAnalyzes(List<HTMLModel> htmlModels,List<BLModel> jsBLModels)throws SuperServiceException;
}
