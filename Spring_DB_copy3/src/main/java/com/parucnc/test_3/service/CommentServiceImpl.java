package com.parucnc.test_3.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.parucnc.test_3.dao.CommentDAOImpl;
import com.parucnc.test_3.domain.CommentVO;

@Service
public class CommentServiceImpl implements CommentService{
	@Inject
	private CommentDAOImpl dao;
	
	@Override
	public void insert(CommentVO vo) throws Exception{
		dao.insert(vo);
	}
	
	@Override
	public List showComment(CommentVO vo) throws Exception{
		return dao.showComment(vo);
	}
	
	@Override
	public List commentCount(CommentVO vo) throws Exception{
		return dao.commentCount(vo);
	}
	
	@Override
	public void commentDelete(String cno) throws Exception{
		dao.commentDelete(cno);
	}
	
	@Override
	public void commentModify(Map map) throws Exception{
		dao.commentModify(map);
	}
}
