<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
<style>
</style>
<script src="//code.jquery.com/jQuery-3.5.1.min.js"></script>
<script type="text/javascript">
	var dislikeBtnData = {
		"uno" : '${check.uno}',
		"bno" : '${bno}',
		"isLike" : "0"
	};
	var likeBtnData = {
		"uno" : '${check.uno}',
		"bno" : '${bno}',
		"isLike" : "1"
	};
	
	$(document).ready(function(){
		// 댓글 삭제	
		$(".replyUpdate.btn.btn-danger.btn-xs").click(function() {

			if(confirm("정말 삭제하시겠습니까?")){
				var commentDeleteData = {
					"cno" : $(this).val()
					};
				$(this).parent().remove();
				$.ajax({
					 type : "POST"
					,url : "/board/commentDelete"
					,data : commentDeleteData
					
				});
			}
		});
		
		// 댓글 수정시작
		$(".replyUpdate.btn.btn-warning.btn-xs").click(function() {
			$(this).siblings(".comm1").css("display" , "none");
			$(this).siblings(".comm2").css("display" , "");

			$(this).css("display", "none");
			$(this).siblings(".replyUpdate.btn.btn-danger.btn-xs").css("display", "none");
			$(this).siblings(".replyUpdate.btn.btn-success.btn-xs").css("display", "");
			$(this).siblings(".replyUpdate.btn.btn-info.btn-xs").css("display", "");
		});

		// 댓글 수정 취소
		$(".replyUpdate.btn.btn-info.btn-xs").click(function() {
			$(this).siblings(".comm1").css("display" , "");
			$(this).siblings(".comm2").css("display" , "none");

			$(this).css("display", "none");
			$(this).siblings(".replyUpdate.btn.btn-success.btn-xs").css("display", "none");
			$(this).siblings(".replyUpdate.btn.btn-warning.btn-xs").css("display", "");
			$(this).siblings(".replyUpdate.btn.btn-danger.btn-xs").css("display", "");
		});

		//댓글 수정 확인
		$(".replyUpdate.btn.btn-success.btn-xs").click(function() {
			var content = $(this).siblings(".comm2").children(".modifyComment").val();
			$(this).siblings(".comm1").css("display" , "").text(content);
			$(this).siblings(".comm2").css("display" , "none");

			$(this).css("display", "none");
			$(this).siblings(".replyUpdate.btn.btn-info.btn-xs").css("display", "none");
			$(this).siblings(".replyUpdate.btn.btn-warning.btn-xs").css("display", "");
			$(this).siblings(".replyUpdate.btn.btn-danger.btn-xs").css("display", ""); 
			var commentModifyData = {
					"cno" : $(this).val(),
					"content" : content
			}; 
			if(confirm("수정하시겠습니까?")){
				$.ajax({
					 type : "POST"
					,url : "/board/commentModify"
					,data : commentModifyData
				});
			}
		});

		//좋아요, 싫어요 버튼
		$("#like").click(function() {
			$.ajax({
				type : "POST",
				url : "/board/likeOrDislike",
				data : likeBtnData, //다수의 데이터를 받아와야 한다면 객체를 데이터로 받아온다.
				success : function(result) { // result --> 접근하는 controller가 return하는 값을 result로 받아온다. 
					var content = ": [" + result + "]";
					$("#cntLike").text(content);
				},
				error : function() {
					alert("이미 참여");
				}
			});
		});

		$("#dislike").click(function() {
			$.ajax({
				type : "POST",
				url : "/board/likeOrDislike",
				data : dislikeBtnData,
				success : function(result) { 
					var content = ": [" + result + "]";
					$("#cntDislike").text(content);
				},
				error : function() {
					alert("이미 참여");
				}
			});
		});

		//첨부파일 이미지 표출
		var t;
		$(".attachment").mouseenter(function(){
			t = $(this).text();
			var fno = $(this).attr("id");
			$(this).next().css("visibility", "visible");
		});
		$(".attachment").mouseleave(function(){
			$(this).next().css("visibility", "hidden")
		});
	});

	function login_require() {
		alert("로그인이 필요한 서비스입니다.");
	}
</script>
<!-- <style>
button.like-button, button.dislike-button {
	background-color: white;
}
</style> -->
</head>

