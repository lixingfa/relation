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
    public static final Long UPLOADFILE_MAX_SIZE = Long.parseLong(PropConfigUtil.get("UPLOADFILE_MAX_SIZE", "314572800"));
	
    /**上传的最大文件数量**/
    public static final Long UPLOADFILE_MAX_NUM = Long.parseLong(PropConfigUtil.get("UPLOADFILE_MAX_NUM","100"));
    
    /**解析的最大文件数量，即html和js文件的总数**/
    public static final Long ANALYZE_MAX_NUM = Long.parseLong(PropConfigUtil.get("ANALYZE_MAX_NUM","100"));
    
	/** 附件上传默认物理路径 uploadFilestUserFilesh **/
	private static final String DEFAULT_ROOT_PATH_ATTACHMENT = File.separator + "uploadFiles" + File.separator + "UserFile" + File.separator;

	/** 附件上传物理路径 **/
	public static String ROOT_PATH_ATTACHMENT = PropConfigUtil.get("ROOT_PATH_ATTACHMENT",DEFAULT_ROOT_PATH_ATTACHMENT);
	
	/**允许上传的后缀**/
	public static String UPLOAD_FILE_SUFFIXS = PropConfigUtil.get("UPLOAD_FILE_SUFFIXS", "html,htm,js,css,png,jpg,gif,swf");
	
	/**
	 *	错误页面跳转路径定义
	 *	@author zhengzch
	 *	@date 2017.08.29
	 *
	 */
	public static String ERRORPAGE = "error/500";;
}
