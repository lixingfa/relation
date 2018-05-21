package com.garlane.relation.common.model.result;


/**
 *	公用结果返回类
 *	@author hefule
 *	@date 2017年2月14日 上午9:30:42
 *
 */
public class ResultModel{

	
	/**结果返回类私有变量*/
	private String success;
	/**结果返回类私有变量*/
	private boolean successful;
	
	/**结果返回类私有消息变量*/
	private String message;
	
	/**结果返回类私有数据变量*/
	private Object data;
	
	public ResultModel(){}
	
	/**
	 *	
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@param success 结果标识
	 *	@param message 结果消息
	 *	@param data 结果数据
	 *
	 */
	public ResultModel(String success,String message,Object data){
		this.success = success;
		this.message = message;
		this.data = data;
		if("false".equalsIgnoreCase(success) || "true".equalsIgnoreCase(success)){
			this.successful = Boolean.valueOf(success.toLowerCase());
		}
	}
	
	/**
	 *	
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@param success 结果标识(true或者false)
	 *	@param message 结果消息
	 *	@param data 结果数据
	 *
	 */
	public ResultModel(boolean success,String message,Object data){
		this(success+"",message,data);
	}
	
	/**
	 *	返回是成功的结果
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@param success 结果标识(自定义)
	 *	@param message 结果消息
	 *
	 */
	public static ResultModel success(String success,String message){
		return new ResultModel(success,message,null);
	}
	
	/**
	 *	返回是true的结果
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@param message 结果消息
	 *	@param data 结果数据
	 *	@return 返回中包含success为"true"
	 *
	 */
	public static ResultModel success(String message,Object data){
		return new ResultModel(true,message,data);
	}
	/**
	 *	返回是true的结果
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@param message 结果消息
	 *	@return 返回包含success为"true"
	 *
	 */
	public static ResultModel success(String message){
		return success(message,(Object)null);
	}
	
	/**
	 *	返回是true的结果
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@return 返回包含success为"true"
	 *
	 */
	public static ResultModel success(){
		return success((String)null);
	}
	
	
	/**
	 *	返回错误的结果
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@param success 结果标识(自定义)
	 *	@param message 结果消息
	 *
	 */
	public static ResultModel error(String error,String message){
		return new ResultModel(error,message,null);
	}
	
	/**
	 *	返回是false的结果
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@param message 结果消息
	 *	@param data 结果数据
	 *	@return 返回中包含success为"true"
	 *
	 */
	public static ResultModel error(String message,Object data){
		return new ResultModel(false,message,data);
	}
	/**
	 *	返回是false的结果
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@param message 结果消息
	 *	@return 返回包含success为"true"
	 *
	 */
	public static ResultModel error(String message){
		return error(message,(Object)null);
	}
	
	/**
	 *	返回是false的结果
	 *	@author hefule
	 *	@date 2017年2月14日 上午9:35:34
	 *	@return 返回包含success为"false"
	 *
	 */
	public static ResultModel error(){
		return error((String)null);
	}
	
	

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	
	
	
}
