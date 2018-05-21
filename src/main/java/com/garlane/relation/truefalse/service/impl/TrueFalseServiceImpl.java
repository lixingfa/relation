package com.garlane.relation.truefalse.service.impl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garlane.relation.common.service.impl.BaseServiceImpl;
import com.garlane.relation.common.utils.exception.SuperServiceException;
import com.garlane.relation.common.utils.file.FilePathUtil;
import com.garlane.relation.truefalse.dao.TrueFalseDao;
import com.garlane.relation.truefalse.model.TrueFalseModel;
import com.garlane.relation.truefalse.service.TrueFalseService;

/**
 * 
 * @author zhouwx
 * @date 2017年11月2日下午12:00:00
 */
@Service
public class TrueFalseServiceImpl extends BaseServiceImpl<TrueFalseModel> implements TrueFalseService{
	
	@Autowired
	private TrueFalseDao trueFalseDao;

	@Override
	public void add(TrueFalseModel trueFalseModel) throws SuperServiceException {
		try {
			String pageUrl = trueFalseModel.getPageUrl();
			pageUrl = FilePathUtil.joinPath(pageUrl);
			if (pageUrl.startsWith("/")) {
				pageUrl = pageUrl.substring(1);
			}
			trueFalseModel.setPageUrl(pageUrl);
			trueFalseDao.add(trueFalseModel);
        } catch (Exception e) {
            throw new SuperServiceException(e);
        } 
	}
	
	@Override
	public void updateSelective(TrueFalseModel trueFalseModel) throws SuperServiceException {
		try {
			String pageUrl = trueFalseModel.getPageUrl();
			if (StringUtils.isNotBlank(pageUrl)) {
				pageUrl = FilePathUtil.joinPath(pageUrl);
				if (pageUrl.startsWith("/")) {
					pageUrl = pageUrl.substring(1);
				}
				trueFalseModel.setPageUrl(pageUrl);				
			}
			trueFalseDao.updateSelective(trueFalseModel);
        } catch (Exception e) {
            throw new SuperServiceException(e);
        } 
	}

}
