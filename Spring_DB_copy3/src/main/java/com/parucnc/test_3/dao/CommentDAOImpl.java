package com.parucnc.test_3.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.parucnc.test_3.domain.CommentVO;

@Repository
public class CommentDAOImpl implements CommentDAO{
	
	@Inject
	private SqlSession sql;
	private static String namespace = "com.comment.mappers.comment";
	
	@Override
	public void insert(CommentVO vo) throws Exception{
		sql.insert(namespace+".insert", vo);
	}
	
	@Override
	public List showComment(CommentVO vo) throws Exception{
		return sql.selectList(namespace+".showComment", vo);
	}

	@Override
	public List commentCount(CommentVO vo) throws Exception{
		return sql.selectList(namespace+".commentCount", vo);
		
	}

	@Override
	public void commentDelete(String cno) throws Exception{
		int c = Integer.parseInt(cno);
		sql.delete(namespace+".deleteComment", c);
	}
	
	@Override
	public void commentModify(Map map) throws Exception{
		sql.update(namespace+".modifyComment", map);
	}
}
