package com.garlane.relation.upload.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.garlane.relation.common.constant.ConfigConstant;
import com.garlane.relation.common.utils.exception.SuperServiceException;
import com.garlane.relation.common.utils.file.FileUtils;
import com.garlane.relation.upload.service.UploadService;
@Service("uploadService")
public class UploadServiceImpl implements UploadService{
	// 文件格式要求
	private String[] suffixArr = ConfigConstant.UPLOAD_FILE_SUFFIXS.split(",");
	private List<String> suffixList = Arrays.asList(suffixArr);
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssSSSS");
	
	/**
	 * 上传项目
	 * @param files 项目的所有文件
	 * @return 项目在服务器上的路径
	 * @throws SuperServiceException
	 */
	public String upload(MultipartFile[] files) throws SuperServiceException{
		// 判断存储的文件夹是否存在
		if (files.length > ConfigConstant.UPLOADFILE_MAX_NUM) {
			throw new SuperServiceException("上传的文件总数大于" + ConfigConstant.UPLOADFILE_MAX_NUM + "，当前项目文件总数：" + files.length);
		}

		try {
			// 遍历文件夹，看是否有违规
			for (MultipartFile mf : files) {
				if (!mf.isEmpty()) {
					String originalFilename = mf.getOriginalFilename();
					String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
					if (!suffixList.contains(suffix)) {
						throw new SuperServiceException(originalFilename + "不在允许上传的文件类型中。只允许上传：" + ConfigConstant.UPLOAD_FILE_SUFFIXS);
					}
					if (mf.getSize() > ConfigConstant.UPLOADFILE_MAX_SIZE) {
						throw new SuperServiceException(originalFilename + "单个文件超过允许上传的" + ConfigConstant.UPLOADFILE_MAX_SIZE + "，当前大小：" + mf.getSize());
					}					
				}
			}
			String path = ConfigConstant.ROOT_PATH_ATTACHMENT + format.format(new Date()) + File.separator;//TODO 要加上当前用户名
			new File(path).mkdirs();
			String priorPath = null;
			//写入文件
			for (MultipartFile mf : files) {
				//保持上传时的目录结构
				String name = ((CommonsMultipartFile)mf).getFileItem().getName();//找这个找得好辛苦，幸好看着源码找到了
				String dir = name.substring(0, name.lastIndexOf("/"));
				if (!dir.equals(priorPath)) {
					priorPath = dir;
					FileUtils.FileCreate(path + name);					
				}
				mf.transferTo(new File(path + name));
			}
			return path;
		} catch (FileNotFoundException e) {
			throw new SuperServiceException("找不到文件！", e);
		} catch (IOException e) {
			throw new SuperServiceException("IO接口异常！", e);
		}
	}

}
