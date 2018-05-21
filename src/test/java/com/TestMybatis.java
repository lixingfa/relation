package com;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.garlane.relation.action.dao.ActionDao;
import com.garlane.relation.action.model.ActionModel;
import com.minstone.webconfig.common.utils.exception.SuperServiceException;

/**
 * TEST,service/dao测试用例
 * @author zhouwx
 * @date 2017年4月19日下午3:42:42
 */
public class TestMybatis {
//	private static String DOC_SEND_INFO = "DOC_SEND_INFODOC_SYSID";
//	private static String SERVER_NAME = "SYS_PUBLIC";

    private ApplicationContext applicationContext;  
	private final Logger logger = Logger.getLogger(TestMybatis.class);
  
    @Before  
    public void setUp() throws Exception {  
    	logger.info("before");
        applicationContext = new ClassPathXmlApplicationContext(  
                "classpath:spring/spring-context.xml"); 
    }  
  
    @Test  
    public void testYouWantToTest() throws SuperServiceException {  
    	logger.info("in test");
    	ActionDao commonDao = (ActionDao) applicationContext  
                .getBean("actionDao"); 
    	String buttonUuid = "2";
    	List<ActionModel> list = new ArrayList<ActionModel>();
    	List<ActionModel> list02 = new ArrayList<ActionModel>();
		try {
			list = commonDao.getActionModelByButtonUuid(buttonUuid);
//			list02 = commonDao.listActionModelInfo();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
    	logger.info("list.size(): "+list.size());
    	logger.info("list02.size(): "+list02.size());
    	for(ActionModel model : list02){
    		logger.info(model.getButtonUuid());
    	}
    }  
 
    @After
    public void shutDown() throws Exception{
    	logger.info("after");
    }
}
