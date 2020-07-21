package com.parucnc.test_3.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.parucnc.test_3.domain.User_BoardVO;

@Repository
public class User_BoardDAOImpl implements User_BoardDAO{
	
	private static String namespace = "com.user_board.mappers.user_board";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public void u_bMapping(Map map) throws Exception{
		sql.insert(namespace+".u_bMapping", map);
	}
	
	@Override
	public User_BoardVO check_clickedBefore(Map map) throws Exception{
		return sql.selectOne(namespace+".check_clickedBefore", map);
	}
	
	@Override
	public void recommDelete(Map map) throws Exception{
		sql.delete(namespace+".recommDelete", map);
	}
	
	@Override
	public int countLike(Map map) throws Exception{
		return sql.selectOne(namespace+".countLike", map);
	}
	
	@Override
	public int countDislike(Map map) throws Exception{
		return sql.selectOne(namespace+".countDislike", map);
	}
}
