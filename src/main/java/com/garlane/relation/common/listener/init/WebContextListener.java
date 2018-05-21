package com.garlane.relation.common.listener.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.garlane.relation.common.constant.NumConstant;

/**
 *	初始化项目时执行
 *	@author lixingfa
 *	@date 2017年8月29日 下午4:02:21
 *
 */
@Component("webContextListener")
public class WebContextListener implements ServletContextListener, ApplicationListener<ApplicationEvent>{
	
	private final static Logger logger = Logger.getLogger(WebContextListener.class);
	
	private final static String systemName = "relation";
	
	/**系统启动加载次数*/
	private int systemStartNum = NumConstant.COMMON_NUMBER_ONE;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		loadSystemStartOut(true,null);
	}


	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ContextRefreshedEvent){
			loadSystemStartOut(false,(ContextRefreshedEvent) event);
		}
		/*else if(event instanceof ContextClosedEvent){
			loadSystemEndOut(true,(ContextClosedEvent) event);
		}*/
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//loadSystemEndOut(false,null);
	} 
	
	/**
	 *	系统启动输出函数
	 *	@author hefule
	 *	@date 2017年5月8日 上午12:50:40
	 *	@param start true为启动开始提示，false为启动完成提示
	 *	@param event 刷新事件对象
	 *
	 */
	private void loadSystemStartOut(boolean start,ContextRefreshedEvent event){
		if(start){
			logger.info("\r\n===================== "+systemName+"启动开始  =======================\r\n");
		}else{
			ApplicationContext  ctx = event.getApplicationContext();
			WebContextListener webContextListener = (WebContextListener) ctx.getBean("webContextListener");
			if(ctx.getParent()!=null && webContextListener.systemStartNum==NumConstant.COMMON_NUMBER_ONE){
				logger.info("\r\n===================== "+systemName+"启动完成  =======================\r\n");
				webContextListener.systemStartNum = NumConstant.COMMON_NUMBER_TWO;
			}
		}
	}
	
	/**
	 *	系统停止输出函数
	 *	@author hefule
	 *	@date 2017年5月8日 上午12:50:40
	 *	@param start true为停止开始提示，false为停止完成提示
	 *	@param event 停止事件对象
	 *
	 */
	/*private void loadSystemEndOut(boolean start,ContextClosedEvent event){
		if(start){
			ApplicationContext  ctx = event.getApplicationContext();
			WebContextListener webContextListener = (WebContextListener) ctx.getBean("webContextListener");
			if(webContextListener.systemStartNum == NumConstant.COMMON_NUMBER_TWO){
				logger.info("\r\n===================== "+systemName+"停止开始  =======================\r\n");
				webContextListener.systemStartNum = NumConstant.COMMON_NUMBER_ONE;
			}
		}else{
			logger.info("\r\n===================== "+systemName+"停止完成  =======================\r\n");
		}
	}*/

}
