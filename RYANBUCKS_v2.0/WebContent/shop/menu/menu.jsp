<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MENU | Ryanbucks Korea</title>
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
				<h2 class="en">MENU</h2>
				
				<ul class="smap">
					<li>
						<a href="/"><img src="/common/img/icon_home.png" alt="홈으로"></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li class="en">
						<a href="/menu/index.do" class="this">MENU</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<!-- 음료 -->
		<div class="menu_drink_section">
			<div class="menu_section_inner">
				<p class="menu_drink_txt">
					<img src="/img/menu/drink_txt.png">
				</p>
				<div class="menu_drink_btn">
					<a href="/menu/drink_list.do" title="라이언벅스 음료 자세히보기">자세히 보기</a>
				</div>
			</div>
		</div>
		<!-- // 음료 -->
		
		<!-- 푸드 -->
		<div class="menu_food_section">
			<div class="menu_section_inner">
				<p class="menu_food_txt">
					<img src="/img/menu/food_txt.png">
				</p>
				<div class="menu_food_btn">
					<a href="/menu/food_list.do" title="라이언벅스 푸드 자세히보기">자세히 보기</a>
				</div>
			</div>
		</div>
		<!-- // 푸드 -->
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>

	<script src="/common/js/logout.js"></script>
</body>
</html>