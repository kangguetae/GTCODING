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


	회원가입 페이지
	<form method="post">
		<input type="text" name="id" />
		<input type="text" name="pw" />
		<input type="submit" value="회원가입"/>
	</form>
	
	<c:if test="${!empty param.existIdErr}">
		<script>
			alert("존재하는 아이디")
		</script>
	</c:if>
	
	<a href="/">로그인 페이지</a>
	
</body>
</html>