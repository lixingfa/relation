package com.garlane.relation.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.garlane.relation.common.model.tree.CallBack;
import com.garlane.relation.common.model.tree.Node;
import com.garlane.relation.common.model.tree.TreeModel;
import com.garlane.relation.common.model.tree.WebConfigTreeModel;
import com.garlane.relation.common.utils.change.StringUtil;

public class FileUtils {

    private static final Logger logger=Logger.getLogger(FileUtils.class);
    private static final List<Map<String,String>> PROJECT_LIST=new ArrayList<Map<String,String>>();
    
    
    
    static{
        try {
            Map<String,String> map;
            InputStream projectIn = Thread.currentThread().getContextClassLoader().getResourceAsStream("project.properties");
            Properties props = new Properties();
            props.load(projectIn);
            for(Map.Entry<Object, Object> entry: props.entrySet()){
                map=new HashMap<String, String>();
                map.put("text", entry.getValue().toString());
                PROJECT_LIST.add(map);
            }
        } catch (IOException e) {
            logger.error("加载项目信息出错",e);
        }
        
        
    }
    
    /**
     * 
     * loadProjects:(加载项目信息). <br/> 
     * @author liyhu
     * @date 2017年12月8日下午2:07:48
     * @return List<Map<String,String>>
     */
    public static List<Map<String,String>> loadProjects(){
        return PROJECT_LIST;
    }
	/**
	 * 把目录下的文件整理为单向树结构
	 * 其内部由双向链表组成，存储子节点用的是线性数组
	 * 
	 * @param basePath
	 * @return
	 * @author zhouwx
	 * @param 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @date 2017年11月9日 下午3:21:19
	 */
	public static <T extends TreeModel<T>>Node<T> file2PageTree(String basePath, CallBack<T> callBack, Class<T> treeModelClazz) throws InstantiationException, IllegalAccessException{
//		String BASE_PATH = "D:/tool/apache-tomcat-7.0.75/webapps/WebConfig/WEB-INF/jsp";
//		String SUFFIX = ".jsp";
		
		if(callBack == null){
			callBack = new CallBack<T>(){
				@Override
				public void execute(T t) {}
			};
		}
		File baseDir = new File(basePath);
		
		Assert.isTrue(baseDir.exists(),"baseDir:"+baseDir+",文件不存在");
		//遍历逻辑
		Node<T> tNode = null;	//tNode : traversal_node
		Node<T> baseNode = new Node<T>();
		if (baseDir.exists()) {
			//以下分两部分设置，其中节点部分是创建节点树必备属性，TreeModel则用于实现easyUI的树结构
			//初始化基目录节点
			baseNode.setFile(baseDir);
//			String baseFileName = baseNode.getFile().getName();
			baseNode.setParNode(null);
			baseNode.setChildNodes(new LinkedList<Node<T>>());
			baseNode.setReadIndex(-1);

			//初始化基目录TreeModel

			//TreeModel部分
			Integer seq = 0;
			baseNode.setParId(null);
			baseNode.setId(seq.toString());
			baseNode.setText(baseDir.getName());
			baseNode.setChildren(new LinkedList<T>());
			
			T treeModel = treeModelClazz.newInstance();
			treeModel.setParId(null);
			treeModel.setId(seq.toString());
			treeModel.setText(baseDir.getName());
			treeModel.setChildren(new LinkedList<T>());
			callBack.init(baseNode);
			callBack.execute(treeModel);
			baseNode.getTreeModels().add((T) treeModel);
			
			tNode = baseNode;
//			tNode.setBaseNode(baseNode);
			while(true){
				/**
				 * 以下2个值直接控制循环走向，原理是通过文件系统结构，构建节点树实体
				 * childFiles	: 当前文件下包含的子文件，用于建立节点树实体
				 * tNode.getReadIndex()	: 当前循环节点下，作为一个下标，指向正在或已完成循环的子节点
				 */
				List<File> childFiles = Arrays.asList(tNode.getFile().listFiles());
				tNode.setReadIndex(tNode.getReadIndex() + 1);
//				String fileName = tNode.getFile().getName();
				if(tNode.getAbandon()){
					continue;
				}
				//若子节点为末节点（即文件），新建子末节点
				if (childFiles.get(tNode.getReadIndex()).isFile()) {
					//节点部分
					Node<T> childNode = new Node<T>();
					childNode.setFile(childFiles.get(tNode.getReadIndex()));
//					String chileFileName = childNode.getFile().getName();
					childNode.setParNode(tNode);
					childNode.setChildNodes(new LinkedList<Node<T>>());
					childNode.setReadIndex(-1);
					
					//TreeModel部分
					childNode.setParId(tNode.getId());
					childNode.setId(String.valueOf(++seq));
					childNode.setText(childNode.getFile().getName());
					childNode.setChildren(new LinkedList<T>());

					treeModel = treeModelClazz.newInstance();
					treeModel.setParId(tNode.getId());
					treeModel.setId(childNode.getId());
					treeModel.setText(childNode.getFile().getName());
					treeModel.setChildren(new LinkedList<T>());
					callBack.init(childNode);
					if(callBack.abandon()){
						continue;
					}
					callBack.execute(treeModel);
					baseNode.getTreeModels().add((T)treeModel);
					
					System.out.println("文件："+childNode.getFile().getAbsolutePath());
					
					tNode.getChildNodes().add(childNode);
					//该子节点不是当前遍历节点下的最后一个节点时
					if(tNode.getReadIndex()+1 < childFiles.size()){
						continue;
					//该子节点是当前遍历节点下的最后一个节点时
					}else if (tNode.getReadIndex()+1 == childFiles.size()) {
						//完成当前节点遍历时
						//设置读完当前遍历节点
						//若遍历节点有父节点，则指向上移到父节点，继续其遍历
						tNode = findParNodeWithoutReadFinish(tNode);
						//当前遍历节点的父节点"A"指向下一个"A"的子节点，相当于指向遍历节点的下一个兄弟节点
						if(tNode.getParNode() == null && (tNode.getReadIndex()+1) == tNode.getFile().listFiles().length){
							break;
						}
						continue;
					}
				}
				//若子节点为非末节点（即目录），则新增当前目录节点，遍历子节点
				if (childFiles.get(tNode.getReadIndex()).isDirectory()) {
					//如果当前非末节点为新节点，即当前目录没有文件时，新增当前目录
					//节点部分
					Node<T> childNode = new Node<T>();
					childNode.setFile(childFiles.get(tNode.getReadIndex()));
//					String chileFileName = childNode.getFile().getName();
					childNode.setParNode(tNode);
					childNode.setChildNodes(new LinkedList<Node<T>>());
					childNode.setReadIndex(-1);

					//TreeModel部分
					childNode.setParId(tNode.getParId());
					childNode.setId(String.valueOf(++seq));
					childNode.setText(childNode.getFile().getName());
					childNode.setChildren(new LinkedList<T>());

					treeModel = treeModelClazz.newInstance();
					treeModel.setParId(tNode.getId());
					treeModel.setId(childNode.getId());
					treeModel.setText(childNode.getFile().getName());
					treeModel.setChildren(new LinkedList<T>());
					callBack.init(childNode);
					if(callBack.abandon()){
						continue;
					}
					callBack.execute(treeModel);
					baseNode.getTreeModels().add((T)treeModel);

					System.out.println("文件："+childNode.getFile().getAbsolutePath());
					
					if (!isDirWithChildFile(childFiles.get(tNode.getReadIndex()))) {
						childNode.setAbandon(true);
						//完成当前节点遍历时
						//设置读完当前遍历节点
						//若遍历节点有父节点，则指向上移到父节点，继续其遍历
						tNode = findParNodeWithoutReadFinish(tNode);
						//当前遍历节点的父节点"A"指向下一个"A"的子节点，相当于指向遍历节点的下一个兄弟节点
						if(tNode.getParNode() == null && (tNode.getReadIndex()+1) == tNode.getFile().listFiles().length){
							break;
						}
						continue;
					}
					tNode.getChildNodes().add(childNode);
					tNode = childNode;
					continue;
				}
			}
		}
		return baseNode;
	}
	
