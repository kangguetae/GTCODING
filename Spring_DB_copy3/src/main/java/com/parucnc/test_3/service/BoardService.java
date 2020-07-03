package com.parucnc.test_3.service;

import java.util.List;
import java.util.Map;

import com.parucnc.test_3.dao.BoardDAO;
import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.UserVO;

public interface BoardService {
	public List list(BoardVO vo) throws Exception;
	public BoardVO view(int bno) throws Exception;
	public void write(BoardVO vo) throws Exception;
	public void delete(int bno) throws Exception;
	public void update(BoardVO vo) throws Exception;
	
	public List listPage(int startNum) throws Exception;
	public int count() throws Exception;
	public void viewUpdate(int bno) throws Exception;
	public List search(Map map) throws Exception;
	public int searchCount(Map map) throws Exception;
}
