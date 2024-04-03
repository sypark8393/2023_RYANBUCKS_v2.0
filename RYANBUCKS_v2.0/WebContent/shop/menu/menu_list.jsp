<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.CategoryDTO"%>
<%@page import="dto.MenuDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

String type = resultMap.get("type").toString();
@SuppressWarnings("unchecked")
List<CategoryDTO> categoryList = (List<CategoryDTO>) resultMap.get("categoryList");
@SuppressWarnings("unchecked")
Map<String, List<MenuDTO>> menuMap = (Map<String, List<MenuDTO>>) resultMap.get("menuMap");
@SuppressWarnings("unchecked")
List<MenuDTO> newMenuList = (List<MenuDTO>) resultMap.get("newMenuList");
@SuppressWarnings("unchecked")
Map<Integer, String> menuImageMap = (Map<Integer, String>) resultMap.get("menuImageMap");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title><%=(type.equals("drink")? "음료" : "푸드") %> | Ryanbucks Korea</title>
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
				<h2><%=(type.equals("drink")? "음료" : "푸드") %></h2>
				
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
						<a href='/menu/<%=type %>_list.do' class='this'><%=(type.equals("drink")? "음료" : "푸드") %></a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="content_wrap">
			<!-- 분류 -->
			<div class="product_kind_wrap">
				<p class="tit">분류 보기</p>
				
				<div class="product_kind_btn">
					<a href="javascript:void(0)" role="button">
						<img src="/common/img/list_up_btn.png" alt="분류보기 메뉴 접기">
					</a>
				</div>
				
				<div class="product_toggle_wrap">
					<dl class="product_kind_tab">
						<!-- 카테고리별 -->
						<dt class="category">
							<a href="javascript:void(0)" id="category_tab" class="tab selected" role="button" title="카테고리별 보기">카테고리</a>
						</dt>
						<dd class="panel category_panel">
							<ul class="category_list">
								<li><input type="checkbox" id="all" checked><label for="all">전체 상품보기</label></li>
								
								<%for(CategoryDTO categoryDto : categoryList) {%>
								<li><input type="checkbox" id="<%=categoryDto.getId() %>"><label for="<%=categoryDto.getId() %>"><%=categoryDto.getName() %></label></li>
								<%} %>
							</ul>
						</dd>
						<!-- // 카테고리별 -->
						
						<!-- 테마별 -->
						<dt class="theme">
							<a href="javascript:void(0)" id="theme_tab" class="tab" role="button" title="테마별 보기">테마</a>
						</dt>
						<dd class="panel theme_panel" style="display: none;">
							<ul class="theme_list">
								<li>
									<img src="/img/menu/disney_theme.jpg" alt="디즈니 프로모션">
								</li>
							</ul>
						</dd>
						<!-- // 테마별 -->
					</dl>
				</div>
			</div>
			<!-- // 분류 -->
			
			<!-- 목록 -->
			<!-- 카테고리별 -->
			<div class="product_result_wrap product_result_category">
				<div class="product_list">
					<dl>
						<%
						for(CategoryDTO categoryDto : categoryList) {
						%>
						<dt class="<%=categoryDto.getId() %>"><%=categoryDto.getName() %></dt>
						<dd class="<%=categoryDto.getId() %>">
							<ul>
								<%
								List<MenuDTO> menuList = menuMap.get(categoryDto.getId());
								
								if(menuList != null) {
									for(MenuDTO menuDto : menuList) {
								%>
								<li>
									<dl>
										<dt>
											<a href="/menu/<%=type %>_view.do?no=<%=menuDto.getNo() %>">
												<img src="/img/menu/<%=menuImageMap.get(menuDto.getNo()) %>" alt="<%=menuDto.getNameKor() %>">
											</a>
										</dt>
										<dd><%=menuDto.getNameKor() %></dd>
									</dl>
								</li>
								<%
									}
								} else {
								%>
								<li class="empty">준비 중입니다.</li>
								<%
								}
								%>
							</ul>
						</dd>
						<%} %>
					</dl>
				</div>
			</div>
			<!-- // 카테고리별 -->
			
			<!-- 테마별 -->
			<div class="product_result_wrap product_result_theme" style="display: none;">
				<div class="product_list">
					<dl>
						<dd>
							<ul>
								<%for(MenuDTO menuDto : newMenuList) { %>
								<li>
									<dl>
										<dt>
											<a href="/menu/<%=type %>_view.do?no=<%=menuDto.getNo() %>">
												<img src="/img/menu/<%=menuImageMap.get(menuDto.getNo()) %>" alt="<%=menuDto.getNameKor() %>">
											</a>
										</dt>
										<dd><%=menuDto.getNameKor() %></dd>
									</dl>
								</li>
								<%} %>
							</ul>
						</dd>
					</dl>
				</div>
			</div>
			<!-- // 테마별 -->
			
			<!-- // 목록 -->
			
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>

	<script>
	$(document).ready(function () {
		// 분류 보기 메뉴 접기/펼치기 클릭 시
		$(".product_kind_btn a").on("click", function() {
			// 메뉴가 펼쳐진 상태라면
			if($(".product_toggle_wrap").css("display") === "block") {
				// 접기 상태로 변경
				$(".product_kind_btn a img").attr("src", "/common/img/list_down_btn.png")
				$(".product_kind_btn a img").attr("alt", "분류 보기 메뉴 펼치기")
				$(".product_toggle_wrap").css("display", "none");
			
			// 메뉴가 접힌 상태라면
			} else {
				// 펼쳐진 상태로 변경
				$(".product_kind_btn a img").attr("src", "/common/img/list_up_btn.png")
				$(".product_kind_btn a img").attr("alt", "분류 보기 메뉴 접기")
				$(".product_toggle_wrap").css("display", "block");
			}
			
		});
		
		// 카테고리 버튼 클릭 시
		$("#category_tab").on("click", function() {
			$(".tab").removeClass("selected");
			$(this).addClass("selected");
			
			$(".panel").css("display", "none");
			$(".product_result_wrap").css("display", "none");
			
			$(".category_panel").css("display", "block");
			$(".product_result_category").css("display", "block");
		});
		
		// 테마 버튼 클릭 시
		$("#theme_tab").on("click", function() {
			$(".tab").removeClass("selected");
			$(this).addClass("selected");
			
			$(".panel").css("display", "none");
			$(".product_result_wrap").css("display", "none");
			
			$(".theme_panel").css("display", "block");
			$(".product_result_theme").css("display", "block");
		});
		
		// 전체 상품 보기 체크박스
		$("#all").on("change", function() {
			$("ul.category_list li input[type=checkbox]:not(#all)").prop("checked", false);	// 모든 체크박스에 체크 해제
			
			// 전체 상품 보기가 선택된 경우 모든 카테고리 목록 표시
			if($(this).prop("checked") == true) {
				$(".product_result_category .product_list > dl > dd").css("display", "block");
				$(".product_result_category .product_list > dl > dt").css("display", "block");
			
			// 전체 상품 보기가 선택 해제된 경우 모든 카테고리 목록 숨김
			} else {
				$(".product_result_category .product_list > dl > dd").css("display", "none");
				$(".product_result_category .product_list > dl > dt").css("display", "none");
			}
			
		});
		
		// 카테고리 체크박스
		$("ul.category_list li input[type=checkbox]:not(#all)").on("change", function() {
			// 전체 상품 보기가 선택된 경우라면
			if($("#all").prop("checked") == true) {
				$("#all").prop("checked", false);	// 전체 상품 보기 체크박스에 체크 해제
				
				// 모든 카테고리 목록 숨김
				$(".product_result_category .product_list > dl > dd").css("display", "none");
				$(".product_result_category .product_list > dl > dt").css("display", "none");
			}
			
			// 선택된 카테고리 목록 표시
			if($(this).prop("checked") == true) {
				$(".product_result_category .product_list dl dd." + $(this).attr("id")).css("display", "block");
				$(".product_result_category .product_list dl dt." + $(this).attr("id")).css("display", "block");
			
			// 선택 해제된 카테고리 목록 숨김
			} else {
				$(".product_result_category .product_list dl dd." + $(this).attr("id")).css("display", "none");
				$(".product_result_category .product_list dl dt." + $(this).attr("id")).css("display", "none");
			}
		});
		
	});
	
	
	</script>
	<script src="/common/js/logout.js"></script>
</body>
</html>