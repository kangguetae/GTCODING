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
						*5글자 이상의 아이디:<input class="form-control" type="text" name="id" /> 
						*4글자 이상의 비밀번호: <input class="form-control" type="text" name="pw" /> 
						닉네임: <input class="form-control" type="text" name="nickname" />
							<input class="btn btn-primary" type="submit" value="회원가입" /> 
							<a class="btn btn-success" href="/">로그인 페이지</a>
					</div>
				</form>
				*필수
				<c:if test="${!empty param.existIdErr}">
					<script>
						alert("존재하는 아이디입니다.")
					</script>
				</c:if>
				<c:if test="${!empty param.blankErr}">
					<script>
						alert("회원가입할 아이디와 비밀번호를 입력해 주십시오.")
					</script>
				</c:if>
				<c:if test="${!empty param.shortIdErr}">
					<script>
						alert("아이디가 너무 짧습니다.")
					</script>
				</c:if>
				<c:if test="${!empty param.shortPwErr}">
					<script>
						alert("비밀번호가 너무 짧습니다.")
					</script>
				</c:if>
				<c:if test="${!empty param.invalidIDErr}">
					<script>
						alert("올바르지 않은 아이디 혹은 비밀번호를 입력하였습니다.(한글 혹은 특수문자 포함)")
					</script>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>