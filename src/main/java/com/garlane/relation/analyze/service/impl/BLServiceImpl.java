/**
 * 
 */
package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.logic.PropertyModel;
import com.garlane.relation.analyze.model.page.BLModel;
import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.service.BLService;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**
 * @author lixingfa
 * @date 2018年6月23日上午10:09:01
 * 
 */
@Service("blService")
public class BLServiceImpl implements BLService{

	private Logger log = Logger.getLogger(getClass());
	/**
	 * BLAnalyze:(分析BL语言，转换成逻辑语言)
	 * @author lixingfa
	 * @date 2018年6月23日上午10:20:37
	 * @param htmlModels
	 * @param jsBLModels
	 * @return List<PropertyModel>
	 * @throws SuperServiceException
	 */
	public List<PropertyModel> BLAnalyzes(List<HTMLModel> htmlModels,List<BLModel> jsBLModels)throws SuperServiceException{
		List<PropertyModel> propertyModels = new ArrayList<PropertyModel>();
		log.info("获取所有的BL语言");
		List<BLModel> blModels = new ArrayList<>();
		for (HTMLModel htmlModel : htmlModels) {
			blModels.addAll(htmlModel.getBls());
		}
		blModels.addAll(jsBLModels);
		log.info("开始分析BL语言");
		for (BLModel blModel : blModels) {
			
		}
		
		return null;
	}
	
	private void BLAnalyze(BLModel blModel) throws SuperServiceException{
		
	}
}
