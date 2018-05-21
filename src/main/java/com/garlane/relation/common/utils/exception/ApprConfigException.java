package com.garlane.relation.common.utils.exception;

import org.apache.log4j.Logger;

/**
 *	系统异常处理基类类
 *	@author hefule
 *	@date 2017年1月17日 上午9:24:44
 *
 */
public class ApprConfigException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	public ApprConfigException(){
	}  
	
	public ApprConfigException(String message) {
        super(message);
    }  
	
	public ApprConfigException(Throwable t) {
        super(t);
    }
	
	public ApprConfigException(String message,Throwable t) {
        super(message,t);
    }
	
	public ApprConfigException(String message,Exception e){
		super(message,e);
		logger.error("系统类异常，异常如下：");
	}  

}

