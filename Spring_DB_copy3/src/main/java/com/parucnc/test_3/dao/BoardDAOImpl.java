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
	public BoardVO view(long bno) throws Exception {
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

//	@Override
//	public List listPage(int startNum) throws Exception {
//		
//		return sql.selectList(namespace + ".listPage", startNum);
//	}
//	
//	@Override
//	public int count() throws Exception{
//		return sql.selectOne(namespace+".count");
//	}
	
	@Override
	public List listPage(Map map) throws Exception {
		
		return sql.selectList(namespace + ".listPage", map);
	}
	
	@Override
	public int count(Map map) throws Exception{
		return sql.selectOne(namespace+".count", map);
	}
	
	@Override
	public void likeOrDislike(Map map) throws Exception{
		sql.update(namespace+".likeOrDislike", map);
	}
	
	
	
	@Override
	public void viewUpdate(long bno) throws Exception{
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
	public List listPage_chat(int startNum) throws Exception{
		return sql.selectList(namespace+".listPage_chat",  startNum);
	}
	
	@Override
	public int count_chat() throws Exception{
		return sql.selectOne(namespace+".count_chat");
	}
	
	@Override
	public List listPage_announcement(int startNum) throws Exception{
		return sql.selectList(namespace+".listPage_announcement",  startNum);
	}
	
	@Override
	public int count_announcement() throws Exception{
		return sql.selectOne(namespace+".count_announcement");
	}
	
	@Override
	public List listPage_question(int startNum) throws Exception{
		return sql.selectList(namespace+".listPage_question",  startNum);
	}
	
	@Override
	public int count_question() throws Exception{
		return sql.selectOne(namespace+".count_question");
	}
}
