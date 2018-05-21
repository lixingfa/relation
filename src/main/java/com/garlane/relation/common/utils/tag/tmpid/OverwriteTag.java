package com.garlane.relation.common.utils.tag.tmpid;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.util.StringUtils;

/**
 *	自定义标签，用于在jsp模板中重写指定的占位内容
 *	@author hefule
 *	@date 2017年1月13日 上午12:46:07
 *
 */
public class OverwriteTag extends BodyTagSupport{
	
	private static final long serialVersionUID = 1L;
	
	//模块名的前缀
    public static final String PREFIX = "JspTemplateBlockName_";
    
    //模块名
    private String name;
    
    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }
    
    @Override
    public int doEndTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        //标签内容
        BodyContent bodyContent = getBodyContent();
        request.setAttribute(PREFIX+name,  StringUtils.trimWhitespace(bodyContent!=null?bodyContent.getString():""));        
        return super.doEndTag();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    

}
