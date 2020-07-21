<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
</style>
<script src="//code.jquery.com/jQuery-3.5.1.min.js"></script>

<script type="text/javascript">
	/* jQuery(document).ready(function($){
	 $('body').prepend('<h1>Hello</h1>');
	 }); */

	/* window.onload = function(){
	 var ab = document.getElementById('aaa').cloneNode();
	
	 var aaa = document.getElementById('aaa');
	 aaa.addEventListener('click', function(){
	 alert('aaa');
	 });
	 }
	 */
	window.onload = function() {
		var modify = document.getElementsByClassName("modifyBtn");
		var cancelBtn = document.getElementsByClassName("commentModify_cancelBtn");
		var deleteBtn = document.getElementsByClassName("deleteBtn");
		
		for (var i = 0; i < modify.length; i++) {
			modify[i].addEventListener("click", function() {			
				console.log("수정버튼");
				$(this).parent().css("display", "none");
				$(this).parent().next().css("display", "block");
				console.log($(this).parent().parent().attr("id"));
				console.log($(this).parent().prev().html());
			});
		}

		for(var i = 0; i < cancelBtn.length; i++){
			cancelBtn[i].addEventListener("click", function(){
				console.log("취소버튼");
				$(this).parent().parent().prev().css("display", "block");
				$(this).parent().parent().css("display", "none");
			});
		}

		for(var i = 0; i < deleteBtn.length; i++){
			deleteBtn[i].addEventListener("click", function(){
				console.log("삭제버튼");
			});
		}

		
	}

		
	 function login_require(){
			alert("로그인이 필요한 서비스입니다.");
		}
</script>
<style>
	
</style>
</head>
<body>
	<h1>view</h1>
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>

	<label>title: </label> ${view.title}
	<label> writer: </label> ${view.writer}
	<br>
	<label> content: </label> ${view.content}
	<br>
	<br>
	<c:if test="${isLogin}">
		<c:if test="${!alreadyParticipated}">
		<form method="post" action="/board/likeOrDislike">
			<input type="hidden" value="${view.bno}" name="bNumber" />
			<input type="submit" name="likeOrDislike" value="1" />: [${countLike}]
			<input type="submit" name="likeOrDislike" value="0" />: [${countDislike}]
		</form>
		</c:if>
		<c:if test="${alreadyParticipated}">
			
			<button onclick="alert('이미 참여하였습니다.')">like</button>: [${countLike}]
			<button onclick="alert('이미 참여하였습니다.')">dislike</button>: [${countDislike}]
			<br>
		</c:if>
	</c:if>
	<c:if test="${!isLogin}">
			<button onclick="login_require()">like</button>: [${countLike}]
			<button onclick="login_require()">dislike</button>: [${countDislike}]
		
	</c:if>
	
	
	<c:if test="${userId == view.writer}">
		<a href="/board/delete/?bno=${view.bno}">게시물 삭제</a>
		<!-- 페이지 이동X 해당 페이지로의 요청 -->
		<a href="/board/modify/?bno=${view.bno}">게시물 수정</a>
		<!-- <button id="aaa">aaa</button> -->
	</c:if>
	
	<br>
	<br>
	<label>댓글</label>
	<br>
	<input type="text" style="display: none;">
	<c:forEach var="commentList" items="${comment}">
		<div id="${commentList.cno}">
			<div>${commentList.comm}</div>
			<%-- <c:forEach begin="0" end="25">
				&nbsp
			</c:forEach> --%> <!--spring web-session  -->
			
			<%--
			<div class="hide" style="display:block;">
				<button class="modifyBtn">수정</button>
				<button class="deleteBtn">삭제</button>
				<br>
				<div class="formInput"></div>
			</div>
			 <div class="hide" style="display:none;">
				<form method="POST">
					<input type="submit" name="commentModify_btn" value="수정"/>
					<input type="button" class="commentModify_cancelBtn" value="취소"/>
					<input type="text" name="commentModify" value="${commentList.comm}" />
					<input type="hidden" name="cno" value="${commentList.cno}" />
				</form>
			</div> --%>
		</div>
		<br>
	</c:forEach>


	<form method="POST">
		<c:if test="${isLogin}">
		<textarea rows="2" cols="40" name="comm" placeholder="댓글"></textarea>
		<input type="hidden" name="bno" value="${view.bno}" /> <input
			type="submit" value="댓글작성" />
		</c:if>
		<c:if test="${!isLogin}">
		<textarea rows="2" cols="40" name="comm" placeholder="로그인이 필요한 서비스입니다." readOnly></textarea>
		</c:if>
	</form>
</body>
</html>