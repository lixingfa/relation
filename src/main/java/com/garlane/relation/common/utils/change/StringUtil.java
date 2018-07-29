package com.garlane.relation.common.utils.change;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;


public class StringUtil {

	/**
	 *	判断是否为list是否为空或者长度为0
	 *	@author hefule
	 *	@date 2017年2月7日 下午5:15:59
	 *	@return true表示为空 false 为非空
	 */
	public static boolean listEmpty(List list){
		if(list==null || list.size()==0)
			return true;
		else 
			return false; 
	}
	
	/**
	 * 把指定的字符串向左边补充足够位数的字符
	 *
	 * @param source
	 * @param delim
	 * @return
	 */
	public static String leftLengthString(String source, int len) {
		return leftLengthString(source, len, '0');
	}

	/**
	 * 把指定的字符串向左边补充足够位数的字符
	 *
	 * @param source
	 * @param delim
	 * @return
	 */
	public static String leftLengthString(String source, int len, char c) {
		int n = len - source.length();
		StringBuffer sChar = new StringBuffer();
		for (int i = 0; i < n; i++) {
			sChar.append(c);
		}
		return sChar.toString() + source;
	}

	/**
	 * 把指定的字符串按分隔符产生数组
	 *
	 * @param source
	 * @param delim
	 * @return
	 */
	public static String[] split(String source, String delim) {
		String wordLists[];
		if (source == null) {
			wordLists = new String[1];
			wordLists[0] = source;
			return wordLists;
		}
		if (delim == null) {
			delim = ",";
		}
		StringTokenizer st = new StringTokenizer(source, delim, true);
		int total = st.countTokens();
		String tempList[] = new String[total + 1];
		boolean preFlag = true;
		int j = 0;
		for (int i = 0; i < total; i++) {
			tempList[i] = st.nextToken();
			if (tempList[i].equals(delim) && preFlag) {
				tempList[i] = "";
				j++;
				if (i >= total - 1) {
					tempList[i + 1] = "";
					j++;
				}
			} else if (tempList[i].equals(delim)) {
				preFlag = true;
				if (i >= total - 1) {
					tempList[i] = "";
					j++;
				}
				total--;
				i--;
			} else {
				preFlag = false;
				j++;
			}
		}

		wordLists = new String[j];
		for (int i = 0; i < j; i++) {
			wordLists[i] = tempList[i];
		}

		return wordLists;
	}

	public static String[] split(String source, char delim) {
		return split(source, String.valueOf(delim));
	}

	public static String[] split(String source) {
		return split(source, ",");
	}

	/**
	 * 把一个单引号转为两个单引号(适用SQL语句组合方式)
	 *
	 * @param source
	 * @return
	 */
	public static String getReplaceAll(Object source) {
		if (source == null) {
			return "";
		} else {
			return (source + "").replaceAll("'", "''");
		}
	}

	public static String getReplaceString(String prefix, String source,
			String values[]) {
		String result = source;
		if (source == null || values == null || values.length < 1) {
			return source;
		}
		if (prefix == null) {
			prefix = "%";
		}
		for (int i = 0; i < values.length; i++) {
			String argument = prefix + Integer.toString(i + 1);
			int index = result.indexOf(argument);
			if (index != -1) {
				String temp = result.substring(0, index);
				if (i < values.length) {
					temp = temp + values[i];
				} else {
					temp = temp + values[values.length - 1];
				}
				temp = temp + result.substring(index + 2);
				result = temp;
			}
		}

		return result;
	}

	public static String getRepalceString(String source, String values[]) {
		return getReplaceString("%", source, values);
	}

	/**
	 * 数组中是否有包括指定的字符串
	 *
	 * @param strings
	 * @param string
	 * @param caseSensitive
	 * @return
	 */
	public static boolean contains(String strings[], String string,
			boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else if (strings[i].equalsIgnoreCase(string)) {
				return true;
			}
		}

