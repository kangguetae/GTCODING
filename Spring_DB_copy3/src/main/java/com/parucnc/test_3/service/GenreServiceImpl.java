package com.parucnc.test_3.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.parucnc.test_3.dao.GenreDAO;
import com.parucnc.test_3.dao.UserDAO;
import com.parucnc.test_3.domain.GenreVO;
import com.parucnc.test_3.domain.UserVO;

@Service
public class GenreServiceImpl implements GenreService{

	@Inject
	private GenreDAO dao;
	
	@Override
	public void addGenre(GenreVO vo) throws Exception{
		dao.addGenre(vo);
	}
}
