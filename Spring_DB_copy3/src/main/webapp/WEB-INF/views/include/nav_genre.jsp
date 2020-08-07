<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="//code.jquery.com/jQuery-3.5.1.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					if(genre!="total"){
					var results = data;
					$(".cc").siblings().remove();
					$.each(results, function(i){
						var v = results[i];
						$("#aa").append("<tr></tr>").children().append("<td>"+v.writer+"</td>"+"<td>"+"<a href='/board/view/?bno="+v.bno+"'>"+v.title+"</a>"+"</td>"+"<td>"+v.regDate+"</td>"+"<td>"+v.viewCnt+"</td>"+"<td>"+v.recommCnt+"</td>");
					});  // 다 삭제하고 만들기 

					}
					
				},
				error : function() {
					/* $.each(data, fuction(idx, val){
						console.log(idx+" "+val.title)
					}); */
						
					
					alert("error");
				}
			});
			
		});
	}); 
	



</script>
 <div>
	 <button class="btn btn" id="announcement">공지</button>
	 <button class="btn btn" id="chat">잡담</button>
	 <button class="btn btn" id="question">질문</button>
	 <button class="btn btn " id="total"><a href="/board/listPage?currentPage=1">전체</a></button>
 </div>
 
 <!-- form태그로 바꿀까 개빡치네ㄹㅇㅋㅋ    /  아니면 button으로 바꿔서 data로 genre를 넣으면 되지 않을까? 재밌네 진행해봐 ㅅㅄㅂ -->
<ul class="nav nav-pills">
	<li class="page-item">
		<a class="page-link" href="/board/listPage?genre=announcement&currentPage=1">공지</a></li>
	<li class="page-item">
		<a class="page-link" href="/board/listPage?genre=chat&currentPage=1">잡담</a>
	</li>
	<li class="page-item">
		<a class="page-link" href="/board/listPage?genre=question&currentPage=1">질문</a>
	</li>
	<li class="&{btnClick};">
		<a class="page-link" href="/board/listPage?currentPage=1">전체</a>
	</li>
</ul>
