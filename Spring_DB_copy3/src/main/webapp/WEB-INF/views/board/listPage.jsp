<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
#selectPage {
	text-align: center;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
<body>
	<div class="container">
		<h3>리스트 페이징</h3>

		<div id="nav">
			<%@ include file="../include/nav.jsp"%>
		</div>

		<br>

		<div id="nav_genre">
			<%@ include file="../include/nav_genre.jsp"%>
		</div>



		<div class="text-center">
			<table class="table table-striped">
				<tr>
					<th>writer</th>
					<th>title</th>
					<th>regDate</th>
					<th>viewCnt</th>

				</tr>
				<c:forEach var="list" items="${listPage}">
					<tr>
						<td>${list.writer}</td>
						<td><a href="/board/view/?bno=${list.bno}">${list.title}
								<c:if test="${list.commCnt != 0}">
							(${list.commCnt})
						</c:if>
						</a></td>
						<td>${list.regDate}</td>
						<td>${list.viewCnt}</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<!-- 글 장르가 정해진경우 -->

		<c:if test="${genre != null}">
			<div class="text-center" id="selectPage">
				<div class="col-md-offset-3">
					<ul class="pagination">
						<c:if test="${startNum >= 11}">
							<li class="page-item"><a class="page-link"
								href="/board/listPage?genre=${genre}&currentPage=${startNum-10}">이전</a>
							</li>
						</c:if>

						<c:forEach var="page" begin="${startNum}" end="${endNum}">

							<c:if test="${page ne currentPage}">
								<li class="page-item"><a class="page-link"
									href="/board/listPage?genre=${genre}&currentPage=${page}">${page}</a>
								</li>
							</c:if>
							<c:if test="${page eq currentPage}">
								<li class="page-item"><a class="page-link">${page}</a></li>
							</c:if>

						</c:forEach>

						<c:if test="${startNum+10<=lastPage}">
							<li class="page-item"><a class="page-link"
								href="/board/listPage?genre=${genre}&currentPage=${startNum+10}">다음</a>
							</li>
						</c:if>
					</ul>
				</div>

				<br>
			</div>
		</c:if>



		<!-- 글 장르가 정해지지 않은 경우 -->

		<c:if test="${genre == null}">
			<div class="text-center" id="selectPage">
				<div class="col-md-offset-3">
					<ul class="pagination pg-blue">
						<c:if test="${startNum >= 11}">
							<li class="page-item"><a class="page-link"
								href="/board/listPage?currentPage=${startNum-10}">이전</a></li>
						</c:if>

						<c:forEach var="page" begin="${startNum}" end="${endNum}">

							<c:if test="${page ne currentPage}">
								<li class="page-item"><a class="page-link"
									href="/board/listPage?currentPage=${page}">${page}</a></li>
							</c:if>
							<c:if test="${page eq currentPage}">
								<li class="page-item"><a class="page-link">${page}</a></li>
							</c:if>
						</c:forEach>
						<c:if test="${startNum+10<=lastPage }">
							<li class="page-item"><a class="page-link"
								href="/board/listPage?currentPage=${startNum+10}">다음</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</c:if>



		<div class="search row">
			<form method="GET" action="/board/search">
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
		
	</div>
</body>
</html>