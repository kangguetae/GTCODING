package com.parucnc.test_3.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.parucnc.test_3.domain.BoardVO;
import com.parucnc.test_3.domain.UserVO;
import com.parucnc.test_3.domain.fileUploadVO;

@Repository
public class fileUploadDAOImpl implements fileUploadDAO {
	@Inject
	private SqlSession sql;
	private static String namespace = "com.fileUpload.mappers.fileUpload";

	@Override
	public void fileSave(Map map) throws Exception{
		sql.insert(namespace+".fileSave", map);
	}
	
	@Override
	public List fileFind(Map map) throws Exception{
		return sql.selectList(namespace+".fileFind", map);
	}
	
	
	@Override
	public fileUploadVO getFileInfo(int fno) throws Exception{
		return sql.selectOne(namespace+".getFileInfo", fno);
	}
}
