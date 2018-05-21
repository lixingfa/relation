package com.garlane.relation.common.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface BaseDao<T> {

    /**
     * 根据pojo动态查询
     * 
     * @author liyhu
     * @date 2017年1月18日 下午3:10:56
     * @param actionModel
     * @return List<T>
     */
    public List<T> loadByPoJo(T t);
    
    
    
    
    /**
     * 根据uuid进行查询
     * 
     * @author liyhu
     * @date 2017年1月18日 下午4:18:09
     * @param userCode
     *            用户名
     * @return
     */
    T loadByUuid(String uuid);

    
    /**
     * 根据uuid有选择地更新
     * 
     * @author liyhu
     * @date 2017年1月19日 下午4:40:16
     * @param actionModel
     */
    void updateSelective(T t);


    /**
     * 新增用户
     * 
     * @author 卢健威
     * @date 2017年1月20日 上午9:19:49
     * @param actionModel
     */
    void add(T t);
    
    /**
     * 根据set<uuid>进行删除
     * 
     * @author liyhu
     * @date 2017年1月20日 上午11:49:21
     * @param userCode
     */
    void deleteByUuids(@Param("uuids")Set<String> uuids);

}
