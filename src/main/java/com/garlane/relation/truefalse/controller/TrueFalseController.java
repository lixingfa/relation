package com.garlane.relation.truefalse.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.garlane.relation.common.controller.BaseController;
import com.garlane.relation.common.model.page.PageDataModel;
import com.garlane.relation.common.model.page.PageModel;
import com.garlane.relation.common.model.result.ResultModel;
import com.garlane.relation.common.model.tree.WebConfigTreeModel;
import com.garlane.relation.truefalse.model.TrueFalseModel;
import com.garlane.relation.truefalse.model.WebConfigConditionModel;
import com.garlane.relation.truefalse.service.TrueFalseService;
import com.garlane.relation.truefalse.service.WebConfigConditionService;

/**
 * 对或错判断
 * @author lixingfa
 * @date 2017年11月29日下午5:55:27
 *
 */
@Controller
@RequestMapping("truefalse")
public class TrueFalseController extends BaseController {

	@Autowired
	private TrueFalseService trueFalseService;
	
	@Autowired
	private WebConfigConditionService conditionService;

	
	/**
	 * 
	 * updateSaveTrueFalse:(新增/编辑逻辑). <br/> 
	 * @author liyhu
	 * @date 2017年12月5日下午3:41:38
	 * @param trueFalseModel
	 * @return ResultModel
	 */
	@RequestMapping("updateSaveTrueFalse")
    @ResponseBody
    public ResultModel updateSaveTrueFalse(TrueFalseModel trueFalseModel) {
        try {
            logger.info("编辑平台请求信息");
            if (StringUtils.isNotBlank(trueFalseModel.getUuid())) {
                trueFalseService.updateSelective(trueFalseModel);         
            }else {
                if(trueFalseModel.getCreateTime()==null){
                    trueFalseModel.setCreateTime(new Date());
                }
                trueFalseService.add(trueFalseModel);
            }
            return ResultModel.success("编辑逻辑请求数据成功");
        } catch (Exception e) {
            logger.error("编辑逻辑请求数据失败", e);
            return ResultModel.error("编辑逻辑请求数据失败"+e.getMessage());
        }
    }
	
	/**
	 * toListTrueFalse:(到truefalse配置页)
	 * @author lixingfa
	 * @date 2017年11月29日下午6:24:25
	 * @param request
	 * @return
	 */
	@RequestMapping("toListTrueFalse")
	public ModelAndView toListTrueFalse(HttpServletRequest request) {
		try {
			logger.info("跳转界面");
			ModelAndView modelAndView = new ModelAndView("truefalse/truefalseList");
			return modelAndView;
		} catch (Exception e) {
			logger.error("跳转失败", e);
			return errorModelAndView();
		}
	}
	
	/**
     * 
     * 
     * @param indexTreeModel
     * @param request
     * @return
     * @author zhouwx
     * @date 2017年11月7日 下午6:24:20
     */
    @RequestMapping("loadTrueFalseTree")
    @ResponseBody
    public List<WebConfigTreeModel> loadTrueFalseTree(String actionProjName, HttpServletRequest request){
        List<WebConfigTreeModel> temp = null;
        try{
//            temp = trueFalseService.loadTree(actionProjName);
        }catch(Exception e){
            logger.error("获取平台请求树数据异常",e);
        }
        return temp;
    }
    
    /**
     * 
     * toEditPage:(跳转到编辑页). <br/> 
     * @author liyhu
     * @date 2017年12月6日上午8:56:12
     * @param uuid
     * @return ModelAndView
     */
    @RequestMapping("toEditPage")
    public  ModelAndView toEditPage(String uuid) {
        ModelAndView model = new ModelAndView("truefalse/truefalseEdit");
        TrueFalseModel trueFalseModel = trueFalseService.loadByUuid(uuid); 
        model.addObject("trueFalseModel",trueFalseModel);
        return model;
    }
	
	/**
	 * 
	 * loadOneTrueFalse:(跳转到添加页). <br/> 
	 * @author liyhu
	 * @date 2017年12月6日上午8:58:44
	 * @return
	 */
    @RequestMapping("toAddPage")
    public ModelAndView toAddPage() {
        try {
            logger.info("读取单个逻辑请求数据---跳转到逻辑请求编辑页面");
            ModelAndView model = new ModelAndView("truefalse/truefalseNew");
            return model;
        } catch (Exception e) {
            logger.error("获取单个逻辑请求数据失败", e);
            return errorModelAndView();
        }
    }
		