<body>
	<div class="container">
		<h1>view</h1>
		<div id="nav">
			<%@ include file="../include/nav.jsp"%>
		</div>
		<div class="form-group">
			<label>title </label>
			<div class="form-control"><c:out value="${view.title}" escapeXml="true"/></div>
		</div>
		<div class="form-group">
			<label> writer </label>
			<div class="form-control">${view.writer}</div>
		</div>
		<div class="form-group">
			<label> content </label>
			<textarea class="form-control col-sm-12" rows="5" readOnly>${view.content}</textarea>
			<c:if test="${fileList.size() != 0}">
			<br> <label><b>첨부파일</b></label> <br>
			</c:if>
			<c:forEach var="file" items="${fileList}">
				<a class="attachment" id="${file.fno}" href="/board/fileDownload?fno=${file.fno}" download>
				${file.originalName}</a>
				<img style='visibility:hidden; position:absolute; left: 300px;' width='200' height='130' src='/board/getImage?filenumber=${file.fno}'/>
				<br>
			</c:forEach>
		</div>

		<c:if test="${isLogin}">
			<c:if test="${!alreadyParticipated}">
				<button class="like-button" id="like">
					<!-- <img src="/resources/Image/like.jpg"> -->
					like
				</button>
				<div style="display: inline" id="cntLike">: [${countLike}]</div>
				<button class="dislike-button" id="dislike">dislike</button>
				<div style="display: inline" id="cntDislike">:
					[${countDislike}]</div>
			</c:if>
			<c:if test="${alreadyParticipated}">
				<button class="like-button" onclick="alert('이미 참여하였습니다.')">like</button> : [${countLike}]
			<button class="dislike-button" onclick="alert('이미 참여하였습니다.')">dislike</button> : [${countDislike}]
		</c:if>
		</c:if>
		<c:if test="${!isLogin}">
			<button onclick="login_require()">like</button>: [${countLike}]
			<button onclick="login_require()">dislike</button>: [${countDislike}]
		</c:if>
		<br>
		<c:if test="${userId == view.writer or status>=1}">
			<br>
			<a class="btn btn-warning" href="/board/modify/?bno=${view.bno}">게시물
				수정</a>
			<a onclick="return confirm('정말 삭제하시겠습니까?')" class="btn btn-danger" href="/board/delete/?bno=${view.bno}">게시물
				삭제</a>
			<!-- 페이지 이동X 해당 페이지로의 요청 -->
			
		</c:if>
		<br> <br>

		<form method="POST">
			<c:if test="${isLogin}">
				<div class="form-group">
					<input type="hidden" name="writer" value="${check.id}" />
					<label><input type="checkbox" name="isSecret" value="1"/>비밀댓글</label>
					<textarea class="form-control" rows="2" cols="40" name="comm"
						placeholder="댓글"></textarea>
					<input type="hidden" name="bno" value="${view.bno}" /> <input
						class="form-control blue" type="submit" value="댓글작성" />
				</div>
			</c:if>
			<c:if test="${!isLogin}">
				<textarea rows="2" cols="40" name="comm"
					placeholder="로그인이 필요한 서비스입니다." readOnly></textarea>
				<button onclick="alert('로그인이 필요한 서비스입니다.')">댓글작성</button>
			</c:if>
		</form>
		
		<div class="col-xs-12">
			<c:forEach var="comment" items="${commentList}">
				<div>	
					<span class="glyphicon glyphicon-user"></span>
					<b>${comment.writer}</b>
					<br>
					<div class="comm1" style="display:">
						<c:if test="${comment.secret eq 0 }">
							<c:out value="${comment.comm}" escapeXml="true" />
						</c:if>
						<c:if test="${comment.secret eq 1}">
							<c:choose>
								<c:when test="${comment.writer eq check.id or check.status eq 'admin' or check.status eq 'manager'}">
									<c:out value="${comment.comm}" escapeXml="true" />
								</c:when>
								<c:otherwise>
									<p style="background-color:lightgrey">비밀 댓글입니다.</p>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<div class="comm2" style="display:none">
						<textarea class="modifyComment" cols="45">${comment.comm}</textarea>
					</div>
					
					<c:if test="${comment.writer eq check.id or check.status eq 'admin' or check.status eq 'manager'}">
						<c:if test="${check.status ne 'manager' or comment.writer ne 'admin'}">
							<button value="${comment.cno}" style="float: right ;" type="button" class="replyUpdate btn btn-danger btn-xs">삭제</button>				
							<button  style="float: right;" type="button" class="replyUpdate btn btn-warning btn-xs">수정</button>
							<button value="${comment.cno}" class="replyUpdate btn btn-success btn-xs" style="display:none; float:right;" >수정</button>
							<button class="replyUpdate btn btn-info btn-xs" style="display:none; float:right;">취소</button>
						</c:if>
					</c:if>
					<br>
					<hr>
				</div>
			</c:forEach> 
		</div>
	</div>
	<br><br>
</body>
</html>