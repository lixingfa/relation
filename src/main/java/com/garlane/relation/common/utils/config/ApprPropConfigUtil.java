package com.garlane.relation.common.utils.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringUtils;


/**
 * 加载property文件类 原生PropertyPlaceholderConfigurer加载property只能用于xml中占位符替换
 * 修改后将键值对保存在静态map中，供java程序引用
 * 
 * @author zyf
 * 
 */
public class ApprPropConfigUtil extends PropertyPlaceholderConfigurer {
	private final Logger logger = Logger.getLogger(ApprPropConfigUtil.class);
	private final static Map<String, String> resolvedProps = new HashMap<String, String>();// 将属性保存起来
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

		Set<Object> keySets = props.keySet();
		if (keySets != null && keySets.size() != 0) {
			logger.info("加载域环境目录下appr.properties文件");
		}
		
		for (Object key : keySets) {
			String mapKey = key.toString();
			String mapVal = props.getProperty(mapKey);
			//logger.info("加载参数：" + mapKey + "--->>>" + mapVal);
			resolvedProps.put(mapKey, mapVal);
		}
		
		// 回调父类方法
		super.processProperties(beanFactoryToProcess, props);
	}

	public static String get(String key) {
		return resolvedProps.get(key);
	}
	
	public static String get(String key, String defaultVal) {
		String realValue = resolvedProps.get(key);
		if (StringUtils.isEmpty(realValue)) {
			realValue = defaultVal;
		}
		return realValue;
	}

	public static Iterator<String> keys() {
		return resolvedProps.keySet().iterator();
	}

	/**
	 * 允许获取全局map，但不允许修改
	 * @author 杨朝敬
	 * @date   2016年3月21日 上午10:50:05
	 * @return
	 */
	public static Map<String, String> getResolvedprops() {
		return Collections.unmodifiableMap(resolvedProps);
	}

}
