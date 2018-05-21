package com.garlane.relation.common.service.impl;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garlane.relation.common.constant.Constants;
import com.garlane.relation.common.dao.BaseDao;
import com.garlane.relation.common.model.page.PageDataModel;
import com.garlane.relation.common.model.page.PageModel;
import com.garlane.relation.common.model.tree.CallBack;
import com.garlane.relation.common.model.tree.Node;
import com.garlane.relation.common.model.tree.WebConfigTreeModel;
import com.garlane.relation.common.service.BaseService;
import com.garlane.relation.common.utils.change.CollectionUtil;
import com.garlane.relation.common.utils.exception.SuperServiceException;
import com.garlane.relation.common.utils.file.FileUtils;


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
    public List<WebConfigTreeModel> loadTree(String projName) throws SuperServiceException {
        try{
        	if (projName.contains("--")) {
				return null;
			}
            //TODO 这里的路径不严谨，待完善basePath = “/D:/tool/apache-tomcat-7.0.75/webapps/WebConfig/WEB-INF/classes/”
            //TODO 未考虑外部链接url；未验证系统名是否存在
            String basePath = this.getClass().getResource("/").getPath();
            basePath = basePath.substring(File.separator.length(), basePath.length()-"WebConfig/WEB-INF/classes/".length());
            final String BASE_PATH = URLDecoder.decode(basePath, Constants.UTF_8)
                    + URLDecoder.decode(projName, Constants.UTF_8) + "/WEB-INF/jsp";  //"D:/tool/apache-tomcat-7.0.75/webapps/TargetProjName/WEB-INF/jsp";

            final String[] SUFFIXS = {".jsp",".jspf"};
            Node<WebConfigTreeModel> node = FileUtils.file2PageTree(BASE_PATH, new CallBack<WebConfigTreeModel>() {
                @Override
                //处理每个节点下ActionTreeModel属性值
                public void execute(WebConfigTreeModel t) {
                    String nodeFullPath = this.node.getFile().getAbsolutePath();
                    String relativePath = nodeFullPath.substring(BASE_PATH.length());
                    //去掉文件后缀SUFFIXS
                    for(String suffix : SUFFIXS){
                        if(relativePath.endsWith(suffix)){
                            relativePath = relativePath.substring(0, relativePath.length()-suffix.length());
                        }
                    }
                    t.setRelativePath(relativePath);
                    t.setAbsolutePath(nodeFullPath);
                }
                @Override
                public boolean abandon() {
                    //放弃非SUFFIXS的后缀文件
                    String basolutePath = this.node.getFile().getAbsolutePath();
                    boolean type = false;
                    for(String suffix : SUFFIXS){
                        type = type || basolutePath.endsWith(suffix);
                    }
                    boolean file = this.node.getFile().isFile();
                    return file && !type;
                }
            }, WebConfigTreeModel.class);
            List<WebConfigTreeModel> result = (List<WebConfigTreeModel>) FileUtils.searchChild(node.getTreeModels(), null, WebConfigTreeModel.class);
            return result;
        }catch(Exception e){
            throw new SuperServiceException(e);
        }
    }

    @Override
    public List<Map<String, String>> loadComboBoxUrlFlag(String absolutePath,String suffix) {
        try{
//          String absolutePath = "D:/tool/apache-tomcat-7.0.75/webapps/ApprControl/WEB-INF/jsp/control/finish/person/apprHandleInfoToDoFinish.jsp";    //可以直接使用路径，但要求下述完成
            Set<String> set = new HashSet<String>();
            List<Map<String, String>> urlFlags = new ArrayList<Map<String, String>>();
            Map<String, String> map = null;
           
            //读取文件内容，并验证字符串
            String fileContent = FileUtils.readFileByChars(absolutePath);
            
            // 正则表达式规则
           // String regEx = "\\$\\{[ \\t]*[\\w]+_URL[\\t]*\\}";     //${ root_URL }
            String regEx = "\\$\\{[ \\t]*[\\w]+"+suffix+"[ \\t]*\\}";         //${ root }
            // 编译正则表达式
            Pattern pattern = Pattern.compile(regEx);
            // 忽略大小写的写法
            //Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(fileContent);
            while (matcher.find()) {
                set.add(matcher.group());
            }
            Iterator<String> iter = set.iterator();
            while(iter.hasNext()){
                map = new HashMap<String, String>();
                map.put("text", iter.next());
                urlFlags.add(map);
            }
            return urlFlags;
        }catch(Exception e){
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
