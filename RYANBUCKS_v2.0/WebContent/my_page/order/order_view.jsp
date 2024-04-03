<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.OrderTotalDTO"%>
<%@page import="dto.OrderDetailDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

OrderTotalDTO orderTotalDto = (OrderTotalDTO) resultMap.get("orderTotalDto");
@SuppressWarnings("unchecked")
List<OrderDetailDTO> orderDetailList = (List<OrderDetailDTO>) resultMap.get("orderDetailList");
@SuppressWarnings("unchecked")
List<String> menuNameList = (List<String>) resultMap.get("menuNameList");
@SuppressWarnings("unchecked")
List<String> menuThumFileNameList = (List<String>) resultMap.get("menuThumFileNameList");
@SuppressWarnings("unchecked")
List<String> menuOptionInfoList = (List<String>) resultMap.get("menuOptionInfoList");
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
				<section class="order_view">
					<header>
						<strong class="tit">주문번호</strong>
						<span class="order_id"> <%=orderTotalDto.getId() %></span>
					</header>
					
					<article class="order_view_info">
						<div class="menu_info">
							<strong class="tit">주문상품정보</strong>
							<table>
								<colgroup>
									<col width="12%">
									<col width="*">
									<col width="16.5%">
									<col width="9.5%">
									<col width="16.5%">
								</colgroup>
								<thead>
									<tr>
										<th scope="col" colspan="2">상품 정보</th>
										<th scope="col">판매가</th>
										<th scope="col">수량</th>
										<th scope="col">소계금액</th>
									</tr>
								</thead>
								<tbody>
									<%
									int totalQuantity = 0;	// 총 주문 수량
									
									for(int i=0; i<orderDetailList.size(); i++) {
										OrderDetailDTO orderDetailDto = orderDetailList.get(i);
										
										int unitAmount = orderDetailDto.getMenuPrice() + orderDetailDto.getOptionPrice();	// 메뉴 가격 + 옵션 가격
										totalQuantity += orderDetailDto.getQuantity();
										
										out.println("<tr>");
										
										out.println("<td class='img'><a href='/menu/menu_view.do?no=" + orderDetailDto.getMenuNo() + "'><img src='/img/menu/" + menuThumFileNameList.get(i) + "'></a></td>");
										out.println("<td class='left'><a href='/menu/menu_view.do?no=" + orderDetailDto.getMenuNo() + "'><strong>" + menuNameList.get(i) + "</strong></a><br>");
										out.println("<span>" + menuOptionInfoList.get(i));
										if(!menuOptionInfoList.get(i).equals("")) {
											out.println(String.format(" (%+,d원)", orderDetailDto.getOptionPrice()));
										}
										out.println("</span></td>");
										out.println("<td>" + String.format("%,d원", unitAmount) + "</td>");
										out.println("<td>" + orderDetailDto.getQuantity() + "</td>");
										out.println("<td>" + String.format("%,d원", unitAmount * orderDetailDto.getQuantity()) + "</td>");
										
										out.println("</tr>");
									}
									%>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="3"></td>
										<td>주문 상품 수<br><%=String.format("%d종 (%s개)", orderDetailList.size(), totalQuantity) %></td>
										<td>총 결제 금액<br><%=String.format("%,d원", orderTotalDto.getAmount()) %></td>
									</tr>
								</tfoot>
							</table>
						</div>
					
						<div class="recipient_info">
							<strong class="tit">수령자정보</strong>
							<table>
								<colgroup>
									<col width="15%">
									<col width="30%">
									<col width="15%">
									<col width="30%">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">이름</th>
										<td><%=orderTotalDto.getRecipientName() %></td>
										<th scope="row">연락처</th>
										<td><%=orderTotalDto.getRecipientTel() %></td>
									</tr>
									<tr>
										<th scope="row">이메일</th>
										<td colspan="3"><%=(orderTotalDto.getRecipientEmail() != null)? orderTotalDto.getRecipientEmail() : "-" %></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="pay_info">
							<strong class="tit">결제정보</strong>
							<table>
								<colgroup>
									<col width="15%">
									<col width="30%">
									<col width="15%">
									<col width="30%">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">결제 수단</th>
										<td><%=(orderTotalDto.getPayMethod().equals("CARD")? "카드" : "") %></td>
										<th scope="row">카드번호</th>
										<td><%=orderTotalDto.getCardNo() %></td>
									</tr>
									<tr>
										<th scope="row">할부개월</th>
										<td><%=(orderTotalDto.getCardQuota().equals("00")? "일시불" : "") %></td>
										<th scope="row">승인 번호</th>
										<td><%=orderTotalDto.getAuthCode() %></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<%if(orderTotalDto.getType().equals("pickup")) { %>
						<div class="pickup_info">
							<strong class="tit">픽업정보</strong>
							<table>
								<colgroup>
									<col width="15%">
									<col width="30%">
									<col width="15%">
									<col width="30%">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">픽업 지점</th>
										<td><%=orderTotalDto.getPickupBranch() %></td>
										<th scope="row">픽업 시간</th>
										<td><%=orderTotalDto.getPickupTime().toString().substring(0, 16) %></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<%} else { %>
						<div class="shipping_info">
							<strong class="tit">배송정보</strong>
							<table>
								<colgroup>
									<col width="15%">
									<col width="30%">
									<col width="15%">
									<col width="30%">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">주소</th>
										<td colspan="3"><%=String.format("[%s] %s %s", orderTotalDto.getPostCode(), orderTotalDto.getRoadAddress(), orderTotalDto.getDetailAddress()) %></td>
									</tr>
								</tbody>
							</table>
						</div>
						<%} %>
					</article>
				</section>
			</div>
			
			<jsp:include page="/my_page/my_navigation.html" />
		</div>
		<!-- // 내용 -->
	
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script src="/common/js/logout.js"></script>
	<script src="/common/js/my_nav.js"></script>
</body>
</html>