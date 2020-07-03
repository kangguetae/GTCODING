package com.parucnc.test_3.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.parucnc.test_3.dao.BoardDAO;
import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.UserVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;

	@Override
	public List list(BoardVO vo) throws Exception {
		return dao.list(vo);
	}

	@Override
	public BoardVO view(int bno) throws Exception {
		return dao.view(bno);
	}

	@Override
	public void write(BoardVO vo) throws Exception {
		dao.write(vo);
	}

	@Override
	public void delete(int bno) throws Exception {
		dao.delete(bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		dao.update(vo);
	}

	@Override
	public List listPage(int startNum) throws Exception {
		startNum = (startNum - 1) * 10;
		return dao.listPage(startNum);
	}
	
	@Override
	public int count() throws Exception{
		return dao.count();
	}
	
	@Override
	public void viewUpdate(int bno) throws Exception{
		dao.viewUpdate(bno);
	}
	
	@Override
	public List search(Map map) throws Exception{
		System.out.println("a");
		return dao.search(map);
	}
	
	@Override
	public int searchCount(Map map) throws Exception{
		int temp = (int)(map.get("startNum"));
		temp = (temp-1)*10;
		map.put("startNum", temp);
		
		return dao.searchCount(map);
	}
}