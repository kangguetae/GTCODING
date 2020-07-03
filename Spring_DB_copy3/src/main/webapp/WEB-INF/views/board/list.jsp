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
	게시판 목록
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>
	<br>

	<table>
		<tr>
			<th>bno</th>
			<th>writer</th>
			<th>title</th>
			<th>regDate</th>
			<th>viewCnt</th>
		</tr>
		<c:forEach var="list" items="${list}">
			<tr>
				<td>${list.bno}</td>
				<td>${list.writer}</td>
				<td><a href="/board/view/?bno=${list.bno}">${list.title}</a></td>
				<td>${list.regDate}</td>
				<td>${list.viewCnt}</td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>