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
	$(document).ready(function() {
		$("#like").click(function() {
			$.ajax({
				type : "POST",
				url : "/board/likeOrDislike",
				data : likeBtnData, //다수의 데이터를 받아와야 한다면 객체를 데이터로 받아온다.
				success : function(result) { // result --> 접근하는 controller가 return하는 값을 result로 받아온다. 

					var content = ": [" + result + "]";
					$("#cntLike").text(content);
					/* "["+1+"]" */
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
				success : function(result) { // result --> 접근하는 controller가 return하는 값을 result로 받아온다. 

					var content = ": [" + result + "]";
					$("#cntDislike").text(content);
				},
				error : function() {
					alert("이미 참여");
				}
			});
		});

		var t;

		$(".attachment").mouseenter(function(){
			t = $(this).text();
			var fno = $(this).attr("id");
			
			//$(this).children().css("display", "inline");
			//$(this).append("<img style='position: absolute;' src='/board/getImage?filename=${fileList.get(0).getChangedName()}'/>");
			$(this).append("<img style='position: absolute;' src='/board/getImage?filenumber="+fno+"'/>");
		});
		$(".attachment").mouseleave(function(){
			$(this).text(t);
			
		});
		
		
	});


	
	window.onload = function() {
		
		var modify = document.getElementsByClassName("modifyBtn");
		var cancelBtn = document
				.getElementsByClassName("commentModify_cancelBtn");
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

		for (var i = 0; i < cancelBtn.length; i++) {
			cancelBtn[i].addEventListener("click", function() {
				console.log("취소버튼");
				$(this).parent().parent().prev().css("display", "block");
				$(this).parent().parent().css("display", "none");
			});
		}

		for (var i = 0; i < deleteBtn.length; i++) {
			deleteBtn[i].addEventListener("click", function() {
				console.log("삭제버튼");
			});
		}


		
	}

	function login_require() {
		alert("로그인이 필요한 서비스입니다.");
	}


	
</script>
<style>
button.like-button, button.dislike-button {
	background-color: white;
}
</style>
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
		<c:if test="${userId == view.writer or isAdmin}">
			<br>
			<a class="btn btn-danger" href="/board/delete/?bno=${view.bno}">게시물
				삭제</a>
			<!-- 페이지 이동X 해당 페이지로의 요청 -->
			<a class="btn btn-warning" href="/board/modify/?bno=${view.bno}">게시물
				수정</a>
			<!-- <button id="aaa">aaa</button> -->
		</c:if>

		<br> <br> <label>댓글</label> <br> <input type="text"
			style="display: none;">
		<c:forEach var="commentList" items="${comment}">
			<div class="form-group" id="${commentList.cno}">
				<div>
					<c:out value="${commentList.comm}" escapeXml="true" />
				</div>
			</div>

		</c:forEach>


		<form method="POST">
			<c:if test="${isLogin}">
				<div class="form-group">
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
	</div>
</body>
</html>