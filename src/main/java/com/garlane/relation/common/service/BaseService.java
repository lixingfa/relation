package com.garlane.relation.common.service;

import java.util.List;
import java.util.Map;

import com.garlane.relation.common.model.page.PageDataModel;
import com.garlane.relation.common.model.page.PageModel;
import com.garlane.relation.common.model.tree.WebConfigTreeModel;
import com.garlane.relation.common.utils.exception.SuperServiceException;

/**
 * 一个基础service
 * @author liyhu
 * @date 2017年12月5日上午11:33:49
 * @param <T>
 */
public interface BaseService<T> {
    /**
     * 查询所有
     * getAll:. <br/> 
     * @author zhouwx
     * @date 2017年11月2日下午12:00:00
     * @return ActionModel
     */
     List<T> getAll() throws SuperServiceException;
    

    /**
     * 
     * 查询分页数据
     * @param pageModel
     * @param t
     * @return
     * @throws SuperServiceException
     * @author zhouwx
     * @date 2017年11月3日 下午1:54:13
     */
     PageDataModel<T> loadPageList(PageModel pageModel, T t) throws SuperServiceException;

    /**
     * 
     * 
     * @param actionModel
     * @author zhouwx
     * @date 2017年11月3日 下午4:45:28
     */
     void add(T actionModel) throws SuperServiceException;

    /**
     * 
     * 
     * @param uuid
     * @return
     * @throws SuperServiceException
     * @author zhouwx
     * @date 2017年11月13日 上午5:31:55
     */
     T loadByUuid(String uuid) throws SuperServiceException;

    /**
     * 
     * 
     * @param uuid
     * @throws SuperServiceException
     * @author zhouwx
     * @date 2017年11月13日 上午6:00:34
     */
     void delete(String uuid) throws SuperServiceException;
    
    /**
     * 
     * 
     * @param actionModel
     * @throws SuperServiceException
     * @author zhouwx
     * @date 2017年11月15日 上午11:48:15
     */
     void updateSelective(T t) throws SuperServiceException;
  
     List<T> loadByPoJo(T t);
}
