<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.*"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

String src = (String) resultMap.get("src");
MemberDTO memberDto = (MemberDTO) resultMap.get("memberDto");
AddressDTO addressDto = (AddressDTO) resultMap.get("addressDto");
@SuppressWarnings("unchecked")
List<Integer> cartNoList = (List<Integer>) resultMap.get("cartNoList");
@SuppressWarnings("unchecked")
List<OrderDetailDTO> orderDetailList = (List<OrderDetailDTO>) resultMap.get("orderDetailList");
@SuppressWarnings("unchecked")
List<String> menuNameList = (List<String>) resultMap.get("menuNameList");
@SuppressWarnings("unchecked")
List<String> menuThumFileNameList = (List<String>) resultMap.get("menuThumFileNameList");
@SuppressWarnings("unchecked")
List<String> menuOptionInfoList = (List<String>) resultMap.get("menuOptionInfoList");

String selectableFirstDate = LocalDate.now().plusDays(1).toString(); // +1일
String selectableLastDate = LocalDate.now().plusDays(7).toString(); // +7일
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>주문하기 | Ryanbucks Korea</title>
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
				<h2>주문하기</h2>
				
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
						<a href="javascript:void(0);" class="this">주문하기</a>
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
			<form id="frm_order" action="/order/order_process" method="post">
				<input type="hidden" name="src" value="<%=src %>">
				<%
				if(cartNoList == null) {	// menu_veiw에서 요청
					out.println("<input type='hidden' name='menu_no' value=" + orderDetailList.get(0).getMenuNo() + ">");
					out.println("<input type='hidden' name='option_no' value=" + orderDetailList.get(0).getOptionNo() + ">");
					out.println("<input type='hidden' name='quantity' value=" + orderDetailList.get(0).getQuantity() + ">");
					
				} else {	// cart에서 요청
					for(int no : cartNoList) {
						out.println("<input type='hidden' name='cart_item' value=" + no + ">");
					}
				}
				%>
				
				<!-- 주문 상품 목록 -->
				<div class="product_list">
					<strong>주문상품</strong>
					<table>
						<colgroup>
							<col width="7%">
							<col width="8.5%">
							<col width="*">
							<col width="12.5%">
							<col width="7%">
							<col width="12.5%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">No</th>
								<th scope="col" colspan="2">상품 정보</th>
								<th scope="col">판매가</th>
								<th scope="col">수량</th>
								<th scope="col">소계금액</th>
							</tr>
						</thead>
						<tbody>
							<%
							int totalAmount = 0;	// 총 금액(결제 예정 금액)
							int totalQuantity = 0;	// 총 주문 수량
							
							for(int i=0; i<orderDetailList.size(); i++) {
								OrderDetailDTO orderDetailDto = orderDetailList.get(i);
								
								int unitAmount = orderDetailDto.getMenuPrice() + orderDetailDto.getOptionPrice();	// 단가: 메뉴 가격 + 옵션 가격
								int subTotalAmount = unitAmount * orderDetailDto.getQuantity();						// 소계 금액: 단가 * 수량
								
								totalQuantity += orderDetailDto.getQuantity();
								totalAmount += subTotalAmount;
								
								out.println("<tr>");
								
								out.println("<td>" + (i+1) + "</td>");
								out.println("<td class='img'><a href='/menu/menu_view.do?no=" + orderDetailDto.getMenuNo() + "'><img src='/img/menu/" + menuThumFileNameList.get(i) + "'></a></td>");
								out.println("<td class='left'><strong>" + menuNameList.get(i) + "</strong><br>");
								out.println("<span>" + menuOptionInfoList.get(i));
								if(!menuOptionInfoList.get(i).equals("")) {
									out.println(String.format(" (%+,d원)", orderDetailDto.getOptionPrice()));
								}
								out.println("</span></td>");
								out.println("<td>" + String.format("%,d원", unitAmount) + "</td>");
								out.println("<td>" + orderDetailDto.getQuantity() + "</td>");
								out.println("<td>" + String.format("%,d원", subTotalAmount) + "</td>");
								
								out.println("</tr>");
							}
							%>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="4"></td>
								<td>주문 상품 수<br><%=String.format("%d종 (%s개)", orderDetailList.size(), totalQuantity) %></td>
								<td>총 결제 금액<br><%=String.format("%,d원", totalAmount) %></td>
							</tr>
						</tfoot>
					</table>
				</div>
				<!-- // 주문 상품 목록 -->
				
				<!-- 주문 정보 -->
				<div class="input_info_wrap">
					<!-- 수령인 정보 -->
					<div class="recipient_info_wrap">
						<fieldset>
							<legend>수령인 정보</legend>
							<dl>
								<dt><label for="name"><strong>이름<span class="green">(필수)</span></strong></label></dt>
								<dd><input id="name" name="name" type="text" maxlength="10" placeholder="이름"></dd>
							</dl>
							<dl>
								<dt><label for="tel_first"><strong>연락처<span class="green">(필수)</span></strong></label></dt>
								<dd>
									<div class="tel">
										<select id="tel_first" name="tel_first">
										<option value="010">010</option>
										<option value="011">011</option>
										<option value="016">016</option>
										<option value="017">017</option>
										<option value="019">019</option>
										</select>
										-
										<input id="tel_middle" name="tel_middle" type="number" maxlength="4" oninput="numberMaxLength(this);" placeholder="4자리">
										-
										<input id="tel_last" name="tel_last" type="number" maxlength="4" oninput="numberMaxLength(this);" placeholder="4자리">
									</div>
								</dd>
							</dl>
							<dl>
								<dt><label for="email_id"><strong>이메일</strong></label></dt>
								<dd>
									<div class="email">
										<input id="email_id" name="email_id" type="text" maxlength=20 placeholder="최대 20자리">
										@
										<select name="email_domain" id="email_domain">
											<option value="naver.com">naver.com</option>
											<option value="gmail.com">gmail.com</option>
											<option value="hanmail.net">hanmail.net</option>
										</select>
									</div>
								</dd>
							</dl>
						</fieldset>
					</div>
					<!-- // 수령인 정보 -->
					
					<!-- 주문 유형 -->
					<div class="type_wrap">
						<fieldset>
					    	<legend>주문 유형</legend>
					    	<input type="radio" name="type" id="type_pickup" value="pickup" checked><label for="type_pickup">픽업</label>
					    	<input type="radio" name="type" id="type_shipping" value="shipping"><label for="type_shipping">배송</label>
					    </fieldset>
					</div>
					<!-- // 주문 유형 -->
					
					<!-- 픽업 정보 -->
					<div class="pickup_info_wrap">
						<fieldset>
							<legend>픽업 정보</legend>
							
							<dl>
								<dt><label for="branch"><strong>픽업 지점</strong></label></dt>
								<dd>
									<select name="branch" id="branch">
										<option value="상봉점">상봉점</option>
										<option value="중랑점">중랑점</option>
									</select>
								</dd>
							</dl>
							
							<dl>
								<dt><label for="hour"><strong>픽업 시간</strong></label></dt>
								<dd>
									<input type="date" name="date" value=<%=selectableFirstDate %> min=<%=selectableFirstDate %> max=<%=selectableLastDate %>>
									<select name="hour" id="hour">
										<%
										for(int i=9; i<22; i++) {
											out.println("<option value=" + i + ">" + i + "</option>");
										}
										%>
									</select>
									<select name="minutes" id="minutes"> <!-- input type=time은 시간 선택이 직관적이지 않아서 선택 안함 -->
										<%
										for(int i=0; i<60; i=i+10) {
											out.println("<option value=" + i + ">" + String.format("%02d", i) +"</option>");
										}
										%>
									</select>
									
									<span class="info">*주문일 기준 익일 ~ 7일 이내에만 픽업 주문이 가능합니다.</span>
								</dd>
							</dl>
						</fieldset>
					</div>
					<!-- // 픽업 정보 -->
					
					<!-- 배송지 정보 -->
					<div class="shipping_info_wrap" style="display: none;">
						<fieldset>
							<legend>배송지 정보</legend>
							<%if(addressDto != null) {%>
								<input type="radio" name="address" id="existed" value="existed" checked><label for="existed">기본 배송지</label>
								<input type="radio" name="address" id="new" value="new"><label for="new">신규 배송지</label>
							<%} else {%>
					    		<input type="radio" name="address" id="new" value="new" checked><label for="new">신규 배송지</label>
					    	<%} %>
							
							<%if(addressDto != null) {%>
							<!-- 기본 배송지 -->
							<div class="existed_address">
								<input type="text" name="existed_post_code" class="post_code" value="<%=addressDto.getPostCode() %>" readonly>
								<br>
								<input type="text" name="existed_road_address" class="road_address" value="<%=addressDto.getRoadAddress() %>" readonly><br>
								<input type="text" name="existed_detail_address" class="detail_address" value="<%=addressDto.getDetailAddress() %>" readonly>
							</div>
							<!-- // 기본 배송지 -->
							<%} %>
							
							<!-- 신규 배송지 -->
							<div class="new_address">
								<input type="text" name="new_post_code" id="post_code" class="post_code" placeholder="우편번호" readonly>
								<a href="javascript:void(0);" id="search_address" role="button" onclick="daumPostcode()">검색</a>
								<br>
				  				<input type="text" name="new_road_address" id="road_address" class="road_address" placeholder="도로명주소" readonly><br>
				  				<input type="text" name="new_detail_address" id="detail_address" class="detail_address" maxlength=30 placeholder="상세주소(최대 30자리)">
							</div>
							<!-- // 신규 배송지 -->
						</fieldset>
					</div>	
					<!-- // 배송지 정보 -->
					
					<!-- 결제 수단 -->
					<div class="pay_method">
						<fieldset>
							<legend>결제 수단</legend>
							<input type="radio" name="pay_method" id="card" value="CARD" checked><label for="card">신용카드</label><br>
						</fieldset>
					</div>
					<!-- // 결제 수단 -->
				</div>
				<!-- // 주문 정보 -->
				
				<!-- 약관 -->
				<div class="term_wrap">
					<input type="checkbox" id="pay_purpose" name="pay_purpose">
					<label for="pay_purpose"></label><a href="javascript:void(0);" onclick="showTerm('#pay_purpose_wrap')">개인정보 수집 및 이용 동의<span class="green">(필수)</span></a>
					<jsp:include page="/shop/term/pay_purpose.html" />
				</div>
				<!-- // 약관 -->
				
				<!-- 결제 및 주의사항 -->
				<div class="button_box">
					<a href="javascript:void(0);" role="submit" onclick="order()">결제하기</a>
					<span class="caution">*주문 완료 후 주문 변경 또는 취소는 불가합니다.</span>
				</div>
				<!-- // 결제 및 주의사항 -->
			</form>
		</div>
		<!-- // 내용 -->
	
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/common/js/daum_postcode.js"></script>
	
	<script>
	// 페이지에 처음 접근한 시간 기록
	var pageLoadTime = new Date();
	
	$(document).ready(function () {
		// 이름
		$("#name").attr("value", "<%=memberDto.getName()%>");
		
		// 연락처
		$("#tel_first option").each(function() {								// 선택 가능한 지역번호 옵션에 순차적으로 접근하면서
		    if ($(this).val() === "<%=memberDto.getTel() %>".split('-')[0]) {	// 일치하는 지역번호 탐색
		      $(this).prop("selected", true);
		      return;
		    }
		});
		$("#tel_middle").attr("value", "<%=memberDto.getTel() %>".split('-')[1]);
		$("#tel_last").attr("value", "<%=memberDto.getTel() %>".split('-')[2]);
		
		// 이메일
		if(<%=(memberDto.getEmail() != null) %>) { // 저장된 이메일 정보가 있는 경우
			// 아이디
			$("#email_id").attr("value", "<%=memberDto.getEmail() %>".split('@')[0]);
		
			// 도메인
			$("#email_domain option").each(function() {								// 선택 가능한 도메인 옵션에 순차적으로 접근하면서
			    if ($(this).val() === "<%=memberDto.getEmail() %>".split('@')[1]) {	// 일치하는 도메인 탐색
			      $(this).prop("selected", true);
			      return;
			    }
			});
		}
		
		// 주문 유형 - 픽업을 선택한 경우
		$("#type_pickup").on("change", function() {
			if($(this).prop("checked")) {
				$(".pickup_info_wrap").css("display", "block");		// 픽업 정보 영역 보이기
				$(".shipping_info_wrap").css("display", "none");	// 배송지 정보 영역 숨기기
				
			}
			
		});
		
		// 주문 유형 - 배송을 선택한 경우
		$("#type_shipping").on("change", function() {
			if($(this).prop("checked")) {
				$(".pickup_info_wrap").css("display", "none");		// 픽업 정보 영역 숨기기
				$(".shipping_info_wrap").css("display", "block");	// 배송지 정보 영역 보이기
				
			}
			
		});
		
	});
	
	function order() {
		// 현재 시간 구하기
	    var currentTime = new Date();
		
		// 경과 시간(밀리초 단위) 구하기
		var elapsedTime = currentTime - pageLoadTime;
		//console.log((elapsedTime / (1000 * 60)));
		
		// 20분 이상 경과 시 이전 페이지로 이동
		if((elapsedTime / (1000 * 60)) >= 20) {
			alert("주문 시간이 초과되었습니다.\n다시 진행해 주세요.");
			history.back();
			return;
		}
		
		var form = $("#frm_order");
		var name = form.find("#name");
		var tel_middle = form.find("#tel_middle");
		var tel_last = form.find("#tel_last");
		var pay_purpose = form.find("#pay_purpose");
		
		// 이름을 입력하지 않았을 때
		if(!name.val()) {
			alert("이름을 입력해 주세요.");
			name.focus();
			return;
		}
		
		// 연락처를 입력하지 않았을 때
		if(!tel_middle.val()) {
			alert("연락처를 입력해 주세요.");
			tel_middle.focus();
			return;
			
		} else if(tel_middle.val().length != 4) { // 연락처가 올바르지 않을 때
			alert("연락처가 올바르지 않습니다.");
			tel_middle.focus();
			return;
		}
		
		if(!tel_last.val()) {
			alert("연락처를 입력해 주세요.");
			tel_last.focus();
			return;
			
		} else if(tel_last.val().length != 4) { // 연락처가 올바르지 않을 때
			alert("연락처가 올바르지 않습니다.");
			tel_last.focus();
			return;
		}
		
		// 신규 배송지 주소를 입력하지 않았을 때
		if($("#type_shipping").prop("checked") && $("#new").prop("checked") && !$("#post_code").val()) {
			alert("배송지 주소를 입력해주세요.");
			$("#post_code").focus();
			return;
		}
		
		// 개인정보 수집 및 이용에 동의하지 않았을 때
		if(!pay_purpose.prop("checked")) {
			alert("개인정보 수집 및 이용에 동의해 주세요.");
			pay_purpose.focus();
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "/order/order_process",
			data : form.serialize(),
			success : function(result) {
				if(result != 0) {	// 주문 요청 성공 시
					location.href="/order/order_finish.do?order_id=" + result;
					
				} else {	// 주문 요청 실패 시
					alert("주문 요청에 실패 하였습니다.");
					history.back();
				}
			}
		});
		
	}
	</script>
	
	<script src="/common/js/logout.js"></script>
	<script src="/common/js/input_len_constraints.js"></script>
	<script src="/common/js/show_term.js"></script>
</body>
</html>