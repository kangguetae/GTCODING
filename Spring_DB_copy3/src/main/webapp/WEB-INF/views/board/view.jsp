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
<script type="text/javascript">
window.onload = function(){
	var ab = document.getElementById('aaa').cloneNode();
	
	var aaa = document.getElementById('aaa');
	aaa.addEventListener('click', function(){
			alert('aaa');
		});
}
	function modify(cno){
		var aaaa = document.getElementById("commentInput_"+cno);
		var form = document.createElement('form');
		
		var ip= document.createElement('input');
		ip.setAttribute("type", "text");
		
		
		
		/* input.setAttribute("type", "text");
		input.setAttribute("name", modComm); */
		
		
		form.appendChild(ip);
		aaaa.appendChild(form);
	}
	function delete_button(cno){
		alert("삭제버튼");
	}
</script>
</head>
<body>
	<h1>view</h1>
	<div id="nav">
		<%@ include file="../include/nav.jsp"%>
	</div>
		
		<label>title: </label> 
		${view.title}
		<label> writer: </label> 
		${view.writer}<br>
		<label> content: </label>
		${view.content}<br><br>
		
	
	<a href="/board/delete/?bno=${view.bno}">게시물 삭제</a> <!-- 페이지 이동X 해당 페이지로의 요청 -->
	<a href="/board/modify/?bno=${view.bno}">게시물 수정</a>
	<!-- <button id="aaa">aaa</button> -->
	
	<br><br><label>댓글</label><br>
	<input type="text" style="display:none;">
	<c:forEach var="commentList" items="${comment}">
		<div id="comment_${commentList.cno}">
			<div>${commentList.comm}</div> 
			<c:forEach begin="0" end="25" >
				&nbsp
			</c:forEach>
			<button id="modifyComment_${commentList.cno}" onclick="modify(${commentList.cno});">수정</button>
			<button id="deleteComment_${commentList.cno}" onclick="delete_button(${commentList.cno});">삭제</button>
			<br>
			<div id="commentInput_${commentList.cno}">
			
			</div>
		</div>
	</c:forEach>
	
	
	<form method="POST">
		<textarea rows="2" cols="40" name="comm"  placeholder = "댓글"></textarea>
		<input type="hidden" name = "bno" value="${view.bno}"/>
		<input type="submit" value="댓글작성" />
	</form>
</body>
</html>