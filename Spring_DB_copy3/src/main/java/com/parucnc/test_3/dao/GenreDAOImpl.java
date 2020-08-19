package com.parucnc.test_3.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.parucnc.test_3.domain.GenreVO;
import com.parucnc.test_3.domain.UserVO;

@Repository
public class GenreDAOImpl implements GenreDAO{

	private static String namespace = "com.genre.mappers.genre";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public void addGenre(GenreVO vo) throws Exception{
		sql.insert(namespace+".addGenre", vo);
	}
	
	@Override
	public List genreList() throws Exception{
		return sql.selectList(namespace+".genreList");
	}
	
	@Override
	public void deleteGenre(String genreEng) throws Exception{
		sql.delete(namespace+".deleteGenre", genreEng);
	}
	
}
