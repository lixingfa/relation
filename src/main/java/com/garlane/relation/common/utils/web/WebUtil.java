package com.garlane.relation.common.utils.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtil {

	public static final String getRemoteUser() {
		HttpServletRequest hsr = getRequest();
		if (hsr != null) {
			return hsr.getRemoteUser();
		}else {
			return null;
		}
	}
	
	public static HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
}
