package com.parucnc.test_3.service;

import com.parucnc.test_3.domain.UserVO;

public interface UserService {
	public UserVO loginCheck(UserVO vo) throws Exception;
	public void insertUser(UserVO vo) throws Exception;
}
