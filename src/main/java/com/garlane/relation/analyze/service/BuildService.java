/**
 * 
 */
package com.garlane.relation.analyze.service;

import java.util.List;

import com.garlane.relation.analyze.model.page.HTMLModel;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**构建项目
 * @author lixingfa
 * @date 2019年1月3日下午5:02:23
 * 
 */
public interface BuildService {

	/**本来应该从底层开始构建的，目前条件不足，走一步看一步了
	 * build:(构建项目)
	 * @author lixingfa
	 * @date 2019年1月3日下午5:26:46
	 * @param htmlModels
	 * @param folderPath 选择的页面文件夹目录
	 * @throws SuperServiceException
	 */
	public void build(List<HTMLModel> htmlModels,String folderPath) throws SuperServiceException;
}
