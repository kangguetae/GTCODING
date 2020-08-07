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
		<div class="row justify-content-center">
			<div class="col-md-8 col-md-offset-3 col-sm-8 col-sm-offset-2">
				<h3>회원가입 페이지</h3>
				<form method="post">
					<div class="form-group">
						아이디: <input class="form-control" type="text" name="id" /> 
						비밀번호: <input class="form-control" type="text" name="pw" /> 
						닉네임: <input class="form-control" type="text" name="nickname" />
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
		</div>
	</div>
</body>
</html>