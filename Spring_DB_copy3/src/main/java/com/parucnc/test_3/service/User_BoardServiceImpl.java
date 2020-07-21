package com.parucnc.test_3.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.parucnc.test_3.dao.User_BoardDAOImpl;
import com.parucnc.test_3.domain.User_BoardVO;

@Service
public class User_BoardServiceImpl implements User_BoardService{
	@Inject
	User_BoardDAOImpl dao;
	
	@Override
	public void u_bMapping(Map map) throws Exception{
		dao.u_bMapping(map);
	}
	
	@Override
	public User_BoardVO check_clickedBefore(Map map) throws Exception{
		return dao.check_clickedBefore(map);
	}
	
	@Override
	public void recommDelete(Map map) throws Exception{
		dao.recommDelete(map);
	}
	
	@Override
	public int countLike(Map map) throws Exception{
		return dao.countLike(map);
	}
	@Override
	public int countDislike(Map map) throws Exception{
		return dao.countDislike(map);
	}
}
