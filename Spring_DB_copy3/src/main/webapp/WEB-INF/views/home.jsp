<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<title>Home</title>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
</head>
<body>

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8 col-md-offset-3 col-sm-8 col-sm-offset-2">
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3>로그인 페이지</h3>
					</div>
					<P>The time on the server is ${serverTime}.</P>

					<form method="post" action="/">
						<div>
							id <input class="form-control" type="text" name="id" value="${vo.id}"/>
						</div>
						<div>
							pw <input class="form-control" type="password" name="pw" value="${vo.pw}" />
						</div>
						<div>
							<input class="form-control btn btn-primary" type="submit"
								value="로그인">
						</div>
						
						<label for="automaticLogin">
						<c:if test="${checked}">
							<input id="automaticLogin" type="checkbox" name="check" value="auto" checked>
						</c:if>
						<c:if test="${!checked}">
							<input id="automaticLogin" type="checkbox" name="check" value="auto">
						</c:if>
						
						아이디, 비밀번호 기억 </label>
					</form>
					
					
					<a href="/user/signUp"><button class="btn btn-success">회원가입</button></a>
				</div>
			</div>
		</div>
		
		
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
