package com.garlane.relation.common.constant;

import com.garlane.relation.common.utils.config.ApprPropConfigUtil;

/**
 *	环境静态关键字
 *	@author hefule
 *	@date 2017年1月13日 下午1:53:34
 *
 */
public class ConfigConstant{

	
	/**系统编码*/
	public static String SYSCODE = "007";
	
	/**
	 *	数据源类型
	 *	@author hefule
	 *	@date 2017年1月18日 上午11:03:26
	 *	@serialField 参数值有 oracle或者mysql
	 *
	 */
	public static String DBTYPE = ApprPropConfigUtil.get("DB_TYPE","oracle").toLowerCase();//base包的都是小写的
	
	/**
	 *	base数据库名称
	 *	@author hefule
	 *	@date 2017年2月13日 下午2:55:52
	 *
	 */
	public static String DBNAME = ApprPropConfigUtil.get("base.jdbc.name");
	
	/**
	 *	获取文件存放根路径
	 *	@author hefule
	 *	@date 2017年4月11日 上午9:33:26
	 *
	 */
	public static String FILEBASEPATH = ApprPropConfigUtil.get("SP_ROOT_PATH_ATTACH");
	
	/**
	 *	错误页面跳转路径定义
	 *	@author hefule
	 *	@date 2017年4月27日 下午5:00:45
	 *
	 */
	public static String ERRORPAGE = "error/500";
	
	/**
	 *	判断是否为mysql静态方法
	 *	@author hefule
	 *	@date 2017年2月24日 下午1:11:09
	 *
	 */
	public static boolean isMysql(){
		if("mysql".equals(DBTYPE)){
			return true;
		}
		return false;
	}
	
	/**
	 *	判断是否为oracle静态方法
	 *	@author hefule
	 *	@date 2017年2月24日 下午1:11:47
	 *
	 */
	public static boolean isOracle(){
		if("oracle".equals(DBTYPE)){
			return true;
		}
		return false;
	}
	
}
