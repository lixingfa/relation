package com.garlane.relation.common.utils.encrypt;

import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 *	编码工具类
 *  实现aes加密、解密 
 *	@author hefule
 *	@date 2017年1月20日 下午5:46:35
 *	@see 秘钥需要8位
 */	
public class EncryptUtils {
	
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";  
    
    private static KeyGenerator kgen;
    
    private static Cipher cipher;
    static{
    	 try {
			kgen = KeyGenerator.getInstance("AES");
			cipher = Cipher.getInstance(ALGORITHMSTR);
		} catch (Exception e) {
			
		}  
    }
  
//    public static void main(String[] args) throws Exception {  
//    	for(int i=0;i<10000;i++){
//        String content =getRandomString(8);  
//        String KEY = getRandomString(8);
//        
//        System.out.println("加解密秘钥："+ KEY);
//        
//        System.out.println("加密前：" + content);  
//  
//        String encrypt = aesEncrypt(content, KEY);  
//        System.out.println("加密后：" + encrypt);  
//  
//        String decrypt = aesDecrypt(content, KEY);  
//        System.out.println("解密后：" + decrypt);  
//    	}
//    }  
    
    /**
   	 *	随机生成字符串
   	 *	@author hefule
   	 *	@date 2017年1月21日 下午12:06:06
   	 *
   	 */
   public static String getRandomString(int length) { //length表示生成字符串的长度  
       String base = "abcdefghijklmnopqrstuvwxyz0123456789!~*$_-";     
       Random random = new Random();     
       StringBuffer sb = new StringBuffer();     
       for (int i = 0; i < length; i++) {     
           int number = random.nextInt(base.length());     
           sb.append(base.charAt(number));     
       }     
       return sb.toString();     
    } 
    
    /**
	 *	加密公用方法
	 *	@author hefule
	 *	@date 2017年1月21日 上午11:27:35
	 *	@param content 需要加密的字段
	 *	@param encryptKey 秘钥
	 *
	 */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
    	if(encryptKey==null || "".equals(encryptKey) || encryptKey.length()!=16)
    		return content;
        return base64Encode(aesString(content.getBytes("utf-8"), encryptKey,Cipher.ENCRYPT_MODE));  
    }  
  
  
    /**
	 *	解密公用方法
	 *	@author hefule
	 *	@date 2017年1月21日 上午11:27:35
	 *	@param content 需要解密的字段
	 *	@param encryptKey 秘钥
	 *
	 */
    public static String aesDecrypt(String content, String encryptKey) throws Exception {  
    	if(encryptKey==null || "".equals(encryptKey) || encryptKey.length()!=16)
    		return content;
        return  new String(aesString(base64Decode(content), encryptKey,Cipher.DECRYPT_MODE));  
    }  

    /**
	 *	base 64 encode 
	 *	@author hefule
	 *	@date 2017年1月21日 上午11:29:20
	 *	@param bytes 待编码的byte[] 
	 *	@return 编码后的base 64 code 
	 *
	 */
    private static String base64Encode(byte[] bytes){  
    	return Base64.encodeBase64String(bytes);
    }  
  
    /**
	 *	base 64 encode 
	 *	@author hefule
	 *	@date 2017年1月21日 上午11:29:20
	 *	@param bytes 待解码的字符串 
	 *	@return 解码码后的base 64 code 
	 *
	 */ 
    private static byte[] base64Decode(String content) throws Exception{  
        return Base64.decodeBase64(content);  
    }  
  
    /**
	 *	加解密算法执行
	 *	@author hefule
	 *	@date 2017年1月21日 上午11:30:03
	 *
	 */  
    private static byte[] aesString(byte[] content, String encryptKey,int mode) throws Exception {  
    	 kgen.init(128, new SecureRandom(encryptKey.getBytes()));  
         SecretKey secretKey = kgen.generateKey();  
         byte[] enCodeFormat = secretKey.getEncoded();  
         SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
         cipher.init(mode, key);// 初始化  
        return cipher.doFinal(content);  
    }
    
}