	private static <T extends TreeModel<T>> Node<T> findParNodeWithoutReadFinish(Node<T> node){
		int subNodeCount = node.getFile().listFiles().length;
		//为起始节点时，返回
		if(node.getParNode() == null && (node.getReadIndex()+1) == subNodeCount){
			return node;
		//该节点已完成遍历，则继续向上查找未完成遍历的父节点
		}else if((node.getReadIndex()+1) == subNodeCount){
			return findParNodeWithoutReadFinish(node.getParNode());
		}
		//未完成遍历的父节点
		return node;
	}

	private static <T extends TreeModel<T>> boolean isDirWithChildFile(File dir){
		if(dir.isFile()){
			throw new RuntimeException("请使用文件夹作为参数");
		}
//		String tFileName = dir.getName();
		File subFiles[] = dir.listFiles();
		for(File subFile : subFiles){
			if(subFile.isFile()){
				return true;
			}
		}
		boolean result = false;
		for(File subFile : subFiles){
			if(subFile.isDirectory()){
				result = isDirWithChildFile(subFile);
				if(result){return true;}
			}
		}
		return result;
	}
	

	public static String readFileByChars(String fileName) {
		 Reader reader = null;
		 StringBuffer sb = new StringBuffer();
	        try {
	            // 一次读多个字符
	            char[] tempchars = new char[1000];
	            int charread = 0;
	            reader = new InputStreamReader(new FileInputStream(fileName));
	            // 读入多个字符到字符数组中，charread为一次读取字符数
	            while ((charread = reader.read(tempchars)) != -1) {
	                // 同样屏蔽掉\r不显示
	                if ((charread == tempchars.length)
	                        && (tempchars[tempchars.length - 1] != '\r')) {
	                	sb.append(tempchars);
	                } else {
	                    for (int i = 0; i < charread; i++) {
	                        if (tempchars[i] == '\r') {
	                            continue;
	                        } else {
	    	                	sb.append(tempchars[i]);
	                        }
	                    }
	                }
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
			return sb.toString();
	}

	/**
	 *	递归主页菜单
	 *	@author hefule
	 *	@date 2017年1月16日 下午7:17:16
	 *	@param treeList 初始化数据列表
	 *	@param parSeqCode 父节点
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 *
	 */
	public static <T extends TreeModel<T>>List<T> searchChild(List<T> treeList , String parSeqCode, Class<T> tclass) throws IllegalAccessException, InvocationTargetException, InstantiationException{
		List<T> returnList = new ArrayList<T>();
		if(parSeqCode == null){
			parSeqCode = "";
		}
		if(treeList != null){
			for(T indexTreeVo : treeList){
				String parId = indexTreeVo.getParId()==null? "": indexTreeVo.getParId();
				if(parSeqCode.equals(parId)){
					T iTreeVo = tclass.newInstance();
					BeanUtils.copyProperties(iTreeVo, indexTreeVo);
					List<T> lt = searchChild(treeList, indexTreeVo.getId(), tclass);
					if(!StringUtil.listEmpty(lt)){
						iTreeVo.setState(indexTreeVo.getState());
					}
					iTreeVo.setChildren((List<T>) lt);
					returnList.add((T) iTreeVo);
				}
			}
		}
		return returnList;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		FileUtils.file2PageTree("/D:/tool/apache-tomcat-7.0.75/webapps/WebConfig/WEB-INF/classes/", null, WebConfigTreeModel.class);
//		FileUtils.file2PageTree("/D:/tool/apache-tomcat-7.0.75/webapps/WebConfig/WEB-INF/jsp", null);
	}
}
