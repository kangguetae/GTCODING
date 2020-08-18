package com.parucnc.test_3.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.parucnc.test_3.domain.UserVO;

	

@Repository
public class UserDAOImpl implements UserDAO{
	
	
	private static String namespace = "com.user.mappers.user";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public UserVO loginCheck(UserVO vo) throws Exception{
		return sql.selectOne(namespace+".loginCheck", vo);
	}
	
	@Override
	public void insertUser(UserVO vo) throws Exception{
		sql.insert(namespace+".insertUser", vo);
	}
	
	@Override
	public UserVO isAdmin(UserVO vo) throws Exception{
		return sql.selectOne(namespace+".isAdmin", vo);
	}
	
	@Override
	public UserVO remID(int uNum) throws Exception{
		return sql.selectOne(namespace+".remID", uNum);
	}
	
	@Override
	public List userList() throws Exception{
		return sql.selectList(namespace+".userList");
	}
	
	@Override
	public void empowerment(String id) throws Exception{
		sql.update(namespace+".empowerment", id);
	}
}
