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
	 * getPropertyModelsOfBL:(从BL中获取属性)
	 * @author lixingfa
	 * @date 2018年7月11日下午3:44:52
	 * @param htmlModels
	 * @param jsBLModels
	 * @throws SuperServiceException
	 */
	public void getPropertyModelsOfBL(List<HTMLModel> htmlModels,List<BLModel> jsBLModels)throws SuperServiceException{
		List<BLModel> blModels = new ArrayList<BLModel>();
		for (HTMLModel htmlModel : htmlModels) {
			//加载HTML和内嵌JS里的BL语言
			blModels.addAll(htmlModel.getBls());			
			//加载A标签携带的BL语言
			blModels.addAll(htmlModel.getABLs());
		}
		//加载A标签携带的BL语言
		blModels.addAll(jsBLModels);
		//对整个项目的bl进行处理
		getPropertyModelsOfBL(blModels);
	}
	
	/**
	 * BLAnalyze:(从BL语言中获取属性)
	 * @author lixingfa
	 * @date 2018年7月11日下午3:10:47
	 * @param blModels
	 * @throws SuperServiceException
	 */
	private void getPropertyModelsOfBL(List<BLModel> blModels) throws SuperServiceException{
		for (BLModel blModel : blModels) {
			String text = blModel.getText();
			
			//TODO 一条语句就添加一次，但中文和英文要联系起来
			if (text.indexOf("/***") == 0) {
				
			}
		}
	}
	
	/**
	 * BLAnalyze:(分析BL语言，转换成逻辑语言)
	 * @author lixingfa
	 * @date 2018年6月23日上午10:20:37
	 * @param htmlModels
	 * @param jsBLModels
	 * @return List<PropertyModel>
	 * @throws SuperServiceException
	 */
	public void BLAnalyzes(List<HTMLModel> htmlModels,List<BLModel> jsBLModels)throws SuperServiceException{		
		
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
