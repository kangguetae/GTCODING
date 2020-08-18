package com.parucnc.test_3.dao;
import java.util.List;
import java.util.Map;

import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.UserVO;



public interface BoardDAO {
	public List list(BoardVO vo) throws Exception;
	public BoardVO view(long bno) throws Exception;
	public void write(BoardVO vo) throws Exception;
	public void delete(int bno) throws Exception;
	public void update(BoardVO vo) throws Exception;
	
	
//	public List listPage(int startNum) throws Exception;
//	public int count() throws Exception;
	public List listPage(Map map) throws Exception;
	public int count(Map map) throws Exception;
	
	public void viewUpdate(long bno) throws Exception;
	
	public List search(Map map) throws Exception;
	public int searchCount(Map map) throws Exception;
	
	public void delChoosedGenreBoard(String genre) throws Exception;

	public List listPage_chat(int startNum) throws Exception;
	public int count_chat() throws Exception;
	
	public List listPage_announcement(int startNum) throws Exception;
	public int count_announcement() throws Exception;
	
	public List listPage_question(int startNum) throws Exception;
	public int count_question() throws Exception;
	
	public void likeOrDislike(Map map) throws Exception;
	public int mostRecentBno() throws Exception;
	public int count2(String[]genres) throws Exception;
}