package com.garlane.relation.common.service.impl;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.garlane.relation.common.dao.BaseDao;
import com.garlane.relation.common.model.page.PageDataModel;
import com.garlane.relation.common.model.page.PageModel;
import com.garlane.relation.common.service.BaseService;
import com.garlane.relation.common.utils.change.CollectionUtil;
import com.garlane.relation.common.utils.exception.SuperServiceException;


@Service("baseService")
public class BaseServiceImpl<T> implements BaseService<T>{

    @Autowired
    private BaseDao<T> baseDao;
    
    @Override
    public List<T> getAll() throws SuperServiceException {
        return baseDao.loadByPoJo(null);
    }

    @Override
    public PageDataModel<T> loadPageList(PageModel pageModel, T t) throws SuperServiceException {
        try {
            pageModel.initPage();
            List<T> actionModels = this.baseDao.loadByPoJo(t);
            return pageModel.loadData(actionModels);
        } catch (Exception e) {
            throw new SuperServiceException(e);
        }
    }

    @Override
    public void add(T t) throws SuperServiceException {
        try {
            this.baseDao.add(t);
        } catch (Exception e) {
            throw new SuperServiceException(e);
        } 
    }

    @Override
    public T loadByUuid(String uuid) throws SuperServiceException {
        return this.baseDao.loadByUuid(uuid);
    }
 
    @Override
    public void delete(String uuid) throws SuperServiceException {
       this.baseDao.deleteByUuids(CollectionUtil.asSet(Arrays.asList(uuid)));
        
    }

    @Override
    public void updateSelective(T t) throws SuperServiceException {
        this.baseDao.updateSelective(t);
    }

	/* (non-Javadoc)
	 * @see com.garlane.relation.common.service.BaseService#loadByPoJo(java.lang.Object)
	 */
	@Override
	public List<T> loadByPoJo(T t) {
		return baseDao.loadByPoJo(t);
	}

}
