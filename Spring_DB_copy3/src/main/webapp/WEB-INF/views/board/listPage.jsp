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
<script src="//code.jquery.com/jQuery-3.5.1.min.js"></script>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
<script type="text/javascript">
$(document).ready(function() {
	$("button").click(function() {
		var genre = $(this).attr("id");
		var c = "#" + genre;
		var genreData = {
				"genre" : genre,
				"currentPage" : "1"
			};
		$.ajax({
			 type : "POST"
			,url : "/board/chooseGenre"
			,data : genreData
			,dataType : "json"
			,success : function(data) { 
				if($(c).attr("class") == "btn btn"){
					$(c).attr("class", "btn btn-primary");
					$(c).siblings().attr("class", "btn btn"); // 장르정하면 다른 칸 선택취소
				 }
				 else{
					$(c).attr("class", "btn btn");
				 }
				//if(genre!="total"){
				var results = data.list;
				var endPage = data.endPage;
				var lastPage = data.lastPage;
				
				$(".cc").siblings().remove();
				$.each(results, function(i){
					var v = results[i]; // clone
					var tr = $("#aa").children();
					$(tr).append("<tr></tr>");
					var td = $("#aa").children().children().last();

					$(td).append($("<td/>",{
						 text:v.writer
					}));
					$(td).append("<td/>")
				
					$(td).children().last().append($("<a/>",{
						 text:v.title
						,href:"/board/view/?bno="+v.bno 
					}));
					$(td).append($("<td/>",{
						 text:v.regDate
					}));
					$(td).append($("<td/>",{
						 text:v.viewCnt
					}));
					$(td).append($("<td/>",{
						 text:v.recommCnt
					}));
				});  // 다 삭제하고 만들기 
				$(".pagination").children().siblings().remove();
				for(var i=1; i<=endPage; i++){
					$(".pagination").append("<li/>").children().addClass("page-item");
					var liChi = $(".pagination").children().last();
					if(i == 1){
						$(liChi).append("<a class='page-link active'/>");
						$(liChi).children().append($("<b/>",{
							 text:i
						}));
					}
					else{
						$(liChi).append($("<a/>",{
							 href:"/board/listPage?genre="+genre+"&currentPage="+i
							,text:i
						}));
					}
//  http://localhost:8080/board/listPage?genre=announcement&currentPage=2
				}
				if(lastPage>10){
					$(".pagination").append("<li/>").children().addClass("page-item");
					$(".pagination").children().last().append($("<a/>",{
						 href:"/board/listPage?genre="+genre+"&currentPage=11"
						,text:"다음"
					}));
				}
				$(".page-item").children().addClass("page-link");
				$()
				//}
			},
			error : function() {
				alert("error");
			}
		});
	});
}); 
</script>
</head>

<body>
	<div class="container">
		<h3>list page</h3>

		<div id="nav">
			<%@ include file="../include/nav.jsp"%>
		</div>
		<br>
		<div>
			<button class="btn btn" id="total">전체</button>
			<button class="btn btn" id="announcement">공지</button>
			<button class="btn btn" id="chat">잡담</button>
			<button class="btn btn" id="question">질문</button>
		</div> 
		<!-- <ul class="nav nav-pills">
			<li class="&{btnClick};">
				<a class="page-link" href="/board/listPage?currentPage=1">전체</a>
			</li>
			<li class="page-item">
				<a class="page-link" href="/board/listPage?genre=announcement&currentPage=1">공지</a></li>
			<li class="page-item">
				<a class="page-link" href="/board/listPage?genre=chat&currentPage=1">잡담</a>
			</li>
			<li class="page-item">
				<a class="page-link" href="/board/listPage?genre=question&currentPage=1">질문</a>
			</li>
		</ul> -->



		<div class="text-center">
			<table id = "aa" class="table table-striped">
				<tr class="cc">
					<th>글쓴이</th>
					<th>제목</th>
					<th>날짜</th>
					<th>조회수</th>
					<th>추천수</th>
				</tr>
				
				<c:forEach var="list" items="${listPage}">
					<tr>
						<td>${list.writer}</td>
						<td>
							<a href="/board/view/?bno=${list.bno}">
								<c:out value="${list.title}" escapeXml="true"/>
								<c:if test="${list.commCnt != 0}">
									(${list.commCnt})
								</c:if>
							</a>
						</td>
						<td>${list.regDate}</td>
						<td>${list.viewCnt}</td>
						<td>${list.recommCnt}</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<!-- 글 장르가 정해진경우 -->

		<c:if test="${genre != null}">
			<div class="row justify-content-center">
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
									<li class="page-item active"><a class="page-link"><b> ${page}</b></a></li>
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
			</div>
		</c:if>



		<!-- 글 장르가 정해지지 않은 경우 -->

		<c:if test="${genre == null}">
			<div class="row justify-content-center">
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
									<li class="page-item"><a class="page-link"><b>${page}</b></a></li>
								</c:if>
							</c:forEach>
							<c:if test="${startNum+10<=lastPage }">
								<li class="page-item"><a class="page-link"
									href="/board/listPage?currentPage=${startNum+10}">다음</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</c:if>

		<div class="row justify-content-center">
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
		<br>
	</div>
</body>
</html>