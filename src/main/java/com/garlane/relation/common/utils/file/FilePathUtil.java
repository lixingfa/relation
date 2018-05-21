package com.garlane.relation.common.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class FilePathUtil {
    private final static Logger LOGGER = Logger.getLogger(FilePathUtil.class);

    // 不同系统下默认分隔符
    private final static String LINUX_SEPAR = "/";

    private final static String WINDOW_SEPAR = "\\";

    // File协议头部固定写法
    private final static String PROTOCOL_FILE_HEAD = "file:///";

    // 当前服务器是否为WINDOWS系统
    private final static boolean IS_WINDOWS_OS;

    // 正确的文件夹分隔符，根据服务器实际情况自动判断
    private final static String REAL_SEPAR;

    // 错误的文件夹分隔符，根据服务器实际情况自动判断
    private final static String ERRO_SEPAR;

    static {
        IS_WINDOWS_OS = File.separator == WINDOW_SEPAR;
        REAL_SEPAR = IS_WINDOWS_OS ? WINDOW_SEPAR : LINUX_SEPAR;
        ERRO_SEPAR = IS_WINDOWS_OS ? LINUX_SEPAR : WINDOW_SEPAR;
    }

    /**
     * 测试方法。。 测试的时候\在字符串要用\\来表示，实际效果跟数据库中读取的\效果一样
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String pppp1 = "file:///D://20169334850//bak////////";
        String pppp2 = "/\\banjian\\mys\\";
        String pppp3 = "\\穗开审批规验证【2016】23号(400_12薛娇).pdf";
        String xxxx1 = "d:\\201612641492\\banjinhao\\";
        String xxxx2 = "//开发区1128-系统完善意见及问题记录表最新版(74_2眭健波).xlsx";

        FilePathUtil.joinPath(pppp1, pppp2, pppp3);
        FilePathUtil.joinPath(xxxx1, xxxx2);
        // String aa = StringUtils.replace("\201612641492\\", "/", "\\\\");
    }

    /**
     * 按顺序传入文件路径或文件名，自动按服务器操作系统种类拼接文件路径
     * 
     * @param paths
     * @return
     * @throws Exception
     */
    public static String joinPath(String... paths) throws Exception {
        return joinFilePath(null, false, paths);
    }

    private static String joinFilePath(String prefix, boolean isAbsolutePath, String... paths) throws Exception {
        LOGGER.debug("当前系统为：" + (IS_WINDOWS_OS ? "WINDOWS" : "LINUX"));
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < paths.length; i++) {
            if (paths[i] == null) {
                throw new Exception("拼接文件路径异常:第" + (i + 1) + "个文件路径参数为null");
            }
            if (i == 0 && paths[i].toLowerCase().startsWith(PROTOCOL_FILE_HEAD)) {
                if (paths[i].length() > 8) {
                }
                String tmp = StringUtils.replace(paths[i].substring(8), ERRO_SEPAR, REAL_SEPAR);
                String[] tmpArr = StringUtils.split(tmp, REAL_SEPAR);
                List<String> tmpList = new ArrayList<String>();
                for (String element : tmpArr) {
                    if (StringUtils.isNotEmpty(element)) {
                        tmpList.add(element);
                    }
                }
                String tmpRealStr = PROTOCOL_FILE_HEAD + StringUtils.join(tmpList.toArray(), REAL_SEPAR);
                list.add(tmpRealStr);
            } else {
                paths[i] = StringUtils.replace(paths[i], ERRO_SEPAR, REAL_SEPAR);
                String[] arr1 = StringUtils.split(paths[i], REAL_SEPAR);
                for (String element : arr1) {
                    if (StringUtils.isNotEmpty(element)) {
                        list.add(element);
                    }
                }
            }
        }

        String realStr = StringUtils.join(list.toArray(), REAL_SEPAR);
        if (isAbsolutePath && IS_WINDOWS_OS) {
            realStr = REAL_SEPAR + realStr;
        }
        LOGGER.debug(realStr);
        System.out.println(realStr);
        return realStr;
    }
}
