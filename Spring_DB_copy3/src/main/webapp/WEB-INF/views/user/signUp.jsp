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

		<h3>회원가입 페이지</h3>

		<form method="post">
			<div class="form-group">
				id: <input class="form-control" type="text" name="id" /> 
				pw: <input class="form-control" type="text" name="pw" /> 
					<input class="btn btn-primary" type="submit" value="회원가입" />
				<a class="btn btn-success" href="/">로그인 페이지</a>
			</div>
		</form>

		<c:if test="${!empty param.existIdErr}">
			<script>
				alert("존재하는 아이디")
			</script>
		</c:if>

		
	</div>
</body>
</html>