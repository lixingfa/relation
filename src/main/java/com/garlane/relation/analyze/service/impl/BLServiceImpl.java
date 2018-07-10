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

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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
		List<BLModel> blModels = new ArrayList<BLModel>();
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
	
	public static void main(String[] args) {
		
		String input = "阿里巴巴";
		StringBuilder pinyin = new StringBuilder();
		
		for (int i = 0; i < input.length(); i++) {
			HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
			// WITHOUT_TONE：无音标  (zhong)  
			// WITH_TONE_NUMBER：1-4数字表示英标  (zhong4)  
			// WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)  
			defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
			// WITH_V：用v表示ü  (nv)  
			// WITH_U_AND_COLON：用"u:"表示ü  (nu:)  
			// WITH_U_UNICODE：直接用ü (nü)  
			defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
			char c = input.charAt(i);
			String[] pinyinArray = null;
			try {
				//由于中文有很多是多音字，所以这些字会有多个String，在这里我们默认的选择第一个作为pinyin
				pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);//主要是这一句
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			if (pinyinArray != null) {
				pinyin.append(pinyinArray[0]);
			} else if (c != ' ') {
				pinyin.append(input.charAt(i));
			}
		}
		
		System.out.println(pinyin.toString().trim().toLowerCase());
	}
}
