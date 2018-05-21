/**
 * 
 */
package com.garlane.relation.truefalse.model;

import java.util.Date;

/**
 * @author lixingfa
 * @date 2017年11月29日下午5:39:22
 * 
 */
public class TrueFalseModel {
	private String uuid;//uuid主键
	private String condition;//条件，当条件类型为3时，数据存储方式为：配置条件（英文与号）SQL条件
	private String flag;//url标志，即页面上的请求占位符。
	private String pageUrl;//标签所在的页面，代码里的路径
	private Date createTime;
	private String projectName;//所属项目的名称
	private String tips;
	private String status;//状态，0不启用配置化，直接返回false，1启用配置化，根据条件返回结果
	private String tfName;//标签的简称
	private String parentFlag;//父标签，因为代码嵌套的关系，父开关为false，子开关也会是false
	
	private String actionUrl;//action表的字段，联查用
	private String actionTips;//action表的字段，联查用
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTfName() {
		return tfName;
	}
	public void setTfName(String tfName) {
		this.tfName = tfName;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getParentFlag() {
		return parentFlag;
	}
	public void setParentFlag(String parentFlag) {
		this.parentFlag = parentFlag;
	}
	public String getActionTips() {
		return actionTips;
	}
	public void setActionTips(String actionTips) {
		this.actionTips = actionTips;
	}	
	
}
