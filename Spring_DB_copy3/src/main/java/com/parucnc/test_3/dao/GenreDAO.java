package com.parucnc.test_3.dao;

import java.util.List;

import com.parucnc.test_3.domain.GenreVO;

public interface GenreDAO {
	public void addGenre(GenreVO vo) throws Exception;
	public List genreList() throws Exception;
	public void deleteGenre(String genreEng) throws Exception;
}
