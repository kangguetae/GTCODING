package com.parucnc.test_3.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.parucnc.test_3.domain.User_BoardVO;
import com.parucnc.test_3.domain.fileUploadVO;


public interface fileUploadService {
	public void fileSave(Map map) throws Exception;
	public List fileFind(Map map) throws Exception;
	public fileUploadVO getFileInfo(int fno) throws Exception;
}
