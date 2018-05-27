package com.garlane.relation.common.constant;

import java.io.File;

import com.garlane.relation.common.utils.config.PropConfigUtil;

/**
 * 环境静态关键字
 * @author hefule
 * @date 2017年1月13日 下午1:53:34
 * 
 */
public class ConfigConstant {

	/**
	 * base数据库名称
	 * @author hefule
	 * @date 2017年2月13日 下午2:55:52
	 * 
	 */
	public static String DBNAME = PropConfigUtil.get("base.jdbc.name");

	/**默认允许上传单文件最大值(单位字节) 50MB*/
    public static final String UPLOADFILE_MAX_SIZE = PropConfigUtil.get("UPLOADFILE_MAX_SIZE", "314572800");
	
    /****/
    public static final String UPLOADFILE_MAX_NUM = PropConfigUtil.get("UPLOADFILE_MAX_NUM","100");
    
	/** 附件上传默认物理路径 uploadFilestUserFilesh */
	private static final String DEFAULT_ROOT_PATH_ATTACHMENT = File.separator + "uploadFiles" + File.separator + "UserFile" + File.separator;

	/** 附件上传物理路径 * */
	public static String ROOT_PATH_ATTACHMENT = PropConfigUtil.get("ROOT_PATH_ATTACH",DEFAULT_ROOT_PATH_ATTACHMENT);
	
	/**
	 *	错误页面跳转路径定义
	 *	@author zhengzch
	 *	@date 2017.08.29
	 *
	 */
	public static String ERRORPAGE = "error/500";;
}
