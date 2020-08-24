package com.parucnc.test_3.dao;

import java.util.List;
import java.util.Map;

import com.parucnc.test_3.domain.CommentVO;

public interface CommentDAO {
	public void insert(CommentVO vo) throws Exception;
	public List showComment(CommentVO vo) throws Exception;
	public List commentCount(CommentVO vo) throws Exception;
	public void commentDelete(String cno) throws Exception;
	public void commentModify(Map map) throws Exception;
}
