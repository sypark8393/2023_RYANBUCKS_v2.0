<%@page import="java.util.Map"%>
<%@page import="dto.NoticeBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
NoticeBoardDTO noticeBoardDto = (NoticeBoardDTO) request.getAttribute("noticeBoardDto");

@SuppressWarnings("unchecked")
Map<String, Object> pNContentMap = (Map<String, Object>) request.getAttribute("pNContentMap");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>공지사항 | Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/notice.css" rel="stylesheet">
	
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
				<h2>공지사항</h2>
				
				<ul class="smap">
					<li>
						<a href="/"><img src="/common/img/icon_home.png" alt="홈으로"></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li class="en">
						<a href="/notice/notice_list.do" class="this">NOTICE</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="content_wrap">
			<section class="notice_view">
				<header>
					<h3><%=noticeBoardDto.getTitle() %></h3>
				</header>
				
				<article>
					<%=noticeBoardDto.getContent().replaceAll("\n", "<br>") %>
				</article>
			</section>
			
			<div class="notice_ctrl_btns">
				<ul>
					<li><a href="/notice/notice_list.do">목록</a></li>
				</ul>
			</div>
			
			<table class="pn_info">
				<colgroup>
					<col width="18.181818%">
					<col width="89.818182%">
				</colgroup>
				
				<tr>
					<th class="next" scope="row" id="next_title">윗글</th>
					<td class="next">
						<%if(pNContentMap.get("nextNo") != null) {
							out.println("<a href='/notice/notice_view.do?no=" + pNContentMap.get("nextNo") + "'>" + pNContentMap.get("nextTitle") + "</a>");
							
						} else {
							out.println(pNContentMap.get("nextTitle"));
						}
						%>
					</td>
				</tr>
				
				<tr>
					<th class="prev" scope="row" id="prev_title">아랫글</th>
					<td class="prev">
						<%if(pNContentMap.get("preNo") != null) {
							out.println("<a href='/notice/notice_view.do?no=" + pNContentMap.get("preNo") + "'>" + pNContentMap.get("preTitle") + "</a>");
							
						} else {
							out.println(pNContentMap.get("preTitle"));
						}
						%>
					</td>
				</tr>
			</table>
			
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>

	<script src="/common/js/logout.js"></script>
</body>
</html>