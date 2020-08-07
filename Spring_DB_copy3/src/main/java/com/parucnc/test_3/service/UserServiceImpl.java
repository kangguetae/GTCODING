package com.parucnc.test_3.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.parucnc.test_3.dao.UserDAO;
import com.parucnc.test_3.domain.UserVO;

@Service
public class UserServiceImpl implements UserService{

	@Inject
	private UserDAO dao;
	
	@Override
	public UserVO loginCheck(UserVO vo) throws Exception{
		return dao.loginCheck(vo);
	}
	
	@Override
	public void insertUser(UserVO vo) throws Exception{
		dao.insertUser(vo);
	}
	
	@Override
	public UserVO isAdmin(UserVO vo) throws Exception{
		return dao.isAdmin(vo);
	}
	
	@Override
	public UserVO remID(int uNum) throws Exception{
		return dao.remID(uNum);
	}
}
