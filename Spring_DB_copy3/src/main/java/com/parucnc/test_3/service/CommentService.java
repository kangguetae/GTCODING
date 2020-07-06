package com.parucnc.test_3.service;

import java.util.List;
import java.util.Map;

import com.parucnc.test_3.domain.CommentVO;

public interface CommentService {
	public void insert(CommentVO vo) throws Exception;
	public List showComment(CommentVO vo) throws Exception;
	public List commentCount(CommentVO vo) throws Exception;
}
