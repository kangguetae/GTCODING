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
	$("button").click(func);
}); 
//string.includes("")
//string.replace("전","후")

// 같은 장르 버튼 눌러서 취소할때 리스트 안나오는거 여기 페이지에서 수정
var keepingGenre = ""; // 장르저장 input hidden -> 데이터저장용
var keepingPage;
var t = "";
var count = 0;
<c:forEach var="list" items="${genreList}">
	t = t + "${list.genreEng}" + "$";
</c:forEach>

function func(e) {
	var genre = $(this).attr("id");
	var page = $(this).text();
	var tt = t.split("$");
	
	tt.splice(tt.length-1);
	if(page == "다음"){
		page = keepingPage + 10;
	}
	else if(page == "이전"){
		page = keepingPage - 10;
	}
	else if(!$.isNumeric(page)){ // 페이지 눌렀는지
		page = 1;	
	}

	
	if(genre !== undefined){	 // 장르선택하면 저장
		var fst = genre;
		var next = genre+"#";
		
		if(genre == "total"){
			keepingGenre = "total#";
		}
		else{
			if(keepingGenre.includes(next)){
				keepingGenre = keepingGenre.replace(/total#/g , "");
				keepingGenre = keepingGenre.replace(genre+'#', "")
				
				for(var i in tt){
					if(!keepingGenre.includes(tt[i])){
						count = count + 1;
					}
				}
			}
			else{
				keepingGenre += next;
				for(var i in tt){
					if(keepingGenre.includes(tt[i])){
						count = count + 1;
					}
				}
			}
			if(count == tt.length){
				keepingGenre = "total#";
			}
		}
	}
	count = 0;
	var genreData = {
			"genre" : keepingGenre,
			"currentPage" : page
	};
	var c = "#" + genre;
	
	$.ajax({
		 type : "POST"
		,url : "/board/chooseGenre"
		,data : genreData
		,dataType : "json"
		,success : function(data) { 
			
			if(keepingGenre == "total#"){
				//$("#total").attr("class", "btn btn-primary");
				$("#total").removeClass("btn btn-light").addClass("btn btn-primary");
				//$("#total").siblings().attr("class", "btn btn-light");
				$("#total").siblings().removeClass().addClass("btn btn-light");
			}
			else{
				$("#total").attr("class", "btn btn-light");
				if($(c).attr("class") == "btn btn-light"){
					$(c).attr("class", "btn btn-primary");
					//$$  removeClass(이것만 뽑아쓰기)    attr(부시고 새로짓는느낌?)
					 // 장르정하면 다른 칸 선택취소
				 }
				 else{
					$(c).attr("class", "btn btn-light");
				 }
			}
			var results = data.list;
			var endPage = data.endPage;
			var lastPage = data.lastPage;
			var currentPage = data.currentPage;
			var startPage = data.startPage;
			keepingPage = startPage;
			
			
			$(".cc").siblings().remove();
			$.each(results, function(i){
				var v = results[i]; // clone
				var tr = $("#aa").children();
				$(tr).append("<tr></tr>");
				var td = $("#aa").children().children().last();
				var commCnt;
				
				if(v.commCnt == 0){
					commCnt = "";
				}
				else{
					commCnt = " ("+v.commCnt+")";
				}
				
				$(td).append($("<td/>",{
					 text:v.writer
				}));
				$(td).append("<td/>")
			
				$(td).children().last().append($("<a/>",{
					 text:v.title+commCnt
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

			$(".pagination").children().remove();
			if(startPage >= 11){
				$(".pagination").append("<li/>").children().addClass("page-item");
				$(".pagination").children().last().append($("<a/>",{
					text:"이전"
				}));
			}

			// 아래 페이지 버튼 
			
			for(var i=startPage; i<=endPage; i++){
				
				if(i == currentPage) {
					$(".pagination").append("<li/>").children().last().addClass("page-item active");
				}
				else {
					$(".pagination").append("<li/>").children().addClass("page-item");
				}
				var liChi = $(".pagination").children().last();
				if(i == currentPage){
					$(liChi).append("<a class='page-link'/>");
					$(liChi).children().append($("<b/>",{
						 text:i
					}));
				}
				else{
					$(liChi).append($("<a/>",{
						text:i
					}));
				}

			}
			
			if(lastPage>=startPage+10){
				$(".pagination").append("<li/>").children().addClass("page-item");
				$(".pagination").children().last().append($("<a/>",{
					text:"다음"
				}));
			}
			$(".page-item").children().addClass("page-link");
			$(".page-link").click(func);
		}

		,error : function() {
			alert("error");
		}
	});
}
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
		<button class="btn btn-primary" id="total">전체</button>
			<c:forEach var="list" items="${genreList}">
					<button class="btn btn-light" id="${list.genreEng}">${list.genreKor}</button>
			</c:forEach>	
		</div> 
		

		<div class="text-center">
			<table id = "aa" class="table table-hover">
			<!-- <thead> -->
				<tr class="cc">
					<th>글쓴이</th>
					<th>제목</th>
					<th>날짜</th>
					<th>조회수</th>
					<th>추천수</th>
				</tr>
				<!-- </thead> -->
				<!-- <tbody> -->
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
				<!-- </tbody> -->
			</table>
		</div>

		

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
	
	<c:if test="${!empty param.invalidAccess}">
		<script>
			alert("잘못된 접근입니다.");
		</script>
	</c:if>
	
	
</body>
</html>