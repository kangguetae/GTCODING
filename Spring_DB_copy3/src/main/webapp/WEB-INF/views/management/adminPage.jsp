<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	button{
		align: center;
	}
</style>
<link rel="stylesheet" href="../resources/css/bootstrap.css">

</head>
<body>

	<div class="container">
		<h3>adminPage</h3>
		<div id="nav">
			<%@ include file="../include/nav.jsp"%>
		</div>

		
		<c:if test="${status ge 1}">
			매니저부터
			<form method="POST" action="addGenre" onsubmit="return confirm('정말 추가하시겠습니까?')"> 
				<div class="form-group">
					장르(영):<input class="form-control" size="10" maxlength="20" type="text" name="genreEng" />
					장르(한):<input class="form-control" size="10" maxlength="4"  type="text" name="genreKor" />
					<button class="btn btn-primary">장르추가</button>
				</div>
			</form>
			
			<br>
			
			<form method="POST" action="selectGenre" onsubmit="return confirm('재밌겠네 진행해봐')">
			표시할 장르 선택<br>
				<c:forEach var="list" items="${genreList}">
					<%-- <c:if test="${list.genreEng ne 'announcement'}"> --%>
						<c:if test="${list.selected eq 1}">
							<label><input type="checkbox" name="genres" value="${list.genreEng}" checked/>${list.genreKor}</label>
						</c:if>
						<c:if test="${list.selected ne 1}">
							<label><input type="checkbox" name="genres" value="${list.genreEng}"/>${list.genreKor}</label>
						</c:if>
					<%-- </c:if> --%>
				</c:forEach>
				<br>
				<button class="btn btn-primary">결정</button>
					<!-- <input type="reset" value="초기화"/> -->
			</form>
			
			
			
			<br><br><br>
			<form method="POST" action="deleteGenre" onsubmit="return confirm('정말 삭제하시겠습니까?')">
				<select class="form-control" name="genreDelete" size="1">
					<c:forEach var="list" items="${genreList}">
						<c:if test="${list.genreEng ne 'announcement'}">
							<option value="${list.genreEng}">${list.genreKor}</option>
						</c:if>
					</c:forEach>
				</select>
				<button id="g" class="btn btn-danger">장르삭제</button>
			</form>
			
			<br><br>
			<c:if test="${status ge 2}">
				관리자부터
				<form method="POST" action="authority" onsubmit="return confirm('정말 등록하시겠습니까?')">
					<select class="form-control" name="user" size="1">
						<c:forEach var="user" items="${list}">
							<option value="${user}">${user}</option>
						</c:forEach>
					</select>
				 	<button class="btn btn-primary">매니저등록</button>
				</form>
				
				<br>
				
				<form method="POST" action="deprivation"  onsubmit="return confirm('정말 진행하시겠습니까?')">
					<select class="form-control" name="manager" size="1">
						<c:forEach var="manager" items="${managerList}">
							<option value="${manager}">${manager}</option>
						</c:forEach>
					</select>
					<button class="btn btn-danger">권한박탈</button>
				</form>
			</c:if>
			
			
			
		</c:if>
	</div>
	
	
	<c:if test="${!empty param.genreNull}">
		<script>
			alert("추가할 장르를 전부 입력해주세요")
		</script>
	</c:if>
	
	<c:if test="${!empty param.nullSelect}">
		<script>
			alert("1개 이상의 장르를 선택해주세요.")
		</script>
	</c:if>
	
</body>
</html>