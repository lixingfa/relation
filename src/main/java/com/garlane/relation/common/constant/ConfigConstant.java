package com.garlane.relation.common.constant;

import java.io.File;

import org.apache.log4j.Logger;

import com.garlane.relation.common.utils.config.ApprPropConfigUtil;

/**
 * 环境静态关键字
 * @author hefule
 * @date 2017年1月13日 下午1:53:34
 * 
 */
public class ConfigConstant {

	private static Logger logger = Logger.getLogger(ConfigConstant.class);

	/** 系统编码 */
	public static String SYSCODE = "007";

	/**
	 * 数据源类型
	 * @author hefule
	 * @date 2017年1月18日 上午11:03:26
	 * @serialField 参数值有 oracle或者mysql
	 * 
	 */
	public static String DBTYPE = ApprPropConfigUtil.get("DB_TYPE", "ORACLE").toLowerCase();

	/**
	 * base数据库名称
	 * @author hefule
	 * @date 2017年2月13日 下午2:55:52
	 * 
	 */
	public static String DBNAME = ApprPropConfigUtil.get("base.jdbc.name");

	public static String LOGIN_PROJECT = ApprPropConfigUtil.get("LOGIN_PROJECT", "/ApprBase");

	/**默认允许上传单文件最大值(单位字节) 50MB*/
    public static final long DEFAULT_UPLOADFILE_MAX_SIZE = 10485760*5*6L;
	
	/**
     * 为1时电子件就永远都不是必须的
     */
    public static String UPLOADFILE_NOT_NEED = ApprPropConfigUtil.get("UPLOADFILE_NOT_NEED", "");

	/** 附件上传默认物理路径 /mnt/MDFilesPath/ 2017.01.10 wengsw */
	private static final String DEFAULT_ROOT_PATH_ATTACHMENT = File.separator + "mnt" + File.separator + "MDFilesPath"
			+ File.separator;

	/**
	 * 附件上传物理路径
	 * */
	public static String ROOT_PATH_ATTACHMENT = ApprPropConfigUtil.get("SP_ROOT_PATH_ATTACH",
			DEFAULT_ROOT_PATH_ATTACHMENT);

	/**
	 *	错误页面跳转路径定义
	 *	@author zhengzch
	 *	@date 2017.08.29
	 *
	 */
	public static String ERRORPAGE = "error/500";

	/**
	 * 判断是否为mysql静态方法
	 * @author hefule
	 * @date 2017年2月24日 下午1:11:09
	 * @return boolean
	 */
	public static boolean isMysql() {
		if ("mysql".equals(DBTYPE)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为oracle静态方法
	 * @author hefule
	 * @date 2017年2月24日 下午1:11:47
	 * @return boolean
	 */
	public static boolean isOracle() {
		if ("oracle".equals(DBTYPE)) {
			return true;
		}
		return false;
	}

	/**
	 * getSpCreditIsOpen:(审批对接诚信系统开关标识)
	 * @author lixingfa
	 * @date 2017年5月24日下午4:25:46
	 * @return
	 */
	public static Boolean getSpCreditIsOpen(){
		Boolean result = true;
		try{
			result = "false".equalsIgnoreCase(ApprPropConfigUtil.get("sp.credit.isopen")) ? false : true;
		}catch(Exception e){
			logger.info("未指定诚信系统对接开关sp.credit.isopen，默认开启");
		}
		return result;
	}

	/**
	 * 是否启用加密
	 */
	public static String ENCRYPT_TYPE = ApprPropConfigUtil.get("common.login.encryptType","NONE").toLowerCase();
}
