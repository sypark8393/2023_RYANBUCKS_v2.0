<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

@SuppressWarnings("unchecked")
List<CartDTO> cartList = (List<CartDTO>) resultMap.get("cartList");
@SuppressWarnings("unchecked")
List<MenuDTO> menuList = (List<MenuDTO>) resultMap.get("menuList");
@SuppressWarnings("unchecked")
List<String> menuThumFileNameList = (List<String>) resultMap.get("menuThumFileNameList");
@SuppressWarnings("unchecked")
List<MenuOptionDTO> menuOptionList = (List<MenuOptionDTO>) resultMap.get("menuOptionList");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>장바구니 | Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/cart.css" rel="stylesheet">
	
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
				<h2>장바구니</h2>
				
				<ul class="smap">
					<li>
						<a href="/"><img src="/common/img/icon_home.png" alt="홈으로"></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<a href="/cart/cart.do" class="this">장바구니</a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<span class="next_step">주문하기</span>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<span class="next_step">주문 완료</span>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="content_wrap">
			<%if(cartList.size() == 0) {%>
			<div class="no_list">
				<strong>장바구니에 담긴 상품이 없습니다.</strong>
				<p>
					원하는 상품을 장바구니에 담아보세요!
				</p>
				
				<a href="/menu/index.do">쇼핑 계속하기</a>
			</div>
			
			<%} else { %>
			<div class="product_list">
				<form id="frm_cart" action="/order/order_sheet.do" method="post">
					<input type="hidden" name="src" value="cart">
					<div class="cart_ctrl_btns">
						<div class="btn_check">
							<input type="checkbox" id="check_all" onchange="checkAll()" checked><label for="check_all">전체 선택</label>
						</div>
						<div class="btn_delete">
							<input type="button" value="&times; 선택 삭제" onclick="deleteSelectedItem()">
						</div>
					</div>
					
					<table>
						<colgroup>
							<col width="7%">
							<col width="8.5%">
							<col width="*">
							<col width="12.5%">
							<col width="7%">
							<col width="12.5%">
							<col width="12%">			
						</colgroup>
						<thead>
							<tr>
								<th scope="col"></th>
								<th scope="col" colspan="2">상품 정보</th>
								<th scope="col">판매가</th>
								<th scope="col">수량</th>
								<th scope="col">소계금액</th>
								<th scope="col"></th>
							</tr>
						</thead>
						<tbody>
							<%
							int totalAmount = 0;	// 총 금액(결제 예정 금액)
							int totalCount = 0;		// 총 개수
							
							for(int i=0; i<cartList.size(); i++) {
								CartDTO cartDto = cartList.get(i);
								MenuDTO menuDto = menuList.get(i);
								MenuOptionDTO menuOptionDto = menuOptionList.get(i);
								
								int unitAmount = menuDto.getSalesPrice() + menuOptionDto.getPrice();	// 메뉴 가격 + 옵션 가격
								int subTotalAmount = unitAmount * cartDto.getQuantity();			// 소계 금액: 단가 * 수량
								
								int stock = (menuOptionDto.getNo() != 0)? menuOptionDto.getStock() : menuDto.getStock();
								
								if(stock >= cartDto.getQuantity()) {
									totalAmount += subTotalAmount;
									totalCount++;
								}
							%>
							<tr>
								<td>
									<%if(stock >= cartDto.getQuantity()) { %>
									<input type="checkbox" name="cart_item" id="<%=cartDto.getNo() %>" value="<%=cartDto.getNo() %>" onchange="checkItem()" checked><label for="<%=cartDto.getNo() %>"></label>
									<%} %>
								</td>
								<td>
									<a href="/menu/menu_view.do?no=<%=cartDto.getMenuNo() %>"><img src="/img/menu/<%=menuThumFileNameList.get(i) %>"></a>
								</td>
								<td class="left">
									<strong><%=menuDto.getNameKor() %></strong><br>
									<span>
										<%
										if(stock < cartDto.getQuantity()) {
											out.println("해당 상품은 재고 부족으로 현재 주문하실 수 없습니다.");
											
										} else if(menuOptionDto.getNo() != 0) {
											out.println(menuOptionDto.getOption1Value());
											
											if(menuOptionDto.getOption2Value() != null) {
												out.println(" | " + menuOptionDto.getOption2Value());
											}
											
											out.println(String.format("(%+,d원)", menuOptionDto.getPrice()));
										}
										%>
									</span>
									<%if(stock >= cartDto.getQuantity()) { %>
									<input type="button" class="edit" value="주문 수정" onclick="edit(<%=cartDto.getNo() %>)">
									<%} %>
								</td>
								<td><%=String.format("%,d원", unitAmount) %></td>
								<td><%=cartDto.getQuantity() %></td>
								<td><%=String.format("%,d원", subTotalAmount) %></td>
								<td class="btns">
									<%if(stock >= cartDto.getQuantity()) { %>
									<input type="button" class="order" value="주문하기" onclick="directOrder(<%=cartDto.getNo() %>)">
									<%} %>
									<input type="button" class="delete" value="삭제" onclick="deleteItem(<%=cartDto.getNo() %>)">
								</td>
							</tr>
							<%} %>
						</tbody>
					</table>
					
					<div class="total">
						<div class="total_price">
							<dl>
								<dt>결제 예정 금액</dt>
								<dd>
									<span class="price"><%=String.format("%,d", totalAmount) %></span><span class="won">원</span>
								</dd>
							</dl>
						</div>
						
						<div class="btn_order">
							<a href="javascript:void(0);" onclick="order()"><span><%=totalCount %></span>건 주문하기</a>
						</div>
					</div>
				</form>
			</div>
			<%} %>
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script>
		// 전체 선택
		function checkAll() {
			if($("#check_all").prop("checked")) { // 전체 선택
				$(".product_list table input[type=checkbox]").prop("checked", true);	// 모든 체크박스에 체크
				
			} else { // 전체 선택 해제
				$(".product_list table input[type=checkbox]").prop("checked", false);	// 모든 체크박스에 체크 해제
			}
			
			updateTotalAreaContent();	// 체크된 아이템에 따른 결제 예정 금액, 주문 건수 업데이트
		}
		
		// 선택 삭제
		function deleteSelectedItem() {
			var form = $("#frm_cart");
			var cnt = $(".product_list table input[type=checkbox]:checked").length;
			
			if(cnt == 0) {
				alert("삭제하실 상품을 선택해 주세요.");
				return false;
			}
			
			$.ajax({
				type : "POST",
				url : "/cart/delete_process",
				data : form.serialize(),
				success : function(result) {
					if(result != 0) { // 장바구니에서 메뉴 삭제 성공 시
						alert("메뉴 삭제에 성공하였습니다.");
						location.reload();
						
					} else { // 장바구니에서 메뉴 삭제 실패 시
						alert("메뉴 삭제에 실패하였습니다.");
					}
				}
			});	
			
		}
		
		// 체크박스
		function checkItem() {
			var cnt = $(".product_list table input[type=checkbox]:checked").length;
			
			// 모든 체크박스에 체크가 되었다면 전체 선택 체크박스 체크
			if(cnt === <%=cartList.size() %>) {
				$("#check_all").prop("checked", true);
			
			// 모든 체크박스에 체크가 되지 않은 경우 전체 선택 체크박스 체크 해제
			} else {
				$("#check_all").prop("checked", false);
			}
			
			updateTotalAreaContent();	// 체크된 아이템에 따른 결제 예정 금액, 주문 건수 업데이트
		}
		
		// 단일 삭제
		function deleteItem(no) {
			$.ajax({
				type : "POST",
				url : "/cart/delete_process",
				data : "type=single&no=" + no,
				success : function(result) {
					if(result != 0) { // 장바구니에서 메뉴 삭제 성공 시
						alert("상품이 삭제 되었습니다.");
						location.reload();
						
					} else { // 장바구니에서 메뉴 삭제 실패 시
						alert("상품 삭제에 실패하였습니다.");
					}
				}
			});	
		}
		
		// 단일 주문
		function directOrder(no) {
			var newForm = $('<form>', {
	            action: '/order/order_sheet.do',
	            method: 'post',
	            style: 'display: none;'
	        });
			
			newForm.append($('<input/>', {type: 'hidden', name: 'src', value:"cart", checked: true}));
			newForm.append($('<input/>', {type: 'checkbox', name: 'cart_item', value:no, checked: true}));
			newForm.appendTo('body');
			newForm.submit();
			
		}
		
		// 주문 수정
		function edit(no) {
			window.open("/cart/edit_cart_popup.do?no=" + no, "edit_cart_popup", "width=490, height=500, top=280, left=765");
			
		}
		
		// total 영역(결제 예정 금액, 주문 버튼) 내용 업데이트
		function updateTotalAreaContent() {
			var cnt = $(".product_list table input[type=checkbox]:checked").length;					// 선택된 메뉴 개수
			var checkedRows = $('.product_list table input[type=checkbox]:checked').closest('tr');	// 선택된 메뉴의 행
			
			// 결제 예정 금액
			var subTotalPrice = 0;
            checkedRows.each(function () {
            	subTotalPrice += Number($(this).find('td:eq(5)').text().replace(/,|원/g, ''));	// 각 체크된 행의 소계 금액(6번째 열)
            	
            });
            $(".total .total_price span.price").text(subTotalPrice.toLocaleString('ko-KR'));
			
         	// 주문 버튼
			if(cnt == 0) { // 선택된 메뉴가 없으면
				$(".total .btn_order a").addClass("disabled");		// 주문 버튼 요소의 비활성화 상태를 의미하는 클래스 추가
				
			} else { // 선택된 메뉴가 있으면
				$(".total .btn_order a").removeClass("disabled");	// 주문 버튼 요소의 비활성화 상태를 의미하는 클래스 삭제
			}
            
			// 주문 수량
			$(".total .btn_order span").text(cnt);
			
		}
		
		// 선택 상품 주문
		function order() {
			var cnt = $(".product_list table input[type=checkbox]:checked").length;					// 선택된 메뉴 개수
			
			if(cnt == 0) {
				return false;
			}
			
			var form = $("#frm_cart");
			form.submit();
			
		}
	</script>
	
	<script src="/common/js/logout.js"></script>
</body>
</html>