package com.garlane.relation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

public class PropConfig {
	private Properties prop;
	/**项目名称*/
	public static String projectName;
	/**项目路径*/
	public static String projectPath;
	/**提供参考页面的路径*/
	public static String pagePath;
	/**项目编码*/
	public static String encoding;
	/**展示层框架*/
	public static String View;	
	
	public PropConfig(){
		if (prop == null) {
			prop = new Properties();
			InputStream in = getClass().getResourceAsStream("project.properties");//从当前类的目录下面找
			try {
				prop.load(in);
				projectName = prop.getProperty("projectName");
				projectPath = prop.getProperty("projectPath");
				pagePath = prop.getProperty("pagePath");
				encoding = prop.getProperty("encoding");
				View = prop.getProperty("View").toLowerCase();//展示层框架
			} catch (IOException e) {
				System.out.println("读取配置文件出错，请检查路径是否正确");
				e.printStackTrace();
			}
			//日志
			PropertyConfigurator.configure("log4j.properties");
		}
	}
}
