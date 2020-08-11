<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	<ul class="nav nav-pills blue">
		<li class="page-item">
			<a class="page-link" href="/logout">로그아웃</a>
		</li>
		
		<li class="page-item">
			<a class="page-link" href="/board/write">작성</a>
		</li>
		<li class="page-item">
			<a class="page-link" href="/board/listPage?currentPage=1">목록(페이징)</a>
		</li>
		<c:if test="${isAdmin}">
			<li class="page-item">
				<a class="page-link" href="/management/adminPage">관리자 페이지</a>
			</li>
		</c:if>
	</ul>
	