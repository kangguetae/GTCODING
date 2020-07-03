package com.parucnc.test_3.dao;

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
//		Object a = sql.selectOne(namespace+".loginCheck", vo);
//		System.out.println(a);
//		return null;
		return sql.selectOne(namespace+".loginCheck", vo);
	}
	
	@Override
	public void insertUser(UserVO vo) throws Exception{
		sql.insert(namespace+".insertUser", vo);
	}
}
