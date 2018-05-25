package com.garlane.relation.common.servlet.uitl;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.garlane.relation.common.servlet.FormDataServlet;
import com.garlane.relation.common.servlet.StreamServlet;

public class IoUtil {
static final Pattern RANGE_PATTERN = Pattern.compile("bytes \\d+-\\d+/\\d+");

//private static final String REPOSITORY = "D:/attachFiles/stream";
    private static final Logger logger = Logger.getLogger(FormDataServlet.class);
    /**
     * According the key, generate a file (if not exist, then create
     * a new file).
     * @param filename
     * @param fullPath the file relative path(something like `a../bxx/wenjian.txt`)
     * @return
     * @throws IOException
     */
    public static File getFile(String filePath,String filename) throws IOException {
        if (filePath == null || filePath.isEmpty()
                ||filename == null || filename.isEmpty())
            return null;

        String name = filename.replaceAll("/", Matcher.quoteReplacement(File.separator));

        File f = new File(filePath + File.separator + name);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            f.createNewFile();
        
        return f;
    }

    /**
     * Acquired the file.
     * @param key
     * @return
     * @throws IOException 
     */
    public static File getTokenedFile(String filePath,String key) throws IOException {
        if (filePath == null || filePath.isEmpty()
                ||key == null || key.isEmpty())
            return null;

        File f = new File(filePath + File.separator + key);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            f.createNewFile();
        
        return f;
    }
    
    
    /**
     * close the IO stream.
     * @param stream
     */
    public static void close(Closeable stream) {
        try {
            if (stream != null)
                stream.close();
        } catch (IOException e) {
        }
    }
    
    /**
     * 获取Range参数
     * @param req
     * @return
     * @throws IOException
     */
    public static StreamRange parseRange(HttpServletRequest req) throws IOException {
        String range = req.getHeader(StreamServlet.CONTENT_RANGE_HEADER);

        Matcher m = RANGE_PATTERN.matcher(range);
        if (m.find()) {
            range = m.group().replace("bytes ", "");
            String[] rangeSize = range.split("/");
            String[] fromTo = rangeSize[0].split("-");

            long from = Long.parseLong(fromTo[0]);
            long to = Long.parseLong(fromTo[1]);
            long size = Long.parseLong(rangeSize[1]);
            return new StreamRange(from, to, size);
        }
        throw new IOException("Illegal Access!");
    }


    /**
     * From the InputStream, write its data to the given file.
     */
    public static void streaming(InputStream in, String filePath,String key,String controlSeq, String fileName) throws IOException {
        OutputStream out = null;
        File f = getTokenedFile(filePath,key);
        try {
            out = new FileOutputStream(f);
            int read = 0;
            final byte[] bytes = new byte[FormDataServlet.BUFFER_LENGTH];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        } finally {
            close(out);
        }
    }
    
    
    public static long streaming(InputStream in, String key,String filePath, String fileName) throws IOException {
        OutputStream out = null;
        File f = getTokenedFile(filePath, key);
        String name = filePath + fileName;
        logger.info("=========key:" + f + ",filePath:" + filePath + ",fileName:" + fileName);
        try {
            out = new FileOutputStream(f);
            logger.info("=========进入上传方法out:" + out);
            int read = 0;
            byte[] bytes = new byte[10485760];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        }catch(Exception e){
            logger.error("=========出错：" + e);
        }finally {
            close(out);
        }
        //重名名上传文件
        File dst = getFile(name);
        dst.delete();
        f.renameTo(dst);

        long length = getFile(name).length();
        if (Configurations.isDeleteFinished()) {
            dst.delete();
        }

        return length;
    }

    
    /**
     * According the key, generate a file (if not exist, then create
     * a new file).
     * @param filename
     * @param fullPath the file relative path(something like `a../bxx/wenjian.txt`)
     * @return
     * @throws IOException
     */
    public static File getFile(String filename) throws IOException {
        if (filename == null || filename.isEmpty())
            return null;
        String name = filename.replaceAll("/", Matcher.quoteReplacement(File.separator));
        File f = new File(File.separator + name);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            f.createNewFile();
        
        return f;
    }
}