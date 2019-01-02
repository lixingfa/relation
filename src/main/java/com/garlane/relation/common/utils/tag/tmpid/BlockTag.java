package com.garlane.relation.common.utils.tag.tmpid;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *	jsp注解继承类
 *	@author hefule
 *	@date 2017年1月13日 上午12:44:06
 *
 */
public class BlockTag extends BodyTagSupport{
	
	private final Logger logger  = Logger.getLogger(getClass());
	
	private static final long serialVersionUID = 1L;
	/**
     * 占位模块名称
     */
	private String name;
	
	@Override
    public int doStartTag() throws JspException{
        return super.doStartTag();                
    }
	
	 @Override
	    public int doEndTag() throws JspException {
	        ServletRequest request = pageContext.getRequest();
	        //block标签中的默认值
	        String defaultContent = (getBodyContent() == null)?"":getBodyContent().getString();        
	        String bodyContent = (String) request.getAttribute(OverwriteTag.PREFIX+ name);
	        //如果页面没有重写该模块则显示默认内容
	        bodyContent = StringUtils.isEmpty(bodyContent)?defaultContent:bodyContent;
	        try {
	            pageContext.getOut().write(bodyContent);
	        } catch (IOException e) {
	        	logger.error(e.getMessage(), e);
	        }        
	        return super.doEndTag();
	    } 
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
}
