package com.parucnc.test_3.dao;
import java.util.List;
import java.util.Map;

import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.domain.fileUploadVO;



public interface fileUploadDAO {
	public void fileSave(Map map) throws Exception;
	public List fileFind(Map map) throws Exception;
	public fileUploadVO getFileInfo(int fno) throws Exception;
}