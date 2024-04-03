<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.NoticeBoardDTO"%>
<%@page import="utils.NoticeList"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

int totalCount = Integer.parseInt(resultMap.get("totalCount").toString());
int pageNum = Integer.parseInt(resultMap.get("pageNum").toString());
@SuppressWarnings("unchecked")
List<NoticeBoardDTO> list = (List<NoticeBoardDTO>) resultMap.get("list");
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
			<!-- 검색 -->
			<div class="search_wrap">
				<form id="frm_search" action="/notice/load_list_process" method="post" onsubmit="return false;">
					<p>
						<input type="text" name="keyword" id="keyword" placeholder="검색어를 입력해 주세요.">
						<a href="javascript:void(0);" role="submit" onclick="searchNotice()">검색</a>
					</p>
				</form>
			</div>
			<!-- // 검색 -->
			
			<!-- 목록 -->
			<div class="notice_list">
				<table>
					<colgroup>
	                    <col width="5.45454%">
	                    <col width="74.5454%">
	                    <col width="10.90909%">
	                    <col width="*">
                    </colgroup>
                    <thead>
	                    <tr>
		                    <th scope="col" class="en">No</th>
		                    <th scope="col">제목</th>
		                    <th scope="col">날짜</th>
		                    <th scope="col">조회수</th>
	                    </tr>
                    </thead>
					<tbody>
                    	<%=NoticeList.getListString(list)%>
					</tbody>
				</table>
				
				<!-- 페이지 -->
				<div class="pagination">
					<ul class="pager">
						<%=NoticeList.getPagingString(application, totalCount, pageNum)%>
					</ul>
				</div>
				<!-- // 페이지 -->
			</div>
			<!-- // 목록 -->
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script>
	$(document).ready(function () {
		// 검색어 입력란에서 Enter키 클릭 시 검색 처리를 위한 함수 호출
		$("#keyword").on("keydown", function(event) {
			if(event.keyCode === 13) {
				searchNotice();
			}
		});
		
		// 페이지 영역의 요소를 클릭한 경우
		$(document).on("click", ".pager li", function() {
			// 컨트롤 버튼을 클릭한 경우
			if($(this).hasClass("control")) {
				var pageCountPerBlock = <%=Integer.parseInt(application.getInitParameter("PageCountPerBlock")) %>
				var pageNum = $(".pager li.active a").html();	// 페이지 번호
				
				// 이전 버튼 (첫번째 요소)
				if($(this).index() === 0) {
					pageNum = Math.floor((pageNum - 11) / pageCountPerBlock) * pageCountPerBlock + 1;
					
				// 이전 버튼
				} else {
					pageNum = Math.ceil(pageNum / pageCountPerBlock) * pageCountPerBlock + 1;
					
				}
				
				$.ajax({
					type : "POST",
					url : "/notice/load_list_process",
					data : "keyword=" + $("#keyword").val() + "&page_num=" + pageNum,
					dataType: 'json',
					success : function(result) {
						$('.notice_list table tbody').html(result.list);
						$('.pager').html(result.paging);
					}
				});
				
			// 페이지를 클릭한 경우
			} else {
				$.ajax({
					type : "POST",
					url : "/notice/load_list_process",
					data : "keyword=" + $("#keyword").val() + "&page_num=" + $(this).find("a").html(),
					dataType: 'json',
					success : function(result) {
						$('.notice_list table tbody').html(result.list);
						$('.pager').html(result.paging);
					}
				});
			}
			
		});
	});
	
	function searchNotice() {
		var keyword = $("#keyword");
		
		// 검색어를 입력하지 않았을 때
		if(!keyword.val()) {
			alert("검색어를 입력해 주세요.");
			keyword.focus();
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "/notice/load_list_process",
			data : "keyword=" + keyword.val() + "&page_num=1",
			dataType: 'json',
			success : function(result) {
				$('.notice_list table tbody').html(result.list);
				$('.pager').html(result.paging);
				
			}
		});
	}
	</script>
	
	<script src="/common/js/logout.js"></script>
</body>
</html>