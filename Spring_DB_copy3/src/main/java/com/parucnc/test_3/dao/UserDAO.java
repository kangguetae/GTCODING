package com.parucnc.test_3.dao;

import java.util.List;

import com.parucnc.test_3.domain.UserVO;

public interface UserDAO {
	public UserVO loginCheck(UserVO vo) throws Exception;
	public void insertUser(UserVO vo) throws Exception;
	public UserVO isAdmin(UserVO vo) throws Exception;
	public UserVO remID(int uNum) throws Exception;
	public List userList() throws Exception;
	public void empowerment(String id) throws Exception;
	public void deprivation(String id) throws Exception;
	public List managerList() throws Exception;
}
