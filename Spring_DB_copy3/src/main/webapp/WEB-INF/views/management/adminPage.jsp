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
	<div class="container">
		<h3>adminPage</h3>
		<div id="nav">
			<%@ include file="../include/nav.jsp"%>
		</div>

		<div>권한부여</div>${status }
		<c:if test="${status ge 1}">
			매니저부터
			<c:if test="${status ge 2}">
				관리자부터
				<form method="POST" action="authority">
					<select name="user" size="1">
						<c:forEach var="user" items="${list}">
							<option value="${user}">${user}</option>
						</c:forEach>
					</select>
				 	<input type="submit" value="매니저등록" />
				</form>
			</c:if>
			
			<br><br>
			<form method="POST" action="addGenre">
				장르(영):<input size="10" maxlength="20" type="text" name="genreEng" /><br>
				장르(한):<input size="10" maxlength="3"  type="text" name="genreKor" />
				<input type="submit" value="장르추가"/>
			</form>
			
			<br><br>
			
			<form method="POST" action="deleteGenre">
				<select name="genreDelete" size="1">
					<c:forEach var="list" items="${genreList}">
						<option value="${list.genreEng}">${list.genreKor}</option>
					</c:forEach>
				</select>
				<input type="submit" value="장르삭제"/>
			</form>
		</c:if>
	</div>
</body>
</html>