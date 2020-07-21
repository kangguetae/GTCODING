<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>

<title>Home</title>
</head>
<body>
	<h1>로그인 페이지</h1>
	
	<P>The time on the server is ${serverTime}.</P>

	<form method="post" action="/">
		id <input type="text" name="id" /><br>
		pw <input type="password" name="pw" />
		<input type="submit" value="로그인">
	</form>
	  
	
	<c:if test="${!empty param.pwErr}" >
		<script>
		alert("비밀번호X")
		</script>
	</c:if>
	
	
	<c:if test="${!empty param.idErr}" >
		<script>
			alert("아이디X")
		</script>
	</c:if>
	
	
	
	<p>
		<a href="/user/signUp">회원가입</a>
		
	</p>
	<ul>
		<li>
			<a href="/board/list">목록</a>
		</li>
		
		<li>
			<a href="/board/listPage?currentPage=1">목록(페이징)</a>
		</li>
	</ul>

	
</body>
</html>
