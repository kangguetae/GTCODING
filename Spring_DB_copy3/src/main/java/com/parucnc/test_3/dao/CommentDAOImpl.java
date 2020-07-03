package com.parucnc.test_3.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.parucnc.test_3.domain.CommentVO;

@Repository
public class CommentDAOImpl implements CommentDAO{
	
	@Inject
	private SqlSession sql;
	private static String namespace = "com.comment.mappers.comment";
	
	public void insert(CommentVO vo) throws Exception{
		sql.insert(namespace+".insert", vo);
	}
	
	public List showComment(CommentVO vo) throws Exception{
		return sql.selectList(namespace+".showComment", vo);
	}
}
