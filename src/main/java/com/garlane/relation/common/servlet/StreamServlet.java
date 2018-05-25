package com.garlane.relation.common.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.garlane.relation.common.constant.ConfigConstant;
import com.garlane.relation.common.servlet.uitl.IoUtil;
import com.garlane.relation.common.servlet.uitl.StreamException;
import com.garlane.relation.common.servlet.uitl.StreamRange;
import com.garlane.relation.common.utils.AttachmentUtil;

/**
 * File reserved servlet, mainly reading the request parameter and its file
 * part, stored it.
 */
public class StreamServlet extends HttpServlet {
    
    private static final long serialVersionUID = -4508191159365889082L;
    
    private static final Logger logger = Logger.getLogger(StreamServlet.class);
    
    /** when the has increased to 10kb, then flush it to the hard-disk. */
    static final int BUFFER_LENGTH = 10240;
    static final String START_FIELD = "start";
    public static final String CONTENT_RANGE_HEADER = "content-range";

    @Override
    public void init() throws ServletException {
    }
    
    /**
     * Lookup where's the position of this file? 判断文件是否已经存在或者已经上传完成
     * 如果文件存在并且未上传完成则返回断点大小并继续文件上传
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        doOptions(req, resp);
        
        JSONObject json = new JSONObject();
        final PrintWriter writer = resp.getWriter();
        final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
        final String size = req.getParameter(TokenServlet.FILE_SIZE_FIELD);
        final String fileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);
        final String customSavePath = req.getParameter(TokenServlet.FILE_PATH_FIELD);
        final String filePath = ConfigConstant.ROOT_PATH_ATTACHMENT + customSavePath;
        String newFileName = fileName;
        long start = 0;
        boolean success = true;
        String message = "";
        try {
            File f = IoUtil.getTokenedFile(filePath, token);
            start = f.length();
            if("0".equals(size)){
                message = "Error: 文件为空！";
                success = false;
                return;
            }
            /** file size is 0 bytes. */
            if (token.endsWith("_0") && "0".equals(size) && 0 == start) {
                f.renameTo(IoUtil.getFile(filePath, newFileName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error: " + e.getMessage();
            success = false;
        } finally {
            try {
                if (success)
                    json.put(START_FIELD, start);
                json.put(TokenServlet.SUCCESS, success);
                json.put(TokenServlet.MESSAGE, message);
            } catch (JSONException e) {}
            writer.write(json.toString());
            IoUtil.close(writer);
        }
    }
    
    /***
     * 正式开始上传文件
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doOptions(req, resp);
        
        final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
        final String fileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);
        final String customSavePath = req.getParameter(TokenServlet.FILE_PATH_FIELD);
        //这里组装路径
        final String filePath = ConfigConstant.ROOT_PATH_ATTACHMENT + customSavePath;
        StreamRange range = IoUtil.parseRange(req);
        
        OutputStream out = null;
        InputStream content = null;
        final PrintWriter writer = resp.getWriter();
        
        /** TODO: validate your token. */
        
        JSONObject json = new JSONObject();
        long start = 0;
        boolean success = true;
        String message = "";
        logger.debug("filePath:"+filePath+",token:"+token);
        File f = IoUtil.getTokenedFile(filePath, token);
        logger.debug("-------path:"+f.getPath());
        try {
            if (f.length() != range.getFrom()) {
                /** drop this uploaded data */
                throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
            }
            
            out = new FileOutputStream(f, true);
            content = req.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[BUFFER_LENGTH];
            while ((read = content.read(bytes)) != -1){
                out.write(bytes, 0, read);
            }
            start = f.length();
        } catch (StreamException se) {
            success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
            message = "Code: " + se.getCode();
        } catch (FileNotFoundException fne) {
            message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
            success = false;
        } catch (IOException io) {
            message = "IO Error: " + io.getMessage();
            success = false;
        } finally {
            IoUtil.close(out);
            IoUtil.close(content);
            /** rename the file */
            String newFileName = null;
            if (range.getSize() == start) {
                long maxSize = ConfigConstant.DEFAULT_UPLOADFILE_MAX_SIZE;
                if(range.getSize()>maxSize){
                    success = false;
                    message = "最大文件大小： " + maxSize
                            + " 字节 但是" + fileName + " 是：" + range.getSize()+" 字节";
                }else{
                    newFileName = AttachmentUtil.getUuidFileName(fileName);
                    
                    /** fix the `renameTo` bug */
//                  File dst = IoUtil.getFile(fileName);
//                  dst.delete();
                    // TODO: f.renameTo(dst); 重命名在Windows平台下可能会失败，stackoverflow建议使用下面这句
                    try {
                        // 先删除
                        File dst = IoUtil.getFile(filePath, newFileName);
                        dst.delete();
                        f.renameTo(dst);
                        //Files.move(f.toPath(), f.toPath().resolveSibling(fileName));
                        logger.info("newFileName: `" + newFileName + "`, NE: `" + dst.getPath() + "`");
                    } catch (IOException e) {
                        success = false;
                        message = "Rename file error: " + e.getMessage();
                    }
                }
            }
            try {
                if (success) {
                    json.put(START_FIELD, start);
                    json.put(TokenServlet.FILE_UUID_NAME_FIELD, newFileName);
                }
                json.put(TokenServlet.SUCCESS, success);
                json.put(TokenServlet.MESSAGE, message);
            } catch (JSONException e) {}
            
            writer.write(json.toString());
            IoUtil.close(writer);
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
