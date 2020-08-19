package com.parucnc.test_3.service;

import java.util.List;

import com.parucnc.test_3.domain.GenreVO;


public interface GenreService {
	public void addGenre(GenreVO vo) throws Exception;
	public List genreList() throws Exception;
	public void deleteGenre(String genreEng) throws Exception;
}
