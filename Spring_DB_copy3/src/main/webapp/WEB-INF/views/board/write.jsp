<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>게시판 작성</h3>
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>

	<form method="post" enctype="multipart/form-data">
		<label>title</label> 
		<input type="text" name="title">
		
		<label>writer</label> 
		<input type="text" name="writer" value="${userID}" readOnly /> <br>
		
		<label>content</label>
		<textarea rows="5" cols="50" name="content"></textarea>
		<br>
		<input type="file" name="fileUpload" accept="image/*, video/*" multiple/>
		<br>
		<select name="genre" size="1">
			<option value="question">질문</option>
			<option value="announcement" selected>공지</option>
			<option value="chat">잡담</option>
		</select>
		<input type="submit" value="작성">
	</form>
	
</body>
</html>