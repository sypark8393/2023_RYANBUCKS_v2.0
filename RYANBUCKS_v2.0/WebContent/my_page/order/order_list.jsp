<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.OrderTotalDTO"%>
<%@page import="utils.OrderList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

int totalCount = Integer.parseInt(resultMap.get("totalCount").toString());
int pageNum = Integer.parseInt(resultMap.get("pageNum").toString());
@SuppressWarnings("unchecked")
List<OrderTotalDTO> orderList = (List<OrderTotalDTO>) resultMap.get("orderList");
@SuppressWarnings("unchecked")
List<String> menuSummaryList = (List<String>) resultMap.get("menuSummaryList");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>주문 내역 | Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/my.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="wrap">
	<jsp:include page="/header.jsp" />
	
	<!-- container -->
	<div id="container">
	
		<!-- 서브 타이틀 -->
		<header class="my_sub_tit_wrap">
			<div class="my_sub_tit_bg">
				<div class="my_sub_tit_inner">
					<h4>주문 내역</h4>
					
					<ul class="smap">
						<li>
							<a href="/"><img src="/common/img/icon_home_w.png" alt="홈으로"></a>
						</li>
						<li>
							<img class="arrow" src="/common/img/icon_arrow_w.png" alt="하위메뉴">
						</li>
						<li class="en">
							<a href="/my/index.do">My Ryanbucks</a>
						</li>
						<li>
							<img class="arrow" src="/common/img/icon_arrow_w.png" alt="하위메뉴">
						</li>
						<li>
							<a href="/my/order_list.do">주문 내역</a>
						</li>
					</ul>
				</div>
			</div>
		</header>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="my_cont_wrap">
			<div class="my_cont">
				<!-- 검색 -->
				<section class="search_wrap">
					<form id="frm_search" action="/my/load_order_list_process" method="post">
						<!-- 기간 -->
						<dl class="period_wrap">
							<dt>기간별</dt>
							<dd>
								<input id="period_1month" name="period" type="radio" value="1" checked><label for="period_1month">1개월</label>
								<input id="period_3month" name="period" type="radio" value="3"><label for="period_3month">3개월</label>
								<input id="period_6month" name="period" type="radio" value="6"><label for="period_6month">6개월</label>
								<input id="period_before_6month" name="period" type="radio" value="before 6"><label for="period_before_6month">6개월 이전</label>
							</dd>
						</dl>
						<!-- // 기간 -->
						
						<!-- 주문유형 -->
						<dl class="type_wrap">
							<dt>주문유형</dt>
							<dd>
								<select id="type" name="type">
									<option value="whole">전체보기</option>
									<option value="pickup">픽업</option>
									<option value="shipping">배송</option>
								</select>
							</dd>
						</dl>
						<!-- // 주문유형 -->
						
						<!-- 결제수단 -->
						<dl class="pay_method_wrap">
							<dt>결제수단</dt>
							<dd>
								<select id="pay_method" name="pay_method">
									<option value="whole">전체보기</option>
									<option value="CARD">카드</option>
								</select>
							</dd>
						</dl>
						<!-- // 결제수단 -->
						
						<a href="javascript:void(0);" role="submit" onclick="searchOrder()">검색</a>
					</form>
				</section>
				<!-- // 검색 -->
				
				<!-- 목록 -->
				<div class="order_list">
					<ul class="info">
						<li>[주문번호] 및 [주문상품]을 클릭하시면 주문상세 내역을 조회하실 수 있습니다.</li>
					</ul>
					<table>
						<colgroup>
		                    <col width="22%">
		                    <col width="12.5%">
		                    <col width="35%">
		                    <col width="7.5%">
		                    <col width="*%">
                   	 	</colgroup>
	                    <thead>
		                    <tr>
			                    <th scope="col">주문번호</th>
			                    <th scope="col">주문일자</th>
			                    <th scope="col">주문상품</th>
			                    <th scope="col">유형</th>
			                    <th scope="col">총 거래 금액</th>
		                    </tr>
	                    </thead>
						<tbody>
							<%=OrderList.getListString(orderList, menuSummaryList)%>
						</tbody>
					</table>
					
					<!-- 페이지 -->
					<div class="pagination">
						<ul class="pager">
							<%=OrderList.getPagingString(application, totalCount, pageNum)%>
						</ul>
					</div>
					<!-- // 페이지 -->
				</div>
				<!-- // 목록 -->
			</div>
			
			<jsp:include page="/my_page/my_navigation.html" />
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script>
		$(document).ready(function () {
			// 페이지 영역의 요소를 클릭한 경우
			$(document).on("click", ".pager li", function() {
				var form = $("#frm_search");
				var pageNum;
				
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
						url : "/my/load_order_list_process",
						data : form.serialize() + "&page_num=" + pageNum,
						dataType: 'json',
						success : function(result) {
							$('.order_list table tbody').html(result.list);
							$('.pager').html(result.paging);
						}
					});
					
				// 페이지를 클릭한 경우
				} else {
					$.ajax({
						type : "POST",
						url : "/my/load_order_list_process",
						data : form.serialize() + "&page_num=" + $(this).find("a").html(),
						dataType: 'json',
						success : function(result) {
							$('.order_list table tbody').html(result.list);
							$('.pager').html(result.paging);
						}
					});
				}
				
			});
			
		});
		
		function searchOrder() {
			var form = $("#frm_search");
			
			$.ajax({
				type : "POST",
				url : "/my/load_order_list_process",
				data : form.serialize() + "&page_num=1",
				dataType: 'json',
				success : function(result) {
					$('.order_list table tbody').html(result.list);
					$('.pager').html(result.paging);
				
				}
			});
			
		}
	</script>
	
	<script src="/common/js/logout.js"></script>
	<script src="/common/js/my_nav.js"></script>
</body>
</html>