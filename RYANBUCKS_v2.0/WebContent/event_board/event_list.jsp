<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.EventBoardDTO"%>
<%@page import="utils.EventList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

@SuppressWarnings("unchecked")
List<EventBoardDTO> eventList = (List<EventBoardDTO>) resultMap.get("eventList");
@SuppressWarnings("unchecked")
List<String> eventImageList = (List<String>) resultMap.get("eventImageList");
@SuppressWarnings("unchecked")
List<EventBoardDTO> endEventList = (List<EventBoardDTO>) resultMap.get("endEventList");
@SuppressWarnings("unchecked")
List<String> endEventImageList = (List<String>) resultMap.get("endEventImageList");
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
			<dl>
				<!-- 진행중인 이벤트 -->
				<dt>진행중인 이벤트</dt>
				<dd>
					<ul>
						<%=EventList.getListString(eventList, eventImageList)%>
					</ul>
				</dd>
				<!-- // 진행중인 이벤트 -->
				
				<!-- 종료된 이벤트 -->
				<dt>종료된 이벤트</dt>
				<dd>
					<ul class="end_event">
						<%=EventList.getEndListString(endEventList, endEventImageList)%>
					</ul>
				</dd>
				<!-- // 종료된 이벤트 -->
			</dl>
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script src="/common/js/logout.js"></script>
</body>
</html>