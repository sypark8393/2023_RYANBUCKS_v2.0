<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.*"%>
<%@page import="utils.ReviewList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

int pageNum = Integer.parseInt(resultMap.get("pageNum").toString());
MenuDTO menuDto = (MenuDTO) resultMap.get("menuDto");
CategoryDTO categoryDto = (CategoryDTO) resultMap.get("categoryDto");
@SuppressWarnings("unchecked")
List<String> imageList = (List<String>) resultMap.get("imageList");
@SuppressWarnings("unchecked")
List<MenuOptionDTO> optionList = (List<MenuOptionDTO>) resultMap.get("optionList");
double averageRate = Double.parseDouble(resultMap.get("averageRate").toString());
int totalCount = Integer.parseInt(resultMap.get("totalCount").toString());
@SuppressWarnings("unchecked")
List<Integer> countByRateList = (List<Integer>) resultMap.get("countByRateList");
@SuppressWarnings("unchecked")
List<ReviewByMenuDTO> reviewList = (List<ReviewByMenuDTO>) resultMap.get("reviewList");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title><%=menuDto.getNameKor() %> | Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/menu.css" rel="stylesheet">
	
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
				<h2><%=categoryDto.getName() %></h2>
				
				<ul class="smap">
					<li>
						<a href="/"><img src="/common/img/icon_home.png" alt="홈으로"></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li class="en">
						<a href="/menu/index.do">MENU</a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<a href='/menu/<%=categoryDto.getType() %>_list.do'><%=(categoryDto.getType().equals("drink")? "음료" : "푸드") %></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<a href="javascript:void(0);"><%=categoryDto.getName() %></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<a href="javascript:void(0);" class="this"><%=menuDto.getNameKor() %></a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="content_wrap">
			<!-- 메뉴 정보 -->
			<div class="menu_info_wrap">
				<!-- 사진 -->
				<div class="menu_pic">
					<div class="big">
						<p>
							<a href="javascript:void(0);" tabindex="-1" aria-hidden="true" role="presentation">
								<img class="zoomImg" src="/img/menu/<%=imageList.get(0) %>" alt="상세이미지">
							</a>
						</p>
					</div>
					
					<div class="thumnails">
						<ul>
							<%
							for(String fileName : imageList) {
								out.println("<li>");
								out.println("<a href='javascript:void(0);' data-image='/img/menu/" + fileName + "' class='gallery'>");
								out.println("<img src='/img/menu/" + fileName + "' alt='상세이미지'>");
								out.println("</a>");
								out.println("</li>");
							}
							%>
						</ul>
					</div>
				</div>
				<!-- // 사진 -->
				
				<form id="frm_menu" action="/order/order_sheet.do" method="post">
					<input type="hidden" name="src" value="menu_view">
					<input type="hidden" name="menu_no" value="<%=menuDto.getNo() %>">
					<!-- 상세 정보 -->
					<div class="menu_detail">
						<!-- 메뉴명 & 설명 -->
						<div class="info">
							<h4>
								<%=menuDto.getNameKor() %><br>
								<span><%=menuDto.getNameEng() %></span>
							</h4>
						
							<p class="description">
								<%=menuDto.getDescription() %>
							</p>
						</div>
						<!-- // 메뉴명 & 설명 -->
						
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
						
						<%if(menuDto.getStock() == 0) { %>
						<!-- 품절 안내 -->
						<div class="sold_out_msg">
							<strong>이 메뉴는 현재 주문하실 수 없는 상품입니다.</strong><br>
							<span>(재입고 시 구매가능)</span>
						</div>
						
						<%} else {
							if(optionList.size() != 0) {
						%>
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
						<%
						}
						%>
						
						<!-- 수량 -->
						<div class="quantity">
							<input type="button" id="decrease" value="-" onclick="decreaseQuantity()" disabled>
							<input type="number" id="quantity" name="quantity" value="1" min="1" max="<%=menuDto.getStock() %>" readonly>
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
									<span class="info">총 주문 수량 <span>1</span>개</span><span class="price"><%=String.format("%,d", menuDto.getSalesPrice()) %></span><span class="won">원</span>
								</dd>
							</dl>
						</div>
						<!-- // 총 상품 금액 -->
						
						<div class="ctrl_btns">
							<div class="cart">
								<a href="javascript:void(0);" role="button" onclick="addCart()">장바구니</a>
							</div>
							
							<div class="order">
								<a href="javascript:void(0);" role="button" onclick="directOrder()">주문하기</a>
							</div>
						</div>
						<%} %>
					</div>
					<!-- // 상세 정보 -->
				</form>
			</div>
			<!-- // 메뉴 정보 -->
			
			<!-- 리뷰 -->
			<div class="review_wrap">
				<section>
					<header>
						<h4>
							리뷰<br>
							<span>실제 구매하신 분들이 작성하신 리뷰입니다.</span>
						</h4>
					</header>
					
					<%if(reviewList.size() == 0) {%>
					<div class="no_list">
						<strong>등록된 리뷰가 없습니다.</strong>
					</div>
					
					<%} else { %>
					<article>
						<!-- 리뷰 통계 -->
						<div class="statistics">
							<!-- 평균 평점 -->
							<div class="average_rate">
								<strong class="title">구매자 총 평점</strong>
								
								<div class="stcs_content">
									<div class="star">
				        				<span style="width:<%=averageRate*20 %>%;"></span>
					    			</div>
					    			<div class="rate">
					    				<span><%=averageRate %></span><span class="max">/5</span>
					    			</div>
								</div>
				    		</div>
				    		<!-- // 평균 평점 -->
				    		
				    		<!-- 리뷰 수 -->
				    		<div class="total_count">
				    			<strong class="title">전체 리뷰 수</strong>
				    			
				    			<div class="stcs_content">
				    				<img src="/img/review/icon_review.png">
				    				<span><%=totalCount %></span>
				    			</div>
				    		</div>
				    		<!-- // 리뷰 수 -->
				    		
				    		<!-- 별점 비율 -->
				    		<div class="rate_graph">
				    			<strong class="title">별점 비율</strong>
				    			
				    			<div class="stcs_content">
				    				<ul>
				    					<%
				    					for(int i=4; i>=0; i--) {
				    						out.println("<li>");
				    						out.println("<span class='count'>" + countByRateList.get(i) + "</span>");
				    						out.println("<div class='bar'>");
				    						out.println("<span style='height:" + ((countByRateList.get(i) * 1.0 / reviewList.size())*100) + "%;'></span>");
				    						out.println("</div>");
				    						out.println("<span class='label'>" + (i+1) + "점</span>");
				    						out.println("</li>");
					    				}
				    					%>
				    				</ul>
			    				</div>
			    			</div>
				    	<!-- // 별점 비율 -->
						</div>
						<!-- // 리뷰 통계 -->
						
						<!-- 리뷰 목록 -->
						<div class="review_list">
							<!-- 정렬 기준 -->
							<select id="review_sort" name="review_sort" onchange="changeSort()">
								<option value="latest" selected>최신순</option>
								<option value="highest">별점높은순</option>
								<option value="lowest">별점낮은순</option>
							</select>
							<!-- // 정렬 기준 -->
							
							<!-- 목록 -->
							<table>
								<colgroup>
				                    <col width="14%">
				                    <col width="*">
			                    </colgroup>
								<tbody>
									<%=ReviewList.getListString(reviewList)%>
								</tbody>
							</table>
							<!-- // 목록 -->
							
							<!-- 페이지 -->
							<div class="pagination">
								<ul class="pager">
									<%=ReviewList.getPagingString(application, totalCount, pageNum) %>
								</ul>
							</div>
							<!-- // 페이지 -->
						</div>
						<!-- // 리뷰 목록 -->
					</article>
					<%} %>
				</section>
			</div>
			<!-- // 리뷰 -->
								
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script>
	$(document).ready(function () {
		// 페이지 로드 시 자동으로 선택되어지는 옵션으로 결제 금액 정보 초기화
	    var selectOption = $("#option_no");
	    var selectedOption = selectOption.find(":selected");
	    var optionStock = Number(selectedOption.data('option-stock'));
	    var optionPrice = Number(selectedOption.data('option-price'));
		
	    // 옵션 정보가 없는 메뉴이면
	    if(isNaN(optionStock)) {
	    	optionStock = <%=menuDto.getStock() %>;
	    	optionPrice = 0;
	    }
	    
	    // 재고 수량에 따른 수량 증가 버튼 비활성화
	    if (optionStock === 1) {
	    	$(".quantity #increase").prop('disabled', true);
	    }
	    
	 	// 총 상품 금액
	    $(".total_price dd span.price").html((<%=menuDto.getSalesPrice()%> + optionPrice).toLocaleString('ko-KR'));
    	
		// 썸네일 클릭 시 클릭된 이미지를 확대 이미지 영역에 표시
		$(".gallery").on("click", function() {
			var path = $(this).attr("data-image");
			
			$(".zoomImg").attr("src", path);
			
		});
		
		// 페이지 영역의 요소를 클릭한 경우
		$(document).on("click", ".pager li", function() {
			var selectOption = $("#review_sort");
		    var selectedOption = selectOption.find(":selected");
		    
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
			    	url : "/menu/load_review_list_process",
			    	data : "no=" + <%=menuDto.getNo() %> + "&sort=" + selectedOption.val() + "&page_num=" + pageNum,
			    	dataType: 'json',
			    	success : function(result) {
			    		$('.review_list table tbody').html(result.list);
			    		$('.pager').html(result.paging);
			    	}
			    });
				
			// 페이지를 클릭한 경우
			} else {
				$.ajax({
					type : "POST",
					url : "/menu/load_review_list_process",
					data : "no=" + <%=menuDto.getNo() %> + "&sort=" + selectedOption.val() + "&page_num=" + $(this).find("a").html(),
					dataType: 'json',
					success : function(result) {
						$('.review_list table tbody').html(result.list);
						$('.pager').html(result.paging);
					}
				});
			}
			
		});
	});
	
	// 장바구니에 메뉴 담기
	function addCart() {
		if("<%=session.getAttribute("id") %>".toString() === 'null') { // 로그아웃 상태인 경우
			if(confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?")) {
				location.href = "/login/login.do?redirect_url=" + encodeURIComponent("/menu/<%=categoryDto.getType() %>_view.do?no=<%=menuDto.getNo() %>");
			}
			
			return;
		}
		
		var selectOption = $("#option_no");
	    var selectedOption = selectOption.find(":selected");
	    
	 	$.ajax({
	 		type : "POST",
	 		url : "/cart/add_process",
	 		data : "menu_no=" + <%=menuDto.getNo() %> + "&option_no=" + selectedOption.val() + "&quantity=" + $("#quantity").val(),
	 		success : function(result) {
	 			if(result != 0) { // 장바구니에 메뉴 담기 성공 시
	 				if(confirm("장바구니에 메뉴를 담았습니다.\n장바구니로 이동하시겠습니까?")) {
	 					location.href = "/cart/cart.do";
	 				}
	 				
	 			} else { // 장바구니에 메뉴 담기 실패 시
					alert("메뉴 담기에 실패하였습니다.");
					
				}
	 		}
	 	});
	}
	
	// 바로 주문하기
	function directOrder() {
		if("<%=session.getAttribute("id") %>".toString() === 'null') { // 로그아웃 상태인 경우
			if(confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?")) {
				location.href = "/login/login.do?redirect_url=" + encodeURIComponent("/menu/<%=categoryDto.getType() %>_view.do?no=<%=menuDto.getNo() %>");
			}
			
			return;
		}
		
		var form = $("#frm_menu");
		form.submit();
	}
	
	// 정렬 기준 변경
	function changeSort() {
		var selectOption = $("#review_sort");
	    var selectedOption = selectOption.find(":selected");
	    
	    $.ajax({
	    	type : "POST",
	    	url : "/menu/load_review_list_process",
	    	data : "no=" + <%=menuDto.getNo() %> + "&sort=" + selectedOption.val() + "&page_num=1",
	    	dataType: 'json',
	    	success : function(result) {
	    		$('.review_list table tbody').html(result.list);
	    		$('.pager').html(result.paging);
	    	}
	    });
	}
	</script>

	<script src="/common/js/logout.js"></script>
	<script src="/common/js/menu_qty_and_opt.js"></script>
</body>
</html>