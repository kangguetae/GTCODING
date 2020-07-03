<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	게시판 작성
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>

	<form method="post">
		<label>title</label> 
		<input type="text" name="title">
		
		<label>writer</label> 
		<input type="text" name="writer" /> <br>
		
		<label>content</label>
		<textarea rows="5" cols="50" name="content"></textarea>
		
		<input type="submit" value="작성">
	</form>
	
</body>
</html>