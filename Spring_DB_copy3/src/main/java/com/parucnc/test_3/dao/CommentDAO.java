package com.parucnc.test_3.dao;

import java.util.List;

import com.parucnc.test_3.domain.CommentVO;

public interface CommentDAO {
	public void insert(CommentVO vo) throws Exception;
	public List showComment(CommentVO vo) throws Exception;
}
