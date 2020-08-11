<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/bootstrap.css">
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
			<option value="question">질문</option>
			<c:if test="${isAdmin}">
				<option value="announcement" selected>공지</option>
			</c:if>
			<option value="chat">잡담</option>
		</select>
		<input class="btn btn-primary" type="submit" value="작성">
	</form>
	</div>
</body>
</html>