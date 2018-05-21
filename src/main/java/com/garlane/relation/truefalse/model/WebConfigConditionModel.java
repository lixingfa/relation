package com.garlane.relation.truefalse.model;

import java.io.Serializable;

/**
 * 对应表web_config_condition 条件对应逻辑表
 * @author pangyc
 * @date 2018年1月11日上午9:15:36
 */
public class WebConfigConditionModel implements Serializable {

	/**
	 * @author pangyc
	 * @date 2018年1月11日上午9:15:32
	 */
	private static final long serialVersionUID = 1L;
	
	// url标志，其他表condition里的值
	private String flag;
	
	// 条件类型，0配置条件，1SQL条件
	private String conditionType;
	
	// 条件，如这里获取办件状态，标签为${approveStatus}，条件类型为1，条件内容为select approve_status from
	private String condition;
	
	// 配置说明
	private String tips;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
	


}
