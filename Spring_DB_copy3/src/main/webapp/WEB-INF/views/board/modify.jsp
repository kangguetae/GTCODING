<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h3>�Խù� ����</h3>

	<form method="post">
		<label>title</label> 
		<input type="text" value="${list.title}" name="title">
		<label>writer</label> 
		<input type="text" value="${list.writer}" name="writer"/> <br>
		<label>content</label>
		<textarea rows="5" cols="50" name="content">${list.content}</textarea>
		<br> <input type="submit" value="�Խù�  ����">
	</form>
</body>
</html>
 