	/**
	 * loadListTrueFalse:(加载truefalse配置)
	 * @author lixingfa
	 * @date 2017年11月29日下午6:26:58
	 * @param pageModel
	 * @param actionModel
	 * @param request
	 * @return
	 */
	@RequestMapping("loadListTrueFalse")
	@ResponseBody
	public PageDataModel<TrueFalseModel> loadListTrueFalse(PageModel pageModel,TrueFalseModel trueFalseModel,HttpServletRequest request) {
		PageDataModel<TrueFalseModel> pageDataModel = null;
		try {
			logger.info("读取逻辑请求管理分页数据");
			pageDataModel = trueFalseService.loadPageList(pageModel, trueFalseModel);
		} catch (Exception e) {
			logger.error("获取分页逻辑请求数据失败", e);
		}
		return pageDataModel;
	}
	
	/**
	 * 
	 * deleteTrueFalse:(删除逻辑请求). <br/> 
	 * @author liyhu
	 * @date 2017年12月5日下午2:59:40
	 * @param actionModel
	 * @return ResultModel
	 */
    @RequestMapping("deleteTrueFalse")
    @ResponseBody
    public ResultModel deleteTrueFalse(TrueFalseModel trueFalseModel) {
        try {
            logger.info("执行删除平台请求操作");
            trueFalseService.delete(trueFalseModel.getUuid());
            return ResultModel.success("删除逻辑请求成功!");
        } catch (Exception e) {
            logger.error("删除平台请求失败", e);
            return ResultModel.error("删除逻辑请求失败,"+e.getMessage());
        }
    }
    
    /**
     * toShowWebConfigPageList:(跳转置条件逻辑页). <br/> 
     * @author pangyc
     * @date 2018年1月11日上午9:37:41
     * @param request HttpServletRequest对象
     * @return  展示页面
     * @throws Exception
     */
    @RequestMapping("/toShowWebConfigPageList")
    public String toShowWebConfigPageList(HttpServletRequest request) throws Exception {
		 logger.info("跳转到逻辑配置列表页面");
	     return "truefalse/webConfigList";
    }
    
    /**
     * loadWebConfigConditions:(加载所有条件逻辑). <br/> 
     * @author pangyc
     * @date 2018年1月11日上午9:17:41
     * @param pageModel 分页对象
     * @param request   HttpServletRequest对象
     * @return 分页条件逻辑集合
     * @throws Exception
     */
    @RequestMapping("/loadWebConfigConditions")
    @ResponseBody
    public PageDataModel<WebConfigConditionModel> loadWebConfigConditions(PageModel pageModel,WebConfigConditionModel conditionModel, HttpServletRequest request) {
    	try {
    		PageDataModel<WebConfigConditionModel> resultDatas = conditionService.loadPageList(pageModel, conditionModel);
    		return resultDatas;
    	} catch (Exception e) {
    		logger.error("loadWebConfigConditions:(加载所有条件逻辑)出错了！", e);
    	}
    	return new PageDataModel<WebConfigConditionModel>();
    }
    
    /**
     * toAddWebConfigPage:(跳转到新增逻辑配置页). <br/> 
     * @author pangyc
     * @date 2018年1月11日上午9:58:08
     * @param request
     * @return
     */
    @RequestMapping("/toAddWebConfigPage")
    public ModelAndView toAddWebConfigPage(HttpServletRequest request) {
		   try {
	           logger.info("跳转到逻辑配置请求编辑页面");
	           ModelAndView model = new ModelAndView("truefalse/webConfigNew");
	           return model;
	       } catch (Exception e) {
	           logger.error("toAddWebConfigPage:(跳转到新增逻辑配置页). ", e);
	           return errorModelAndView();
	       }
    }
    
    /**
     * checkFlagForUse:(校验配置条件是否已配置). <br/> 
     * @author pangyc
     * @date 2018年1月11日上午10:27:40
     * @param request
     * @return
     */
    @RequestMapping("/checkFlagForUse")
    @ResponseBody
    public ResultModel checkFlagForUse(HttpServletRequest request) {
    	ResultModel result = new ResultModel();
    	String flag = request.getParameter("flag");
    	try {
    		if (StringUtils.isNotBlank(flag)) {
    			WebConfigConditionModel config = conditionService.loadByUuid(flag);
    			if (config == null) {
    				result.setSuccessful(true);
    			} else {
    				result.setSuccessful(false);
    			}
    		} else {
    			result.setSuccessful(false);
    		}
    	} catch (Exception e) {
    		logger.error("checkFlagForUse:(校验配置条件是否已配置)", e);
    	}
    	return result;
    }
    
