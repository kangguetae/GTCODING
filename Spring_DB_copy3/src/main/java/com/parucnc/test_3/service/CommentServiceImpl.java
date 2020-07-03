package com.parucnc.test_3.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.parucnc.test_3.dao.CommentDAOImpl;
import com.parucnc.test_3.domain.CommentVO;

@Service
public class CommentServiceImpl {
	@Inject
	private CommentDAOImpl dao;
	
	public void insert(CommentVO vo) throws Exception{
		dao.insert(vo);
	}
	public List showComment(CommentVO vo) throws Exception{
		return dao.showComment(vo);
	}
}
