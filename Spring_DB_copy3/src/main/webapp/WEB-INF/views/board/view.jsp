<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>view</h1>
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>

	
		<label>title: </label> 
		${view.title}
		<label> writer: </label> 
		${view.writer}<br>
		<label> content: </label>
		${view.content}<br><br>
		
	
	<a href="/board/delete/?bno=${view.bno}">게시물 삭제</a> <!-- 페이지 이동X 해당 페이지로의 요청 -->
	<a href="/board/modify/?bno=${view.bno}">게시물 수정</a>
	
	
	<br><br><label>댓글</label><br>
	<c:forEach var="commentList" items="${comment}">
		${commentList.comm}<br>
	</c:forEach>
	
	
	<form method="POST">
		<textarea rows="2" cols="40" name="comm"  placeholder = "댓글"></textarea>
		<input type="hidden" name = "bno" value="${view.bno}"/>
		<input type="submit" value="댓글작성" />
	</form>
</body>
</html>