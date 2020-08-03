<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>

<title>Home</title>
<link rel="stylesheet" href="../resources/css/bootstrap.css">
</head>
<body>

	<div class="container">
		<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3>로그인 페이지</h3>
				</div>
				<P>The time on the server is ${serverTime}.</P>


				<form method="post" action="/">
					<div>
						id <input class="form-control" type="text" name="id" />
					</div>
					<br>
					<div>
						pw <input class="form-control" type="password" name="pw" />
					</div>
					<div>
						<input class="form-control btn btn-primary" type="submit"
							value="로그인">
					</div>
				</form>

			</div>
		</div>


		<p>
			<a href="/user/signUp"><button class="btn btn-success">회원가입</button></a>
		</p>
		
		
		<!-- <ul>
			<li><a href="/board/list">목록</a></li>

			<li><a href="/board/listPage?currentPage=1">목록(페이징)</a></li>
		</ul>
 -->
	</div>

	<c:if test="${!empty param.pwErr}">
		<script>
			alert("비밀번호X")
		</script>
	</c:if>


	<c:if test="${!empty param.idErr}">
		<script>
			alert("아이디X")
		</script>
	</c:if>






</body>
</html>
