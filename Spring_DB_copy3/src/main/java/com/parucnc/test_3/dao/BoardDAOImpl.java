package com.parucnc.test_3.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.UserVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Inject
	private SqlSession sql;
	private static String namespace = "com.board.mappers.board";

	@Override
	public List list(BoardVO vo) throws Exception {

		return sql.selectList(namespace + ".list", vo);
	}

	@Override
	public BoardVO view(int bno) throws Exception {
		return sql.selectOne(namespace + ".view", bno);
	}

	@Override
	public void write(BoardVO vo) throws Exception {
		sql.insert(namespace + ".write", vo);
	}

	@Override
	public void delete(int bno) throws Exception {
		sql.delete(namespace + ".delete", bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sql.update(namespace + ".update", vo);
	}

	@Override
	public List listPage(int startNum) throws Exception {
		
		return sql.selectList(namespace + ".listPage", startNum);
	}
	
	@Override
	public int count() throws Exception{
		return sql.selectOne(namespace+".count");
	}
	
	@Override
	public void viewUpdate(int bno) throws Exception{
		sql.update(namespace+".viewUpdate", bno);
	}
	
	@Override
	public List search(Map map) throws Exception{
		return sql.selectList(namespace+".search", map);
	}
	
	@Override
	public int searchCount(Map map) throws Exception{
		return sql.selectOne(namespace+".searchCount", map);
	}
	
	@Override
	public List merge(Map map) throws Exception{
		return sql.selectList(namespace+".merge", map);
	}
}
