<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

int no = (int) resultMap.get("no");
CartDTO cartDto = (CartDTO) resultMap.get("cartDto");
MenuDTO menuDto = (MenuDTO) resultMap.get("menuDto");
String fileName = (String) resultMap.get("fileName");
@SuppressWarnings("unchecked")
List<MenuOptionDTO> optionList = (List<MenuOptionDTO>) resultMap.get("optionList");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>주문 수정</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/menu.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="wrap">
		<div class="menu_detail edit_cart">
			<header>
				<h1>주문 수정</h1>
				<a href="javascript:window.close()" role="button">&times;</a>
			</header>
		
			<!-- 메뉴명 -->
			<div class="info">
				<h4><%=menuDto.getNameKor() %></h4>
			</div>
			<!-- // 메뉴명 -->
			
			<!-- 판매가 -->
			<div class="sales_price price">
				<dl>
					<dt>
						<strong>판매가</strong>
						<span>(옵션 미적용가)</span>
					</dt>
					<dd>
						<span class="price"><%=String.format("%,d", menuDto.getSalesPrice()) %></span><span class="won">원</span>
					</dd>
				</dl>
			</div>
			<!-- // 판매가 -->
			
			<%if(optionList.size() != 0) { %>
			<!-- 옵션 -->
			<div class="option">
				<select id="option_no" name="option_no" onchange="changeOption()">
					<%for(MenuOptionDTO menuOptionDto : optionList) {
						out.println("<option value='" + menuOptionDto.getNo() + "' ");
						out.println("data-option-stock='" + menuOptionDto.getStock() + "' ");
						out.println("data-option-price='" + menuOptionDto.getPrice() + "' ");
						
						if(menuOptionDto.getStock() == 0) {
							out.println("class='sold_out' disabled");
						}
						
						if(menuOptionDto.getNo() == cartDto.getOptionNo()) {
							out.println(" selected");
						}
						out.println(">");
						
						out.println(menuOptionDto.getOption1Value());
						if(menuOptionDto.getOption2Name() != null) {
							out.println(" | " + menuOptionDto.getOption2Value());
						}
						out.println(" (" + String.format("%+,d", menuOptionDto.getPrice()) + "원)");
						if(menuOptionDto.getStock() == 0) {
							out.println(" (품절)");	
						}
						
						out.println("</option>");
					}
					%>
				</select>
			</div>
			<!-- // 옵션 -->
			<%} %>
			
			<!-- 수량 -->
			<div class="quantity">
				<input type="button" id="decrease" value="-" onclick="decreaseQuantity()" disabled>
				<input type="number" id="quantity" name="quantity" value="<%=cartDto.getQuantity() %>" min="1" max="<%=menuDto.getStock() %>" readonly>
				<input type="button" id="increase" value="+" onclick="increaseQuantity()">
			</div>
			<!-- // 수량 -->
			
			<!-- 총 상품 금액 -->
			<div class="total_price price">
				<dl>
					<dt>
						<strong>총 상품 금액</strong>
					</dt>
					<dd>
						<span class="info">총 주문 수량 <span><%=cartDto.getQuantity() %></span>개</span><span class="price"><%=String.format("%,d", menuDto.getSalesPrice()) %></span><span class="won">원</span>
					</dd>
				</dl>
			</div>
			<!-- // 총 상품 금액 -->
			
			<div class="ctrl_btns">
				<div class="cart">
					<a href="javascript:window.close();" role="button">취소</a>
				</div>
				
				<div class="order">
					<a href="javascript:void(0);" role="button" onclick="edit()">확인</a>
				</div>
			</div>
		</div>
	</div>
	
	<script>
	$(document).ready(function () {
		var selectOption = $("#option_no");
	    var selectedOption = selectOption.find(":selected");
	    var optionStock = Number(selectedOption.data('option-stock'));
	    var optionPrice = Number(selectedOption.data('option-price'));
		
	 	// 옵션 정보가 없는 메뉴이면
	    if(isNaN(optionStock)) {
	    	optionStock = <%=menuDto.getStock() %>;
	    	optionPrice = 0;
	    }
	    
	 	// 주문 수량이 1이 아니면 수량 감소 버튼 활성화
	 	if(quantity != 1) {	
	 		$(".quantity #decrease").prop('disabled', false);
	 	}
	 	
	 	// 옵션 수량이 주문 수량보다 같거나 작다면 수량 증가 버튼 비활성화
	 	if(optionStock <= quantity) {
	 		$(".quantity #increase").prop('disabled', true);
	 	}
	 	
	 	// 총 상품 금액
	 	$(".total_price dd span.price").html(((<%=menuDto.getSalesPrice()%>  + optionPrice) * <%=cartDto.getQuantity() %>).toLocaleString('ko-KR'));
	 	
	});
		
	function edit() {
		var selectOption = $("#option_no");
	    var selectedOption = selectOption.find(":selected");
		
		$.ajax({
	 		type : "POST",
	 		url : "/cart/edit_cart_process",
	 		data : "no=" + <%=no %> + "&option_no=" + selectedOption.val() + "&quantity=" + $("#quantity").val(),
	 		success : function(result) {
	 			if(result != 0) { // 장바구니 업데이트 성공 시
	 				self.close();						// 팝업창 닫기
	 				opener.document.location.reload();	// 팝업창을 호출한 페이지 새로고침
	 				
	 			} else { // 장바구니 업데이트 실패 시
					alert("주문 수정에 실패하였습니다.");
					
				}
	 		}
	 	});
	}
	</script>
	<script src="/common/js/menu_qty_and_opt.js"></script>
</body>
</html>