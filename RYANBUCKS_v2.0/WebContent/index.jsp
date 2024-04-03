<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/index.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="wrap">
	<jsp:include page="/header.jsp" />
	
	<!-- container -->
	<div id="container">
		
		<!-- top_wrap -->
		<div id="top_wrap">
			<div class="slogan">
				<img src="/img/main/2023_disney2_top_logo.png" alt="LOVE ME" class="pc-slogan">
			</div>
			
			<div class="btn_detail">
				<a href="/event/event_view.do?no=1">자세히 보기</a>
			</div>
			
			<div class="set">
				<div class="set_01" style="opacity: 1;">
					<img src="/img/main/2023_disney2_top_drink1.png" alt="미니 블루베리 요거트 프라푸치노" class="pc-drink">
				</div>
				<div class="set_02" style="opacity: 1;">
					<img src="/img/main/2023_disney2_top_drink2.png" alt="블랙 글레이즈드 라떼" class="pc-drink">
				</div>
				<div class="set_03" style="opacity: 1;">
					<img src="/img/main/2023_disney2_top_drink3.png" alt="마롱 헤이즐넛 라떼" class="pc-drink">
				</div>
			</div>
		</div>
		<!-- // top_wrap -->

		<% if(session.getAttribute("id") != null) { %>
		<!-- 로그인 상태 -->
		<!-- line_msr_wrap -->
		<div id="line_msr_wrap">
			<div class="line_msr_wrap_inner">
				<div class="logo">
					<img src="/img/main/icon_msr_cup.png">
				</div>
				
				<div class="txt">
					<span class="user_greet_txt"><strong><%=session.getAttribute("name") %></strong>님, 안녕하세요!</span>
					<span>혜택에 편리함까지 더한 라이언벅스 리워드를 즐겨보세요.</span>
				</div>
			</div>
		</div>
		<!-- // line_msr_wrap -->
		<!-- // 로그인 상태 -->
		
		<%} else { %>
		<!-- 로그아웃 상태 -->
		<!-- new-rewards_wrap -->
		<section id="new-rewards_wrap">
			<div class="new-rewards_wrap_inner">
				<div class="logo">
					<img src="/img/main/rewards-logo.png" alt="Ryanbucks Rewards">
				</div>
				
				<div class="contents">
					<div class="txt">
						<h2>
							라이언벅스만의 특별한 혜택, <strong>라이언벅스 리워드</strong>
						</h2>
						<p>
							<strong>라이언벅스 회원이세요?</strong> 로그인을 통해 나만의 리워드를 확인해보세요.<br>
							<strong>라이언벅스 회원이 아니세요?</strong> 가입을 통해 리워드 혜택을 즐기세요.
						</p>
					</div>
					
					<div class="buttons">
						<a href="/mem/join.do" class="btn_join">회원가입</a>
						<a href="/login/login.do" class="btn_login">로그인</a>
					</div>
				</div>
			</div>
		</section>
		<!-- // new-rewards_wrap -->
		<!-- // 로그아웃 상태 -->
		<%} %>
		
		<!-- fav_wrap -->
		<section id="fav_wrap">
			<div class="fav_wrap_inner">
				<div class="fav_prod_txt01">PICK YOUR FAVORITE</div>
				<div class="fav_prod_txt02">
					"다양한 메뉴를 라이언벅스에서 즐겨보세요. 라이언벅스만의 특별함을 겸험할 수 있는 최상의 선택 음료,
					라이언벅스 커피와 완벽한 어울림을 자랑하는 푸드, 다양한 시도와 디자인으로 가치를 더하는 상품"
				</div>
				<div class="fav_img"></div>
				<div class="btn_fav_prod">
					<a href="/menu/index.do">자세히 보기</a>
				</div>
			</div>
		</section>
		<!-- // fav_wrap -->

	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>

	<script src="/common/js/logout.js"></script>
</body>
</html>