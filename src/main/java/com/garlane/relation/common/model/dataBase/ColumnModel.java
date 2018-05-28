package com.garlane.relation.common.model.dataBase;
import com.garlane.relation.common.constant.DBConstant;

/**
 * 数据列模型
 * @author Lixingfa
 *
 */
public class ColumnModel {
	/**名称*/
	private String name;
	/**标题*/
	private String title;
	/**页面数据类型*/
	private DBConstant.dataType dataType;
	/**示例数据最大长度*/
	private Long dataLength;
	/**不为空*/
	private boolean notNull = false;
	/**小数位数*/
	private int decimals = 0;
	/**默认值*/
	private String Default = null;

	public ColumnModel(String name,String title,DBConstant.dataType dataType,Long dataLength){
		this.name = name;
		this.title = title;		
		this.dataType = dataType;
		this.dataLength = dataLength;
	}
	
	public ColumnModel(String name,String title){
		if (name == null) {
			name = "";
		}
		if (title == null) {
			title = "";
		}
		this.name = name;
		this.title = title;		
		this.dataType = dataType.String;
		this.dataLength = 30l;
	}
	
	public static void inteColumnModel(ColumnModel cModel,ColumnModel cModel2){
		if (cModel.getTitle() != null && cModel2.getTitle() != null && cModel.getTitle().length() <= cModel2.getTitle().length()) {
			cModel.setTitle(cModel2.getTitle());
		}
		if (cModel.getDataType().compareTo(cModel2.getDataType()) < 0) {//升级为最高的
			cModel.setDataType(cModel2.getDataType());
		}
		if (cModel.getDecimals() < cModel2.getDecimals()) {
			cModel.setDecimals(cModel2.getDecimals());
		}
		if (cModel.getDataLength() < cModel2.getDataLength()) {
			cModel.setDataLength(cModel2.getDataLength());
		}
		cModel.setNotNull(cModel.isNotNull() && cModel2.isNotNull());//是否为空
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DBConstant.dataType getDataType() {
		return dataType;
	}

	public void setDataType(DBConstant.dataType dataType) {
		this.dataType = dataType;
	}

	public Long getDataLength() {
		return dataLength;
	}

	public void setDataLength(Long dataLength) {
		this.dataLength = dataLength;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public int getDecimals() {
		return decimals;
	}

	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	public String getDefault() {
		return Default;
	}

	public void setDefault(String default1) {
		Default = default1;
	}
	
}