    /**
     * updateSaveConfig:(新增或编辑配置条件). <br/> 
     * @author pangyc
     * @date 2018年1月11日上午10:56:37
     * @param trueFalseModel
     * @return
     */
	@RequestMapping("/saveConfigCondition")
    @ResponseBody
    public ResultModel saveConfigCondition(WebConfigConditionModel configCondition) {
        try {
            conditionService.add(configCondition);
            return ResultModel.success("添加逻辑请求数据成功");
        } catch (Exception e) {
            logger.error("编辑逻辑请求数据失败", e);
            return ResultModel.error("添加逻辑请求数据失败"+e.getMessage());
        }
    }
	
	/**
	 * deleteConfigCondition:(删除配置逻辑). <br/> 
	 * @author pangyc
	 * @date 2018年1月11日上午11:19:44
	 * @param conditionModel 配置实体WebConfigConditionModel
	 * @return 
	 */
	@RequestMapping("/deleteConfigCondition")
	@ResponseBody
	public ResultModel deleteConfigCondition(WebConfigConditionModel conditionModel) {
		 try {
            logger.info("执行删除平台请求操作");
            conditionService.delete(conditionModel.getFlag());
            return ResultModel.success("删除逻辑请求成功!");
	      } catch (Exception e) {
            logger.error("删除平台请求失败", e);
            return ResultModel.error("删除逻辑请求失败,"+e.getMessage());
	      }
	}
	
	/**
	 * toEditWebConfigPage:(调整到配置编辑页). <br/> 
	 * @author pangyc
	 * @date 2018年1月11日上午11:28:28
	 * @param request
	 * @return
	 */
	@RequestMapping("/toEditWebConfigPage")
    public ModelAndView toEditWebConfigPage(HttpServletRequest request) {
		 ModelAndView model = new ModelAndView();
		 String flag = request.getParameter("flag");
		 try {
			 logger.info("跳转到逻辑配置请求编辑页面");
			 WebConfigConditionModel conditionModel = conditionService.loadByUuid(flag);
			 model.addObject("webConfigCondition", conditionModel);
			 model.setViewName("truefalse/webConfigEdit");
		 } catch (Exception e) {
			 logger.error("toEditWebConfigPage:(调整到配置编辑页)出错了！", e);
		 }
	     return model;
    } 
    
	/**
	 * updateConfigCondition:(编辑配置条件). <br/> 
	 * @author pangyc
	 * @date 2018年1月11日上午11:56:28
	 * @param conditionModel  配置条件实体WebConfigConditionModel
	 * @return
	 */
	@RequestMapping("/updateConfigCondition")
	@ResponseBody
	public ResultModel updateConfigCondition(WebConfigConditionModel conditionModel) {
		try {
            conditionService.updateSelective(conditionModel);
            return ResultModel.success("编辑逻辑请求数据成功");
        } catch (Exception e) {
            logger.error("编辑逻辑请求数据失败", e);
            return ResultModel.error("编辑逻辑请求数据失败"+e.getMessage());
        }
	}

	@RequestMapping("/toViewTrueFalsePage")
    public ModelAndView toViewTrueFalsePage(HttpServletRequest request,TrueFalseModel t) {
		 ModelAndView model = new ModelAndView();
		 try {
			 logger.info("跳转到界面配置页面");
			 List<TrueFalseModel> trueFalseList = trueFalseService.loadByPoJo(t);
			 model.addObject("trueFalseList", trueFalseList);
			 model.addObject("t", t);
			 model.setViewName("truefalse/viewTrueFalse");
		 } catch (Exception e) {
			 logger.error("toViewTrueFalsePage:跳转到界面配置页面！", e);
		 }
	     return model;
    } 
	
	/************************************************************/
	@InitBinder("trueFalseModel")
    public void bindFormPanelModel(WebDataBinder webDataBinder) {
        webDataBinder.setFieldDefaultPrefix("t.");
    }
}
