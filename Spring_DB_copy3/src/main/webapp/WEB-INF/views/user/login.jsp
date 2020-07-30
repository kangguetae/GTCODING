<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jQuery-3.5.1.min.js"></script>

<style>
	.big{
		font-size:20px;
	}
	.small{
		font-size:8px;
	}
</style>
<script type="text/javascript">

	$(document).ready(function(){
		$(".big").click(function(){
	 
		});
	});
</script>
</head>
<body>
	<!-- 
	<button class="big">button 1</button>
	<button class="small">button 2</button>
	 -->






	로그인 되었습니다.
	
	<div id="nav">
		<%@ include file="../include/nav.jsp" %>
	</div>	
	<br>
	<div id="nav_genre">
		<%@ include file="../include/nav_genre.jsp"%>
	</div>
</body>
</html>