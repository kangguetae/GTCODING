package com.parucnc.test_3.service;

import java.util.List;

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
	
	@Override
	public List userList() throws Exception{
		return dao.userList();
	}
	
	@Override
	public void empowerment(String id) throws Exception{
		dao.empowerment(id);
	}
	
	@Override
	public List managerList() throws Exception{
		return dao.managerList();
	}
	
	@Override
	public void deprivation(String id) throws Exception{
		dao.deprivation(id);
	}
}
