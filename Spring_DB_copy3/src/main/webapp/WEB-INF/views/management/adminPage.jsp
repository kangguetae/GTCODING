<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	button{
		align: center;
	}
</style>
<link rel="stylesheet" href="../resources/css/bootstrap.css">

</head>
<body>
	<div class="container">
		<h3>adminPage</h3>
		<div id="nav">
			<%@ include file="../include/nav.jsp"%>
		</div>

		
		<c:if test="${status ge 1}">
			매니저부터
			<form method="POST" action="addGenre"> 
				<div class="form-group">
					장르(영):<input class="form-control" size="10" maxlength="20" type="text" name="genreEng" />
					장르(한):<input class="form-control" size="10" maxlength="3"  type="text" name="genreKor" />
					<button class="btn btn-primary">장르추가</button>
				</div>
			</form>
			
			<br>
			
			<form method="POST" action="deleteGenre">
				<select class="form-control" name="genreDelete" size="1">
					<c:forEach var="list" items="${genreList}">
						<option value="${list.genreEng}">${list.genreKor}</option>
					</c:forEach>
				</select>
				<button class="btn btn-primary">장르삭제</button>
			</form>
			
			<br><br>
			<c:if test="${status ge 2}">
				관리자부터
				<form method="POST" action="authority">
					<select class="form-control" name="user" size="1">
						<c:forEach var="user" items="${list}">
							<option value="${user}">${user}</option>
						</c:forEach>
					</select>
				 	<button class="btn btn-primary">매니저등록</button>
				</form>
			</c:if>
			
			
			
		</c:if>
	</div>
</body>
</html>