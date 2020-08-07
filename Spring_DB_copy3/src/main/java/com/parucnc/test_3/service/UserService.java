package com.parucnc.test_3.service;

import com.parucnc.test_3.domain.UserVO;

public interface UserService {
	public UserVO loginCheck(UserVO vo) throws Exception;
	public void insertUser(UserVO vo) throws Exception;
	public UserVO isAdmin(UserVO vo) throws Exception;
	public UserVO remID(int uNum) throws Exception;
}
