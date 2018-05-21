package com.garlane.relation.common.model.page;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.garlane.relation.common.utils.change.LineHumpUtil;

/**
 *	分页数据对象
 *	@author hefule
 *	@date 2017年1月12日 下午1:19:13
 *
 */
public final class PageModel implements Serializable{

	private static final long serialVersionUID = 1L;
	/**一页的条数*/
	private int rows;
	/**当前页*/
	private int page;
	/**datagrid 排序字段*/
	private String sort;
	/**datagrid排序方式(asc,desc)*/
	private String order;
	/**datagrid最终排序 order+sort*/
	private String orderBy;
	
	private Page pages;
	
	/**
	 *	初始化分页对象
	 *	@author hefule
	 *	@date 2017年1月13日 下午6:00:58
	 *	@param haveOrder 是否有排序 默认为true有排序
	 *
	 */
	public void initPage(boolean haveOrder){
		orderSort();
		if(!haveOrder || StringUtils.isEmpty(orderBy))
			this.pages = PageHelper.startPage(page,rows);
		else 
			this.pages = PageHelper.startPage(page,rows,orderBy);
	}
	
	/**
	 *	初始化分页对象
	 *	@author hefule
	 *	@date 2017年1月13日 下午6:00:58
	 *	@see 默认有排序
	 *
	 */
	public void initPage(){
		initPage(true);
	}
	
	/**
	 *	初始化分页对象
	 *	@author hefule
	 *	@date 2017年1月13日 下午6:00:58
	 *	@param sort 排序字段 驼峰法命名
	 *	@param order 排序方式 asc,desc
	 *	
	 */
	public void initPage(String sort ,String order){
		this.sort = sort;
		this.order = order;
		initPage();
	}
	/**
	 *	获取分页结果
	 *	@author hefule
	 *	@date 2017年1月13日 下午6:00:40
	 *
	 */
	public PageDataModel loadData(List data){
		return new PageDataModel(pages.getTotal()+"", data);
	}
	
	/**
	 *	获取排序方式
	 *	@author hefule
	 *	@date 2017年1月13日 下午5:36:16
	 *
	 */
	public void orderSort(){
		if(!StringUtils.isEmpty(order) && !StringUtils.isEmpty(sort)){
			orderBy = LineHumpUtil.humpToLine(sort)+" "+order;
		}else if(!StringUtils.isEmpty(sort)){
			orderBy = LineHumpUtil.humpToLine(sort);
		}
	}

	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
}
