package com.parucnc.test_3.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.parucnc.test_3.dao.User_BoardDAOImpl;
import com.parucnc.test_3.dao.fileUploadDAOImpl;
import com.parucnc.test_3.domain.User_BoardVO;
import com.parucnc.test_3.domain.fileUploadVO;

@Service
public class fileUploadServiceImpl implements fileUploadService{
	@Inject
	fileUploadDAOImpl dao;
	
	@Override
	public void fileSave(Map map) throws Exception{
		dao.fileSave(map);
	}
	
	@Override
	public List fileFind(Map map) throws Exception{
		return dao.fileFind(map);
	}
	
	@Override
	public fileUploadVO getFileInfo(int fno) throws Exception{
		return dao.getFileInfo(fno);
	}
}
