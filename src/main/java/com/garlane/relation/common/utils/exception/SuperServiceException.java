package com.garlane.relation.common.utils.exception;


/**
 *	service层异常处理类
 *	@author hefule
 *	@date 2017年1月17日 上午9:21:22
 *
 */
public class SuperServiceException extends ApprConfigException{

	private static final long serialVersionUID = 1L;
	
	public SuperServiceException(){
		logger.error("这是service层的异常，异常信息如下：");	
	}  
	
	public SuperServiceException(String message) {
        super(message);
        logger.error("这是service层的异常，异常信息如下：");
    }  
	
	public SuperServiceException(Throwable t) {
        super(t);
        logger.error("这是service层的异常，异常信息如下：");
    }
	
	public SuperServiceException(String message,Throwable t) {
        super(message,t);
        logger.error("这是service层的异常，异常信息如下：");
    }

}
