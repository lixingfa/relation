package com.garlane.relation.upload.service;

import org.springframework.web.multipart.MultipartFile;

import com.garlane.relation.common.utils.exception.SuperServiceException;

public interface UploadService {

	/**
	 * 上传项目
	 * @param files 项目的所有文件
	 * @return 项目在服务器上的路径
	 * @throws SuperServiceException
	 */
	String upload(MultipartFile[] files) throws SuperServiceException;
}
