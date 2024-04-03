<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
int status = response.getStatus();
System.out.println(status + " 에러 발생");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="wrap">
	<jsp:include page="/header.jsp" />
	
	<!-- container -->
	<div id="container">
		
		<div class="unusual_contact_wrap">
			<img src="/common/img/icon_unusual.png">
			<strong>서비스 <span class="green">이용에 불편</span>을 드려 죄송합니다.</strong>
			<p>
				<span>요청하신 웹페이지의 이름이 바뀌었거나 <strong>변경 혹은 삭제</strong>되어 이용하실 수 없습니다.</span><br>
				입력하신 주소가 정확한지 <strong>다시 한번 확인해보시기 바랍니다.</strong>
			</p>
			
			<ul>
				<li>
					<a href="/index.do" class="btn_gray">메인 페이지로 이동</a>
				</li>
				<li>
					<a href="javascript:history.back();" class="btn_black">이전 페이지로 이동</a>
				</li>
			</ul>
		</div>
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script src="/common/js/logout.js"></script>
</body>
</html>