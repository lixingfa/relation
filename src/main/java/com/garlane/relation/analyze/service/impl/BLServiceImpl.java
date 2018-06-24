/**
 * 
 */
package com.garlane.relation.analyze.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.logic.PropertyModel;
import com.garlane.relation.analyze.model.page.AModel;
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
		List<BLModel> blModels = new ArrayList<>();
		for (HTMLModel htmlModel : htmlModels) {
			log.info("处理HTML和内嵌JS里的BL语言");
			blModels.addAll(htmlModel.getBls());
			BLAnalyze(htmlModel.getBls());
			
			log.info("处理A标签携带的BL语言");
			blModels.addAll(htmlModel.getABLs());
			ABLAnalyze(htmlModel.getaModels());
		}
		blModels.addAll(jsBLModels);
		return null;
	}
	
	private void BLAnalyze(List<BLModel> blModels) throws SuperServiceException{
		for (BLModel blModel : blModels) {
			if (blModel.getText().indexOf("/***") == 0) {
				//TODO
			}else {
				new PropertyModel(blModel.getBl(), blModel.getText());
			}
		}
	}
	
	private void ABLAnalyze(List<AModel> aModels) throws SuperServiceException{
		
	}
}
