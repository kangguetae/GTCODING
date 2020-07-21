package com.parucnc.test_3.dao;

import java.util.List;
import java.util.Map;

import com.parucnc.test_3.domain.User_BoardVO;

public interface User_BoardDAO {
	public void u_bMapping(Map map) throws Exception;
	public User_BoardVO check_clickedBefore(Map map) throws Exception;
	public void recommDelete(Map map) throws Exception;
	public int countLike(Map map) throws Exception;
	public int countDislike(Map map) throws Exception;
}
