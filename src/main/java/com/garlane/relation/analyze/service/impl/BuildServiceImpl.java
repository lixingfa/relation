/**
 * 
 */
package com.garlane.relation.analyze.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.analyze.service.BuildService;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**
 * @author lixingfa
 * @date 2019年1月3日下午5:28:22
 * 
 */
@Service("buildService")
public class BuildServiceImpl implements BuildService{
	
	/**本来应该从底层开始构建的，目前条件不足，走一步看一步了
	 * build:(构建项目)
	 * @author lixingfa
	 * @date 2019年1月3日下午5:26:46
	 * @param htmlModels
	 * @param folderPath 选择的页面文件夹目录
	 * @throws SuperServiceException
	 */
	public void build(List<HTMLModel> htmlModels,String folderPath) throws SuperServiceException{
		//html有多少个actionModel，就有多少个请求方法
		if(folderPath.endsWith("\\")){
			folderPath = folderPath.substring(0,folderPath.length() - 1);
		}
		String projectName = folderPath.substring(folderPath.lastIndexOf("\\"));
		
	}
}
