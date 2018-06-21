package com.garlane.relation.common.socket;

import java.util.Date;
/**
 *@function����Ϣ��
 *@date��2016-9-27 ����09:49:55
 *@author:Administrator.
 *@notice��
 */
public class Message {

	//������
	public String from;
	//���������
	public String fromName;
	//������
	public String to;
	//���͵��ı�
	public String text;
	//��������
	public Date date;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
