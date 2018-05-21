package com.garlane.relation.common.model.page;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *	分页数据返回对象
 *	@author hefule
 *	@date 2017年1月12日 下午1:20:16
 *
 */
public class PageDataModel<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**总数*/
	private String total;
	/**数据*/
    private List<T> rows = new ArrayList<T>();
    
    public PageDataModel(){}
    
    /**
	 *	分页数据返回对象构造函数
	 *	@author hefule
	 *	@date 2017年1月12日 下午1:24:32
	 *	@param total 总数
	 *	@param rows 数据
	 */
    public PageDataModel(String total,List<T> rows){
    	this.total = total;
    	this.rows = rows;
    }
    
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getRows() {
		return rows;
	}
     
}
