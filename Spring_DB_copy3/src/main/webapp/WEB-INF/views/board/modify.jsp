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
	<h3>게시물 수정</h3>

	<form method="post">
		<label>title</label> 
		<input type="text" value="${list.title}" name="title">
		<label>writer</label> 
		<input type="text" value="${list.writer}" name="writer" readOnly/> <br>
		<label>content</label>
		<textarea rows="5" cols="50" name="content">${list.content}</textarea>
		<br> <input type="submit" value="게시물  수정">
		<!-- <button><a href="#">취소</a></button> -->
	</form>
		
	<c:if test="${!empty param.emptyBox}">
		<script>
			alert("제목 또는 내용이 비어있습니다.")
		</script>
	</c:if>
</body>
</html>
 