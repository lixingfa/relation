package com.garlane.relation.common.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.common.constant.ConfigConstant;
import com.garlane.relation.common.servlet.uitl.IoUtil;
import com.garlane.relation.common.utils.AttachmentUtil;

/**
 * FormData Uploading reserved servlet, mainly reading the request parameter and
 * its file part, stored it.
 * PS: use the `streaming api`, this will not store it in a temporary file.
 * {@link http://commons.apache.org/proper/commons-fileupload/streaming.html }
 */
public class FormDataServlet extends HttpServlet {
    private static final long serialVersionUID = -1905516389350395696L;
    
    private static final Logger logger = Logger.getLogger(FormDataServlet.class);
    
    static final String FILE_FIELD = "file";
    /**
     * when the has read to 10M, then flush it to the hard-disk.
     */
    public static final int BUFFER_LENGTH = 1024 * 1024 * 10;
    static final int MAX_FILE_SIZE = 1024 * 1024 * 100;

    @Override
    public void init() throws ServletException {
    }
    
    
    /**
     * 表单上传模式,主要兼容低版本ie,例如IE8,不支持续点续传,没有做文件大小校验(在页面实现)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doOptions(req, resp);
        logger.debug("=======IE8浏览器上传材料=========");
        /** flash @ windows bug */
        req.setCharacterEncoding("utf8");

        final PrintWriter writer = resp.getWriter();
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            writer.println("出错了: 提交内容不是多媒体表单");
            logger.error("出错了: 提交内容不是多媒体表单");
            return;
        }
        JSONObject json = new JSONObject();
        long start = 0;
        boolean success = true;
        String message = "";

        ServletFileUpload upload = new ServletFileUpload();
        InputStream in = null;
        String token = null;       
        String fileName = null;
        String customSavePath = null;
        String uuidFileName  = null;
        String filePath = null;
     
        try {
            FileItemIterator iter = upload.getItemIterator(req);
            //Map<String, Object> map = new HashMap<String, Object>();
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                in = item.openStream();
                if (item.isFormField()) {
                    String value = Streams.asString(in);
                    if (TokenServlet.TOKEN_FIELD.equals(name)) {
                        token = value;
                        /** TODO: validate your token. */
                    }                    
                    if(TokenServlet.FILE_NAME_FIELD.equals(name)){
                        fileName = value;
                    }
                    if(TokenServlet.FILE_PATH_FIELD.equals(name)){
                        customSavePath = value;
                    }
                    logger.info("----------"+name + ":" + value);
                } else {
                    
                    if (token == null || token.trim().length() < 1)
                        token = req.getParameter(TokenServlet.TOKEN_FIELD);
                    /** TODO: validate your token. */
                    // 这里不能保证token能有值
                    fileName = item.getName();
                    if (token == null || token.trim().length() < 1)
                        token = fileName;
                    logger.info("----------token:"+ token + ",fileName:" + fileName);
                   //这里组装路径
                   uuidFileName = AttachmentUtil.getUuidFileName(fileName);
                   filePath  = ConfigConstant.ROOT_PATH_ATTACHMENT + customSavePath;
                   logger.debug("=======filePath:" + filePath);
                   start = IoUtil.streaming(in, token, filePath, uuidFileName);
                   logger.debug("--------上传完成---------");
                }
            }
            logger.info("Form Saved : " + filePath);
        } catch (Exception fne) {
            logger.error("=========上传出错：" + fne + ",Error:" + fne.getLocalizedMessage());
            success = false;
            message = "Error: " + fne.getLocalizedMessage();
        } finally {
             try {
                if (success){
                    json.put(StreamServlet.START_FIELD, start);
                    json.put(TokenServlet.FILE_UUID_NAME_FIELD, uuidFileName);
                }
                json.put(TokenServlet.SUCCESS, success);
                json.put(TokenServlet.MESSAGE, message);
            } catch (JSONException e) {
                logger.error("返回结果生产json异常", e);
            }
            writer.write(json.toString());
            IoUtil.close(in);
            IoUtil.close(writer);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
