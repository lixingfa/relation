package com.garlane.relation.common.utils.file;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.garlane.relation.common.constant.ConfigConstant;

/**
 * 附件类的工具类
 * @author wengsw
 * @date 2017年4月17日上午11:20:52
 */
public class AttachmentUtil {

    /**
     * 
     * createUploadFileSavePath:(统一生成不带根路径路径). <br/>
     * @author wengsw
     * @date 2017年4月17日上午11:22:12
     * @return String
     */
    public static String createUploadFileSavePath() {
        // 申报材料时候所需文件存储根路径
        Calendar cal = Calendar.getInstance(); // 使用日历类
        int year = cal.get(Calendar.YEAR); // 得到年
        int month = cal.get(Calendar.MONTH) + 1; // 得到月，因为从0开始的，所以要加1
        int day = cal.get(Calendar.DAY_OF_MONTH); // 得到天
        return File.separator + String.valueOf(year) + File.separator + String.valueOf(month) + File.separator
                + String.valueOf(day) + File.separator;
    }

    /**
     * 
     * encodeFileName:(附件下载附件名称编码). <br/>
     * @author wengsw
     * @date 2017年4月17日上午11:25:10
     * @param request
     * @param fileName
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String encodeFileName(HttpServletRequest request, String fileName) 
            throws UnsupportedEncodingException {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        String result = "";
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1) {
            //IE 解决文件名包含空格，下载后空格变加号问题
            result = encodeToUTF8(fileName).replace("+", "%20");
        } else {
            Charset charset =  Charset.defaultCharset();
            String str = new String(fileName.getBytes(charset), "ISO8859-1");
            //火狐浏览器，解决文件名包含空格， 下载后文件名丢失空格后面的内容（包括文件后缀）
            result = "\"" + str + "\"";
        }
        return result;
    }
    
    /**
     * encodeToUTF8:(按UTF-8进行url编码). <br/> 
     * @author wengsw
     * @date 2017年5月22日上午11:42:14
     * @param fileName
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String encodeToUTF8(String fileName)throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, "UTF-8");
    }

    /**
     * 获取物理文件方法，先按新版获取文件取不到才拼接旧版路径获取
     * @author wengsw
     * @date 2017年1月11日
     * @param basePath 基础路径(这里仅作为旧版路径使用,故需要旧版路径)
     * @param attachPath 数据库路径
     * @return File
     */
    public static File getDiskFile(String basePath, String attachPath) {
        // 新版上传数据库直接存文件全路径
        File file = new File(attachPath);
        if (!file.exists()) {
            // 没有找到文件再尝试旧版路径
            /* 2017/10/28/
             * 把 basePath 换成 ConfigConstant.ROOT_PATH_ATTACHMENT(配置文件配置的根路径)
             * 原因:附件路径从绝对路径换成相对路径,所以要这样改
             */
        	file = new File(ConfigConstant.ROOT_PATH_ATTACHMENT + attachPath);
        }
        return file;
    }
    
    
    /**
     * 
     * getUuidFileName:(根据文件名生成uuid形式的文件名称). <br/> 
     * @author wengsw
     * @date 2017年11月9日上午10:31:07
     * @param fileName
     * @return 例:test.jpg->44a4fc754f3f4079a91468a23bc4241e.jpg
     */
    public static String getUuidFileName(String fileName){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if(StringUtils.isNotBlank(fileName)){
            int index = fileName.lastIndexOf(".");
            if(index!=-1){
                String suffix = fileName.substring(index);
                uuid += suffix;
            }
        }
        return uuid;
    }

}