		return false;
	}

	public static boolean contains(String strings[], String string) {
		return contains(strings, string, true);
	}

	public static boolean containsIgnoreCase(String strings[], String string) {
		return contains(strings, string, false);
	}

	public static String combineStringArray(String array[], String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * 对指定的字符串转为整型
	 *
	 * @param input
	 * @param deft
	 * @return
	 */
	public static int getInt(String input, int defValue) {
		int iRet = defValue;
		try {
			String temp = getString(input);
			if (!temp.equals("")) {
				iRet = Integer.parseInt(temp);
			}
		} catch (NumberFormatException fe) {
			fe.printStackTrace();
		}
		return iRet;
	}

	public static int getInt(int input, int defValue) {
		if (input == 0) {
			return defValue;
		} else {
			return input;
		}
	}

	public static int getInt(Object sObject) {
		int iRet = 0;
		String temp = getString(sObject, "0");
		try {
			iRet = Integer.parseInt(temp);
		} catch (NumberFormatException nfe) {
		}
		return iRet;
	}

	public static long getLong(Object sObject) {
		long lRet = 0;
		String temp = getString(sObject, "0");
		try {
			lRet = Long.parseLong(temp);
		} catch (NumberFormatException nfe) {
		}
		return lRet;
	}

	/**
	 * 把字符性的数字转换成double型
	 *
	 * @param value
	 * @return
	 */
	public static double getDouble(Object value) {
		double dRet = 0.0D;
		try {
			String temp = getString(value, "0");
			dRet = Double.parseDouble(temp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dRet;
	}

	public static float getFloat(Object value) {
		float fRet = 0;
		try {
			String temp = getString(value, "0");
			fRet = Float.parseFloat(temp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fRet;
	}

	/**
	 * 取得带有默认值的字符串
	 *
	 * @param obj
	 * @param deft
	 * @return
	 */
	public static String getString(Object sObject, String defStr) {
		String temp = "";
		if (sObject == null) {
			temp = "";
		} else {
			temp = (sObject + "").trim();
		}

		if (temp.equals("null") || temp.equals("")) {
			temp = defStr;
		}
		return temp;
	}

	public static String getString(byte[] bData) {
		if (bData == null) {
			return "";
		} else {
			return new String(bData);
		}
	}

	public static String getString(Object sObject) {
		return getString(sObject, "");
	}

	/**
	 * 取得指定长度的字符串
	 *
	 * @param sObject
	 * @param length
	 * @return
	 */
	public static String getString(Object sObject, int length) {
		return getString(sObject, length, "");
	}

	/**
	 * 字符串转日期型
	 *
	 * @param dateString
	 * @return
	 * @throws Exception
	 */
	public static Date getString2Date(String dateString) throws Exception {
		SimpleDateFormat formattxt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formattxt.parse(dateString);
	}


	public static Timestamp getString2Timestamp(String dateString) throws Exception {
		if(dateString == null || dateString.length()==0) {
			return null;
		}
		java.sql.Date date = java.sql.Date.valueOf(dateString);
		return java.sql.Timestamp.valueOf(date + " 23:59:59");
	}

	/**
	 * 日期型转字符串
	 *
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getDate2String(Date date) throws Exception {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formattxt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formattxt.format(date);
	}

	/**
	 * 取得指定长度带默认值的字符串
	 *
	 * @param sObject
	 * @param len
	 * @param defStr
	 * @return
	 */
	public static String getString(Object sObject, int len, String defStr) {
		String temp = "";
		if (sObject == null) {
			temp = "";
		} else {
			temp = (sObject + "").trim();
		}

		if (temp.equals("null") || temp.equals("")) {
			temp = defStr;
		}

		try {
			if (temp.length() > len) {
				temp = temp.substring(0, len);
			}
		} catch (StringIndexOutOfBoundsException ox) {
			ox.printStackTrace();
		}
		return temp;
	}

	/**
	 * 日期转日期时间型
	 *
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp date2Timestamp(java.util.Date date) {
		if (date == null) {
			return null;
		} else {
			return new java.sql.Timestamp(date.getTime());
		}
	}

	public static int length(Object object) {
		String value = object + "";
		if (value == null || value.equals("")) {
			return 0;
		} else {
			return value.length();
		}
	}

	/**
	 * 把空串转为指定的字符串
	 *
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String ifEmpty(Object sObject, Object defObject) {
		String temp = "";
		if (sObject == null) {
			temp = "";
		} else {
			temp = (sObject + "").trim();
		}

		if (temp.equals("") || temp.equals("null")) {
			temp = defObject + "";
		}

		return temp;
	}

	/**
	 * 把空值转为指定的字符串
	 *
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String ifNull(Object sOject, Object defObject) {
		if (sOject == null) {
			return defObject + "";
		} else {
			return sOject + "";
		}
	}

	public static Timestamp ifNull(Timestamp sTime, Timestamp defTime) {
		if (sTime == null) {
			return defTime;
		} else {
			return sTime;
		}
	}

	/**
	 * 把回车，换行，空格符转为HTML格式字符
	 *
	 * @param str
	 * @return
	 */
	public static String HTMLTurn(String value) {
		if ((null == value) || (value.trim().equals(""))) {
			return "";
		}
		while (value.indexOf("\n") != -1) {
			value = value.substring(0, value.indexOf("\n")) + "<br>&nbsp;"
					+ value.substring(value.indexOf("\n") + 1);
		}
		while (value.indexOf(" ") != -1) {
			value = value.substring(0, value.indexOf(" ")) + "&nbsp;"
					+ value.substring(value.indexOf(" ") + 1);
		}
		return value;
	}

	public static String date2String(Date voteDate) throws Exception {
		if (voteDate == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(voteDate);
	}

	/**
	 * 把特殊字符转为HTML格式字符
	 *
	 * @param as_Value
	 * @return
	 * @throws Exception
	 */
	public static String htmEncode(String as_Value) throws Exception {
		StringBuffer ls_Out = new StringBuffer();
		try {
			String ls_Value = as_Value;
			if (ls_Value != null) {
				for (int i = 0; i < ls_Value.length(); i++) {
					ls_Out.append(emitQuote(ls_Value.charAt(i)));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return ls_Out.toString();
	}

	/**
	 * 分割字符串
	 * @param aSrc  源字符串
	 * @param aDelim  分割符
	 * @param aFieldNum 第几部分
	 * @return
	 */
	public static String getField(String aSrc, String aDelim, long aFieldNum) {
		if (aSrc == null) {
			return null;
		}

		int beginIndex = 0;
		int endIndex = 0;
		int fieldNum = 0;

		while (aSrc.indexOf(aDelim, endIndex) != -1) {
			endIndex = aSrc.indexOf(aDelim, endIndex);
			if (fieldNum == aFieldNum) {
				break;
			}

			++endIndex;
			beginIndex = endIndex;
			++fieldNum;
		}
		if ((beginIndex == endIndex)
				&& (((aSrc.indexOf(aDelim, 0) == -1) || (aSrc.indexOf(aDelim,
						endIndex) == -1)))) {
			endIndex = aSrc.length();
		}

		if (endIndex != -1) {
			return aSrc.substring(beginIndex, endIndex);
		}

		return aSrc.substring(beginIndex);
	}

	/**
	 * 把List对象的值转为指定分隔符的字符串
	 *
	 * @param chrList
	 * @param splitTag
	 * @return
	 */
	public static String toString(List chrList, String splitTag) {
		return toString(chrList, splitTag, true);
	}

	public static String toString(String[] chrList, String splitTag) {
		return toString(chrList, splitTag, true);
	}

	public static String toString(List chrList, String splitTag, boolean isChar) {
		String str = "";
		if (chrList != null) {
			for (int i = 0; i < chrList.size(); i++) {
				if (isChar) {
					str += "'" + chrList.get(i) + "'" + splitTag;
				} else {
					str += chrList.get(i) + splitTag;
				}
			}
			if (str.endsWith(splitTag)) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}

	public static String toString(String[] chrList, String splitTag,
			boolean isChar) {
		String str = "";
		if (chrList != null) {
			for (int i = 0; i < chrList.length; i++) {
				if (isChar) {
					str += "'" + chrList[i] + "'" + splitTag;
				} else {
					str += chrList[i] + splitTag;
				}
			}
			if (str.endsWith(splitTag)) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}

	/**
	 * 把指定分隔符的字符串转为List对象无素
	 *
	 * @param strSet
	 * @param splitTag
	 * @return
	 */
	public static List toArrayList(String strSet, String splitTag) {
		List retList = new ArrayList();
		if (strSet == null || strSet.length()==0) {
			return retList;
		}
		String[] tmp = strSet.split(splitTag);
		for (int i = 0; i < tmp.length; i++) {
			retList.add(tmp[i]);
		}
		return retList;
	}

	/**
	   * 将16进制的字符串传转换为字节流，该过程是bytesToHexString的逆过程。
	   * @param hexStr 16进制的字符串。
	   * @return byte[] 转换后得到字节流
	   * */
	  public static byte[] HexStringToBytes(String hexStr) {
	    if (hexStr == null || hexStr.length() == 0) {
	      return new byte[0];
	    } else {
	      int len = hexStr.length() / 2;
	      byte[] buf = new byte[len];
	      char bitHigh, bitLow;
	      for (int i = 0; i < len; i++) {
	        bitHigh = hexStr.charAt(2 * i);
	        bitLow = hexStr.charAt(2 * i + 1);
	        buf[i] = hexTobyte(bitHigh, bitLow);
	      }
	      return buf;
	    }
	  }

	  private static byte hexTobyte(char bitHigh, char bitLow) {
	    byte bHigh = 0, bLow = 0;
	    switch (bitHigh) {
	      case 'a':
	      case 'A':
	        bHigh = 10;
	        break;
	      case 'b':
	      case 'B':
	        bHigh = 11;
	        break;
	      case 'c':
	      case 'C':
	        bHigh = 12;
	        break;
	      case 'd':
	      case 'D':
	        bHigh = 13;
	        break;
	      case 'e':
	      case 'E':
	        bHigh = 14;
	        break;
	      case 'f':
	      case 'F':
	        bHigh = 15;
	        break;
	      default:
	        bHigh = Byte.parseByte(String.valueOf(bitHigh));
	    }
	    switch (bitLow) {
	      case 'a':
	      case 'A':
	        bLow = 10;
	        break;
	      case 'b':
	      case 'B':
	        bLow = 11;
	        break;
	      case 'c':
	      case 'C':
	        bLow = 12;
	        break;
	      case 'd':
	      case 'D':
	        bLow = 13;
	        break;
	      case 'e':
	      case 'E':
	        bLow = 14;
	        break;
	      case 'f':
	      case 'F':
	        bLow = 15;
	        break;
	      default:
	        bLow = Byte.parseByte(String.valueOf(bitLow));
	    }
	    byte bRet = (byte) ((bHigh << 4) + bLow);
	    return bRet;
	  }
		/**
		 * 为 in 操作设置 prepareStatement
		 * @param inListStr
		 * @param params
		 * @param sWhere
		 */
		public static String setInList(String inListStr, List params){
			String[] in_datas = StringUtil.split(inListStr, ',');
			String sWhere = " (";
			if(in_datas.length>0){
			   for(int i=0;i<in_datas.length;i++){
				sWhere += "?,";
				params.add(in_datas[i]);
			 }
			   sWhere = sWhere.substring(0, sWhere.length()-1);
			}
			sWhere += ")";
			return sWhere;
		}

		/**
		 * 为 in 操作设置 prepareStatement
		 * @param inListStr
		 * @param params
		 * @param sWhere
		 */
		public static String setInList(List inList, List params){
			Object[] in_datas = inList.toArray();
			String sWhere = " (";
			if(in_datas.length>0){
			   for(int i=0;i<in_datas.length;i++){
				sWhere += "?,";
				params.add(in_datas[i]);
			 }
			 sWhere = sWhere.substring(0, sWhere.length()-1);
			}
			sWhere += ")";
			return sWhere;
		}

	public static String subStringZh(String str, int length) throws UnsupportedEncodingException {

		byte[] bt = str.getBytes("gbk");
		if (length > bt.length - 1) {
			return str;
		}
		// 判断最后一个是否为负，如果是负的则丢掉该字节
		if (bt[length] < 0) {
			return new String(bt, 0, --length, "gbk");
		} else {
			return new String(bt, 0, length, "gbk");
		}
	}

	public static String str2ISO(String value) throws Exception {
		if (value == null || value.trim().equals("")) {
			return value;
		}

		return new String(value.getBytes("GBK"), "iso8859-1");
	}

	public static String iso2GBK(String value) throws Exception {
		if (value == null || value.trim().equals("")) {
			return value;
		}

		return new String(value.getBytes("iso8859-1"), "GBK");
	}

	public static String parseStr(String value) {
		if (value == null) {
			value = "";
		}
		return value;
	}

	public static String parseStr(String value, String toValue) {
		if (value == null) {
			return toValue;
		}

		return value;
	}

	public static String parseStr(Object value) {
		if (value == null) {
			value = "";
		}
		return value.toString();
	}

	public static String trimEmpty(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static byte[] convertPass(char ac[]) {
		byte abyte0[] = null;
		try {
			if (ac == null) {
				abyte0 = new byte[0];
			} else {
				ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
				OutputStreamWriter outputstreamwriter = new OutputStreamWriter(bytearrayoutputstream, "UTF-8");
				outputstreamwriter.write(ac);
				outputstreamwriter.flush();
				abyte0 = bytearrayoutputstream.toByteArray();
				outputstreamwriter.close();
			}
		} catch (UnsupportedEncodingException unsupportedencodingexception) {
		} catch (IOException ioexception) {
			throw new IllegalArgumentException("Error reading password " + ioexception.toString());
		}
		return abyte0;
	}

	public static byte[] sha1_digest(byte abyte0[]) {
		MessageDigest sha1 = null;
		try {
			sha1 = MessageDigest.getInstance("SHA");
			return sha1.digest(abyte0);
		} catch (NoSuchAlgorithmException nosuchalgorithmexception) {
			throw new IllegalArgumentException("Error disgest bytes" + nosuchalgorithmexception.getMessage());
		}
	}

	public static byte[] md5_digest(byte abyte0[]) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			return md5.digest(abyte0);
		} catch (NoSuchAlgorithmException nosuchalgorithmexception) {
			throw new IllegalArgumentException("Error disgest bytes" + nosuchalgorithmexception.getMessage());
		}
	}

	public static String replace(String s, String s1, String s2) {
		StringBuffer stringbuffer = new StringBuffer();
		int i = s1.length();
		for (int j = 0; (j = s.indexOf(s1)) != -1;) {
			stringbuffer.append(s.substring(0, j) + s2);
			s = s.substring(j + i);
		}

		stringbuffer.append(s);
		return stringbuffer.toString();
	}

	public static String replaceTag(String value, String tagStr, String subStr) {
		StringBuffer strBuffer = new StringBuffer();
		int count = 1;
		int len = tagStr.length();
		if (value == null || tagStr == null || subStr == null) {
			return value;
		}
		for (int j = 0; (j = value.indexOf(tagStr)) != -1;) {
			if (count % 2 == 0) {
				strBuffer.append(subStr);
			} else {
				strBuffer.append(value.substring(0, j));
			}
			value = value.substring(j + len);
			count++;
		}

		strBuffer.append(value);
		return strBuffer.toString();
	}

	public static final String getTagContent(String value, String tag) {
		String out = "";
		if (value != null && !value.equals("")) {
			int indexNum = value.indexOf(tag);
			if (indexNum != -1) {
				value = value.substring(indexNum + tag.length(), value.length());
				indexNum = value.indexOf(tag);
				if (indexNum != -1) {
					out = value.substring(0, indexNum);
				} else {
					out = value;
				}
			}
		}
		return out;
	}

	public static final String formatTag(String tag) {
		String returnTag = tag;
		if (returnTag != null) {
			returnTag = "<!--%" + returnTag + "%-->";
		}
		return returnTag;
	}

	public static final String htmlEncode(String value) {
		StringBuffer out = new StringBuffer();
		if (value == null) {
			return "";
		}
		for (int i = 0; i < value.length(); i++) {
			out.append(emitQuote(value.charAt(i)));
		}

		return out.toString();
	}

	private static String emitQuote(char c) {
		String ls_Out = null;
		if (c == '&') {
			ls_Out = "&amp;";
		} else if (c == '"') {
			ls_Out = "&quot;";
		} else if (c == '<') {
			ls_Out = "&lt;";
		} else if (c == '>') {
			ls_Out = "&gt;";
		} else {
			ls_Out = String.valueOf(c);
		}
		return ls_Out;
	}
	
	/**
	 * 把HTML格式字符转为特殊字符
	 *
	 * @param as_Value
	 * @return
	 * @throws Exception
	 */
	public static String htmlDecode(String content) throws Exception {
		
		String decodeString = "";
		if (content.length() == 0)
			return "";
		decodeString = content.replaceAll("&amp;", "&");
		decodeString = decodeString.replaceAll("&lt;", "<");
		decodeString = decodeString.replaceAll("&gt;", ">");
		decodeString = decodeString.replaceAll("&quot;", "\"");
		return decodeString;
	}
	// 将参数编码
	public static String encodeParam(String param) throws IOException {
		Base64 base64Encoder = new Base64();
		if (param == null) {
			return "";
		}
		try {
			param = base64Encoder.encodeToString(param.getBytes());
		} finally {
			base64Encoder = null;
		}
		return param;
	}

	// 将参数解码
	public static String decodeParam(String param) {
		try {
			param = URLDecoder.decode(param, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Base64 base64Encoder = new Base64();
		if (param == null || param.trim().equals("null")) {
			return "";
		}
		try {
			param = base64Encoder.decode(param).toString();
		} finally {
			base64Encoder = null;
		}
		return param;
	}

	public static final String chinese2uicode(String szOrg) {
		if (szOrg == null) {
			return null;
		}
		String szRet = "";
		try {
			char letter[] = { 'A', 'B', 'C', 'D', 'E', 'F' };
			for (int i = 0; i < szOrg.length(); i++) {
				int ch0 = szOrg.charAt(i);
				if (ch0 < 0 || ch0 > 255) {
					szRet = szRet + "";
					for (int j = 3; j >= 0; j--) {
						int ch = ((ch0 >>> (j * 4)) & 0x000f);
						if (ch < 10) {
							szRet = szRet + Integer.toString(ch);
						} else {
							szRet = szRet + letter[ch - 0x0a];
						}
					}
					szRet = szRet + ";";
				} else {
					szRet = szRet + szOrg.charAt(i);
				}
			}
		} catch (Exception e) {
		}
		return szRet;
	}
	
	 /**
     * 读取管道中的流数据 
     * 
     * @param inStream
     * @return
     */
	public static byte[] readStream(InputStream inStream) {
		ByteArrayOutputStream bops = new ByteArrayOutputStream();
		int data = -1;
		try {
			while ((data = inStream.read()) != -1) {
				bops.write(data);
			}
			return bops.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 *	bean转map
	 *	@author hefule
	 *	@date 2017年2月21日 下午2:18:56
	 *
	 */
	public static Map<String, Object> BeanToMap(Object obj) {  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                // 过滤serialVersionUID属性  
                if (!key.equals("serialVersionUID")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    map.put(key, value);  
                }  
            }  
        } catch (Exception e) {}  
        return map;  
    }  
	
	/***
	 * getUUID:(获得uuid编号)
	 * @author lixingfa
	 * @date 2018年7月4日下午7:18:21
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * getSubStringByLR:(根据成对原则，获取与当前位置成对的子串，用于提取代码片段，会过滤掉注释)
	 * @author lixingfa
	 * @date 2018年7月4日下午7:39:56
	 * @param begin 开始位置，不要求直接从left开始，程序会自动校准到最近的一个left
	 * @param left 左边，如{([
	 * @param right 右边 如}])
	 * @param content
	 * @return 从left到right的内容
	 */
	public static String getSubStringByLR(int begin,char left,char right,String content){
		StringBuffer s = new StringBuffer();
		content = content.substring(begin);
		//校准begin
		begin = content.indexOf(left);//确保字符串从left开始
		if (begin > 0) {
			content = content.substring(begin);			
		}
		char[] c = content.toCharArray();
		boolean singleNote = false;//单行注释
		int note = 0;//多行注释
		int flag = 0;//left\right标志
		int i = 0;
		//校准begin
		//逻辑开始，如果不需要去掉注释，另外起一个方法，去掉singleNote和note就行
		do {
			if (c[i] == left) {
				flag++;
			}else if (c[i] == right) {
				flag--;
			}else if (c[i] == '/' && (i + 1) < c.length) {
				if (c[i + 1] == '/') {//单行注释
					singleNote = true;
					i = i + 2;
					continue;
				}else if (c[i + 1] == '*') {//多行注释
					note++;
					i = i + 2;
					continue;
				}
			}else if(c[i] == '*'  && (i + 1) < c.length && c[i + 1] == '/'){
				note--;
				i = i + 2;
				continue;
			}else if (singleNote && (c[i] == '\n' || c[i] == '\t')) {//换行后单行注释失效
				singleNote = false;
				continue;
			}
			//非注释内部，不是空内容
			if (note == 0 && !singleNote) {
				s.append(c[i]);				
			}
			i++;
		} while (flag != 0);
		begin = s.indexOf(String.valueOf(left));
		return s.substring(begin).toString();
	}
	
	/**
	 * findTheVariate:(在指定内容中寻找变量的值)
	 * @author lixingfa
	 * @date 2018年7月5日下午4:41:04
	 * @param variate 变量名称
	 * @param index 变量所在的内容区（适当的内容区容易定位）
	 * @param content 查找区域
	 * @return String 变量的值
	 */
	public static String findTheVariate(String variate,int index,String content){
		int dist = Integer.MAX_VALUE;
		String value = null;//var url = '${root}/appr/monitor/book/deleteBook.do?books='+books.join(",");
		List<String> matchers = getMatchers("var[ ]+" + variate + "[ ]*=['\"\\w/.?=+&${}\\[\\](,) ]+[;]{1}", content);
		for (String s : matchers) {
			int flag = index - content.indexOf(s);
			if (flag < dist && flag > 0) {//在变量使用之前
				dist = flag;
				value = s;
			}
		}
		return value.substring(value.indexOf("=") + 1).replace(";", "").trim();
	}
	
	/**
	 * getMatchers:(这里用一句话描述这个方法的作用)
	 * @author lixingfa
	 * @date 2018年7月6日下午5:29:09
	 * @param p 定义的正则表达式
	 * @param content 要搜索的内容
	 * @return 匹配的字符
	 */
	public static List<String> getMatchers(String p,String content){
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}
	
	/**
	 * getSubStringForward:(给出截止位置和开始字符，寻找字符串)
	 * @author lixingfa
	 * @date 2018年7月17日上午9:17:24
	 * @param forWardChar 开始字符
	 * @param endIndex 截止位置
	 * @param content 被寻找的内容
	 * @return String
	 */
	public static String getSubStringForward(char forWardChar,int endIndex,String content){
		content = content.substring(0,endIndex);
		if (content.contains(String.valueOf(forWardChar))) {
			return content.substring(content.lastIndexOf(forWardChar)); 
		}else {
			return null;
		}
	}
	
	/**
	 * replaceMatchers:(替换字符串中与正则表达式匹配的内容)
	 * @author lixingfa
	 * @date 2018年7月17日下午8:17:20
	 * @param p 正则表达式
	 * @param replace 用于替换的字符串
	 * @param content 被操作的字符串
	 * @return String
	 */
	public static String replaceMatchers(String p,String replace,String content){
		List<String> matchers = getMatchers(p, content);
		//数字的比较容易排序，字符串会跟期望不一样。这里是希望按长度排前面，避免'+先替换了，导致'+'只剩右边的'了
		Collections.sort(matchers);//String不好重新定义，只好用本办法实现
		for (int i = 0; i < matchers.size(); i++) {
			for (int j = i + 1; j < matchers.size(); j++) {
				if(matchers.get(j).length() > matchers.get(i).length()){
					String index = matchers.get(j);
					matchers.set(j, matchers.get(i));
					matchers.set(i, index);
				}
			}
			content = content.replace(matchers.get(i), replace);
		}
		return content;
	}
	
	/**
	 * 格式化JSON
	 * @param s JSON字符串
	 * @return 格式化后的字符串
	 */
	public static String getJsonFormat(String s){
		int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int index=0;index<s.length();index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = s.charAt(index);
            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
            case '{':
            case '[':
                jsonForMatStr.append(c + "\n");
                level++;
                break;
            case ',':
                jsonForMatStr.append(c + "\n");             
                break;
            case '}':
            case ']':
                jsonForMatStr.append("\n");
                level--;
                jsonForMatStr.append(getLevelStr(level));
                jsonForMatStr.append(c);
                break;
            default:
                jsonForMatStr.append(c);
                break;
            }
        }        
        return jsonForMatStr.toString();
	}
	
	private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
	
	public static void main(String[] args) {
		String content = "var url = '${root}/admin/system/user/loadUserByUnit.do?unitSubCode='+unitCode+'&userRole='+'${userModel.userRole}';";
		System.out.println(replaceMatchers("['\"]?[ ]*[\\+]{1}[ ]*['\"]?", "", content));
	}
}
