<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/bootstrap.css">
</head>
<body>
	<h3>검색</h3>
	
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>
	<br>
	<%-- <div id="nav_genre">
		<%@ include file="../include/nav_genre.jsp"%>
	</div> --%>
	<table>
		<tr>
			<th>bno</th>
			<th>writer</th>
			<th>title</th>
			<th>regDate</th>
			<th>viewCnt</th>
		</tr>
		<c:forEach var="list" items="${listPage}">
			<tr>
				<td>${list.bno}</td>
				<td>${list.writer}</td>
				<td><a href="/board/view/?bno=${list.bno}">${list.title}</a></td>
				<td>${list.regDate}</td>
				<td>${list.viewCnt}</td>
			</tr>
		</c:forEach> 
	</table>
	
	<c:if test="${startNum >= 11}">
	[<a href="/board/search?search=${searchingWay}&contain=${searchWord}&currentPage=${startNum-10}">이전</a>]
	</c:if>
	
	<c:forEach var="page" begin="${startNum}" end="${endNum}">
		${empty param.search ? 'listPage' : ''}
		<c:if test="${page ne currentPage}">
			<a href="/board/search?search=${searchingWay}&contain=${searchWord}&currentPage=${page}">${page}</a>
		</c:if>
		<c:if test="${page eq currentPage}">
			<b>${page}</b>
		</c:if>
		
	</c:forEach>
	<c:if test="${startNum+10<=lastPage }">
	[<a href="/board/search?search=${searchingWay}&contain=${searchWord}&currentPage=${startNum+10}">다음</a>]
	</c:if>
	<br>
	<form method="GET">
		<select name="search" size="1">
			<option value="title" selected>제목</option>
			<option value="content">내용</option>
			<option value="titleAndContent">제목+내용</option>
		</select>
		<input type="text" name="contain" value="${searchWord}"/>
		<input type="submit" value="검색">
	</form> 
</body>
</html>