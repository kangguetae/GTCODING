package com.parucnc.test_3.service;

import java.util.List;

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
	
	@Override
	public List genreList() throws Exception{
		return dao.genreList();
	}
	
	@Override
	public void deleteGenre(String genreEng) throws Exception{
		dao.deleteGenre(genreEng);
	}
}
