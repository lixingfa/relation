package com.garlane.relation.common.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *	公用拦截器
 *	@author hefule
 *	@date 2017年2月20日 上午10:28:25
 *
 */
public class CommonInterceptor extends HandlerInterceptorAdapter implements Filter{
	
	protected Logger logger = Logger.getLogger(getClass());

	/**
	 *	允许通行url关键字
	 *	@author hefule
	 *	@date 2017年2月20日 上午10:31:27
	 *
	 */
	public static String[] allowUrls={
		
	};
	
	/**
	 *	在业务处理器处理请求之前被调用
	 *	如果返回false-->执行所有的拦截器的afterCompletion()-->再退出拦截器链
	 *	如果是返回true-->执行所有拦截器-->Controller-->postHandle()-->afterCompletion()
	 *	@author hefule
	 *	@date 2017年2月20日 上午11:03:30
	 *
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");        
        String userCode = request.getRemoteUser();
        if(StringUtils.isEmpty(userCode)){
        	logger.info("当前用户未登陆");
        }else{
        	logger.info("当前用户为："+userCode);
        }
        logger.info("当前访问方法路径为："+requestUrl);
    	return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView != null && !StringUtils.isEmpty(modelAndView.getViewName())){   
			logger.info("当前请求返回的视图路径为："+modelAndView.getViewName());   
        }else{
        	if(!"application/json;charset=UTF-8".equals(response.getContentType())){
        		logger.info("当前请求返回的是流数据");
        	}else{
        		logger.info("当前请求返回的是json数据");
        	}
        }
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if(ex!=null){
			logger.info("当前请求访问错误,错误访问地址为："+request.getRequestURI()+"\n",ex);
		}else{
			logger.info("当前请求访问成功\n");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		String userCode = ((HttpServletRequest)request).getRemoteUser();
//		if(LoginConstant.IS_ENCRYPT){
//			if(StringUtils.isEmpty(session.getAttribute("haveDecodeUserCode")) && !StringUtils.isEmpty(userCode)){
//				userCode = new String(Base64.decodeBase64(userCode));
//				int length = userCode.length();
//				if (length > 5 && userCode.charAt(length-5) == '@') {
//					userCode = userCode.substring(0,length-5);
//				}
//				session.setAttribute("haveDecodeUserCode", "minstone");
//			}else if(!StringUtils.isEmpty(userCode)){
//				userCode = new String(Base64.decodeBase64(userCode));
//			}
//		}
		/*if(LoginConstant.IS_ENCRYPT && StringUtils.isEmpty(session.getAttribute("userCode")) && !StringUtils.isEmpty(userCode)){//解密
			userCode = new String(Base64.decodeBase64(userCode));
			int length = userCode.length();
			if (length > 5 && userCode.charAt(length-5) == '@') {
				userCode = userCode.substring(0,length-5);
			}
			session.setAttribute("userCode", userCode);
		}else if(!StringUtils.isEmpty(session.getAttribute("userCode"))){
			userCode = (String) session.getAttribute("userCode");
		}else if(LoginConstant.IS_ENCRYPT && !StringUtils.isEmpty(userCode)){
			userCode = new String(Base64.decodeBase64(userCode));
			session.setAttribute("userCode", userCode);
		}*/
		final class CasHttpServletRequestWrapper extends HttpServletRequestWrapper{//匿名内部类
			String userCode;
			CasHttpServletRequestWrapper(HttpServletRequest request,String userCode) {
	            super(request);
	            this.userCode = userCode;
	        }
	        @Override
			public String getRemoteUser() {
	        	return userCode;
	        }
		} 
		chain.doFilter(new CasHttpServletRequestWrapper((HttpServletRequest) request,userCode), response);
	}

	@Override
	public void destroy() {}

}
