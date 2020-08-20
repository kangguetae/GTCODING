package com.parucnc.test_3.service;

import java.util.List;

import com.parucnc.test_3.domain.GenreVO;


public interface GenreService {
	public void addGenre(GenreVO vo) throws Exception;
	public List genreList() throws Exception;
	public List selectedGenreList() throws Exception;
	public void deleteGenre(String genreEng) throws Exception;
	public void genreSelectT(String [] checkList) throws Exception;
	public void genreSelectF(String [] checkList) throws Exception;
}
