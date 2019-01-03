package com.garlane.relation.config;
import java.util.regex.Pattern;

/**
 * 系统编码用到的一些常量
 * @author Lixingfa
 *
 */
public class Constant {
	
	/**主键名称，数据表、pageModel*/
	public static final String PRIMARY_KEY = "primary_key";	
//	/**页面方法*/
//	public static enum PageMethod{
//		get,post
//	}
	/**方法类型**/
	public enum methodType{
		control,
		server,
		dao,
		util
	}
	/**数据类型**/
	public enum dataType{//byte、short、int、long、char，按升序排
		Byte,//8位[-128, 127]
		Short,//16位[- 2^15, 2^15 - 1]
		Integer,//32 bits, [- 2^31, 2^31 - 1]
		Long,//64 bits， [- 2^63, 2^63 - 1]
		Float,//32位单精度
		Double,//64位双精度
		Boolean,
		Char,
		String,
		DateTime
	}
	
	/**
	 * 获取数据类型
	 * @param object 数据对象
	 * @return 数据类型
	 */
	public static Constant.dataType getDataType(String s){
		if (Pattern.matches("^-{0,1}[0-9]*[.]{0,1}[0-9]+$", s)) {//数字类型
			if (s.contains(".")) {//带小数
				double d = Double.parseDouble(s);
				if (d > Float.MAX_VALUE || d < Float.MIN_VALUE) {
					return dataType.Double;
				}else {
					return dataType.Float;
				}
			}else {//整数类型
				long l = Long.parseLong(s);
				if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
					return dataType.Long;
				}else if (l > Short.MAX_VALUE || l < Short.MIN_VALUE) {
					return dataType.Integer;
				}else if (l > Byte.MIN_VALUE && l < Byte.MAX_VALUE) {
					return dataType.Short;
				}else {
					return dataType.Byte;
				}
			}
		}else {
			if (s.length() == 1) {
				return dataType.Char;
			}else {
				if (Pattern.matches("^[0-9]{2,4}[-\\/]{1}[0-9]{2}[-\\/]{1}[0-9]{2}( [0-9]{2}:[0-9]{2}:[0-9]{2}){0,1}$", s)) {
					return dataType.DateTime;
				}
				return dataType.String;				
			}
		}
	}
	/**系统中为防止key相同等后接的唯一标识*/
	public static String onlyFlat = "_garlane";
	
	/**
	 * 页面使用的控件，控件名称_具体名称
	 *
	 */
	public static enum pageControl{
		js_jq,//普通的js、jq
		easyui_treegrid
	}
}
