<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../resources/css/bootstrap.css">
</head>
<body>
	<div class="container">
		<h3>search</h3>

		<div id="nav">
			<%@ include file="../include/nav.jsp"%>
		</div>
		<br>
		<div>
		<button class="btn" id="total"><a href="/board/listPage">돌아가기</button></a>
		<%-- 	<c:forEach var="list" items="${genreList}">
					<button class="btn btn-light" id="${list.genreEng}">${list.genreKor}</button>
			</c:forEach> --%>	
		</div> 
		
		
		
		<%-- <div id="nav_genre">
		<%@ include file="../include/nav_genre.jsp"%>
	</div> --%>
		<div class="text-center">
			<table class="table table-striped">

				<tr>
					<th>bno</th>
					<th>writer</th>
					<th>title</th>
					<th>regDate</th>
					<th>viewCnt</th>
				</tr>
				<c:forEach var="list" items="${listPage}">
					<tr>
						<td>${list.bno}</td>
						<td>${list.writer}</td>
						<td><a href="/board/view/?bno=${list.bno}"><c:out value="${list.title}" escapeXml="true" /></a></td>
						<td>${list.regDate}</td>
						<td>${list.viewCnt}</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<div class="row justify-content-center">
			<div class="text-center" id="selectPage">
				<div class="col-md-offset-3">
					<ul class="pagination">
						<c:if test="${startNum >= 11}">
							<li class="page-item"><a class="page-link"
								href="/board/search?search=${searchingWay}&contain=${searchWord}&currentPage=${startNum-10}">이전</a></li>
						</c:if>

						<c:forEach var="page" begin="${startNum}" end="${endNum}">
								${empty param.search ? 'listPage' : ''}
						<c:if test="${page ne currentPage}">
								<li class="page-item"><a class="page-link"
									href="/board/search?search=${searchingWay}&contain=${searchWord}&currentPage=${page}">${page}</a></li>
							</c:if>
							<c:if test="${page eq currentPage}">
								<li class="page-item"><a class="page-link">${page}</a></li>
							</c:if>

						</c:forEach>
						<c:if test="${startNum+10<=lastPage }">
							<li class="page-item"><a class="page-link"
								href="/board/search?search=${searchingWay}&contain=${searchWord}&currentPage=${startNum+10}">다음</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>


		<div class="row justify-content-center">
			<form method="GET">
				<div class="input-group">
					<div class="col-xs-4 col-sm-4">
						<select name="search" size="1" class="form-control">
							<option value="title" selected>제목</option>
							<option value="content">내용</option>
							<option value="titleAndContent">제목+내용</option>
						</select>
					</div>
					<div class="col-xs-10 col-sm-10">
						<div class="input-group">
							<input class="form-control" type="text" name="contain" /> <input
								class="input-group-btn" type="submit" value="검색">
						</div>
					</div>
				</div>
			</form>
		</div>
		<br>
	</div>
</body>
</html>