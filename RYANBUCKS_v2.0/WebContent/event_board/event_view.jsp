<%@page import="java.util.List"%>
<%@page import="dto.EventBoardDTO"%>
<%@page import="dto.EventImageDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
EventBoardDTO eventBoardDto = (EventBoardDTO) request.getAttribute("eventBoardDto");

@SuppressWarnings("unchecked")
List<String> eventImageList = (List<String>) request.getAttribute("eventImageList");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>이벤트 | Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/event.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="wrap">
	<jsp:include page="/header.jsp" />
	
	<!-- container -->
	<div id="container">
		
		<!-- 서브 타이틀 -->
		<div class="sub_tit_wrap">
			<div class="sub_tit_inner">
				<h2>이벤트</h2>
				
				<ul class="smap">
					<li>
						<a href="/"><img src="/common/img/icon_home.png" alt="홈으로"></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li class="en">
						<a href="/event/event_list.do" class="this">EVENT</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="content_wrap">
			<section class="event_view">
				<header>
					<h3><%=eventBoardDto.getTitle() %></h3>
					<span class="date"><%=eventBoardDto.getStartDate() %> ~ <%=eventBoardDto.getEndDate() %></span>
				</header>
				
				<article class="event_view_info">
					<%for(String fileName : eventImageList) {
						out.println("<img src='/img/event/" + fileName + "'>");
					}
					%>
				</article>
				
				<div class="event_ctrl_btns">
					<a href="/event/event_list.do">목록</a>
				</div>
			</section>
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script src="/common/js/logout.js"></script>
</body>
</html>