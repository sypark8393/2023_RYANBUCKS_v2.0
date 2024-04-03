<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dto.OrderTotalDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
OrderTotalDTO orderTotalDto = (OrderTotalDTO) request.getAttribute("orderTotalDto");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>주문 완료 | Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/order.css" rel="stylesheet">
	
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
				<h2>주문 완료</h2>
				
				<ul class="smap">
					<li>
						<a href="/"><img src="/common/img/icon_home.png" alt="홈으로"></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<a href="/cart/cart.do">장바구니</a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<span class="prev_step">주문하기</span>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<a href="javascript:void(0);" class="this">주문 완료</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="content_wrap">
			<!-- 헤더 -->
			<header>
				<h3>주문이 정상적으로 <span class="green">완료</span>되었습니다.</h3>
				<p>주문 내역은 <strong>My Ryanbucks > 주문 내역</strong>에서 확인하실 수 있습니다.</p>
			</header>
			<!-- // 헤더 -->
			
			<!-- 주문 정보 -->
			<div class="order_info">
				<table>
					<colgroup>
	                    <col width="28%">
	                    <col width="*%">
                    </colgroup>
					<tr>
						<th>주문번호</th>
						<td><a href="/my/order_view.do?order_id=<%=orderTotalDto.getId() %>"><span class="green"><%=orderTotalDto.getId() %></span></a></td>
					</tr>
					
					<%if(orderTotalDto.getType().equals("pickup")) { %>
					<!-- 픽업 주문인 경우 -->
					<tr>
						<th>픽업 지점</th>
						<td><%=orderTotalDto.getPickupBranch() %></td>
					</tr>
					<tr>
						<th>픽업 시간</th>
						<td><%=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(orderTotalDto.getPickupTime()) %></td>
					</tr>
					
					<%} else { %>
					<!-- 배송 주문인 경우 -->
					<tr>
						<th>배송지 주소</th>
						<td>[<%=orderTotalDto.getPostCode() %>] <%=orderTotalDto.getRoadAddress() %> <%= orderTotalDto.getDetailAddress() %></td>
					</tr>
					
					<%} %>
				</table>
			</div>
			<!-- // 주문 정보 -->
			
			<!-- 이동 버튼 -->
			<ul class="btns">
				<li>
					<a href="/index.do" class="btn_gray">메인 페이지로 이동</a>
				</li>
				<li>
					<a href="/my/order_list.do" class="btn_black">주문 내역으로 이동</a>
				</li>
			</ul>
			<!-- // 이동 버튼 -->
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
</body>
</html>