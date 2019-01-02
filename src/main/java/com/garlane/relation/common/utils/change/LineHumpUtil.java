package com.garlane.relation.common.utils.change;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 *	下划线驼峰法互转
 *	@author hefule
 *	@date 2017年1月16日 下午12:01:09
 *
 */
public final class LineHumpUtil {
	
	 private static Pattern linePattern = Pattern.compile("_(\\w)");
	
	 private static Pattern humpPattern = Pattern.compile("[A-Z]");

	/**
	 * 下划线转驼峰
	 *	@author hefule
	 *	@date 2017年1月16日 下午12:02:04
	 *	@param str 转换对象字符串
	 *
	 */
     public static String lineToHump(String str){  
    	 if(StringUtils.isEmpty(str))
    		 return str;
         str = str.toLowerCase();  
         Matcher matcher = linePattern.matcher(str);  
         StringBuffer sb = new StringBuffer();  
         while(matcher.find()){  
             matcher.appendReplacement(sb, matcher.group(1).toUpperCase());  
         }  
         matcher.appendTail(sb);  
         return sb.toString();  
     }  
     
    /**
	 *	驼峰转下划线
	 *	@author hefule
	 *	@date 2017年1月16日 下午12:02:44
	 *  @param str 转换对象字符串
	 */
     public static String humpToLine(String str){  
    	 if(StringUtils.isEmpty(str))
    		 return str;
         Matcher matcher = humpPattern.matcher(str);  
         StringBuffer sb = new StringBuffer();  
         while(matcher.find()){  
             matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());  
         }  
         matcher.appendTail(sb);  
         return sb.toString();  
     }  
     
    /**
	 *	字符串首字母大写
	 *	@author hefule
	 *	@date 2017年2月20日 下午6:19:31
	 *
	 */
     public static String strToUp(String str){
    	 char[] cs= str.toCharArray();
         cs[0]-=32;
         return String.valueOf(cs);
     }
     
     /**
 	 *	字符串首字母小写
 	 *	@author hefule
 	 *	@date 2017年2月20日 下午6:19:31
 	 *
 	 */
      public static String strToDo(String str){
     	 char[] cs= str.toCharArray();
          cs[0]+=32;
          return String.valueOf(cs);
      }
     
}
