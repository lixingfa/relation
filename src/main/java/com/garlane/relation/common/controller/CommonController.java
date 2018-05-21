package com.garlane.relation.common.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.garlane.relation.common.model.tree.WebConfigTreeModel;
import com.garlane.relation.common.service.BaseService;
import com.garlane.relation.common.utils.change.URLCodeUtil;
import com.garlane.relation.common.utils.file.FileUtils;

/**
 * 通用链接，共用一个controller
 * @author liyhu
 * @date 2017年12月5日下午5:20:26
 */
@Controller
@RequestMapping("common")
public class CommonController extends BaseController {
    
    @Resource(name="baseService")
    private BaseService<?> baseService;
    
    
    /**
     * 
     * loadProjects:(加载项目信息). <br/> 
     * @author liyhu
     * @date 2017年12月8日下午2:09:57
     * @return
     */
    @RequestMapping("loadProjects")
    @ResponseBody
    public  List<Map<String, String>> loadProjects() {
        try {
            return FileUtils.loadProjects();
        } catch (Exception e) {
            logger.error("加载页面上的标记出错",e);
        }
        return null;
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
    @RequestMapping("loadJspTree")
    @ResponseBody
    public List<WebConfigTreeModel> loadActionTree(String projName){
        List<WebConfigTreeModel> temp = null;
        try{
            temp = baseService.loadTree(projName);
        }catch(Exception e){
            logger.error("获取平台请求树数据异常",e);
        }
        return temp;
    }
    
    /**
     * 
     * 
     * @param absolutePath
     * @return
     * @author zhouwx
     * @date 2017年11月7日 下午6:24:12
     */
    @RequestMapping("loadComboBoxUrlFlag")
    @ResponseBody
    public List<Map<String, String>> loadComboBoxUrlFlag(String absolutePath,String suffix){
        List<Map<String, String>> temp = null;
        try{
            temp = baseService.loadComboBoxUrlFlag(URLCodeUtil.decode(absolutePath),suffix);
        }catch(Exception e){
            logger.error("获取菜单树数据异常",e);
        }
        return temp;
    }

}
