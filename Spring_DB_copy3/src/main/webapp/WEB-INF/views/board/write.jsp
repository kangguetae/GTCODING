<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
</head>
<body>
	<div class="container">
	<h3>write</h3>
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>
	

	<form method="post" enctype="multipart/form-data">
		<div class="form-group">
		<label>title</label> 
		<input class="form-control" type="text" name="title">
		</div>
		<div class="form-group">
		<label>writer</label> 
		<input class="form-control" type="text" name="writer" value="${userID}" readOnly />
		</div>
		<div class="form-group">
		<label>content</label>
		<textarea class="form-control" rows="5" cols="50" name="content"></textarea>
		</div>
		
		<input type="file" name="fileUpload" accept="image/*, video/*" multiple/>
		
		<select name="genre" size="1">
			<c:if test="${status ge 1}">
				<option value="announcement" selected>공지</option>
			</c:if>
			<c:forEach var="list" items="${genreList}">
				<c:if test="${list.genreEng != 'announcement'}">
					<option value="${list.genreEng}">${list.genreKor }</option>
				</c:if>
			</c:forEach>
			<!-- <option value="question">질문</option>
			<option value="chat">잡담</option> -->
		</select>
		<input class="btn btn-primary" type="submit" value="작성">
	</form>
	</div>
	
	<c:if test="${!empty param.emptyBox}">
		<script>
			alert("제목 또는 내용이 비어있습니다.")
		</script>
	</c:if>
	
</body>
</html>