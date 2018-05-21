package com.garlane.relation.common.utils.change;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

/**
 * 编码的工具类
 * @author liyhu
 * @date 2017年12月5日下午12:01:54
 */
public class URLCodeUtil {

    private static final Logger logger = Logger.getLogger(URLCodeUtil.class);
    private static final String encoding="UTF-8";
    
    /**
     * 
     * encode:(对url参数进行编码). <br/> 
     * @author liyhu
     * @date 2017年12月5日下午12:04:32
     * @param str
     * @return
     */
    public static String encode(String str){
        try {
            return URLEncoder.encode(str, encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return str;
    }
    /**
     * 
     * decode:(解码). <br/> 
     * @author liyhu
     * @date 2017年12月5日下午12:06:28
     * @param str
     * @return
     */
    public static String decode(String str){
        try {
            return URLDecoder.decode(str, encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return str;
    }
}
