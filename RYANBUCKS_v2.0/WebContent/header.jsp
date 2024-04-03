<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- header -->
<div id="header">
	
	<!-- gnb_wrap -->
	<div class="gnb_wrap">

		<!-- sub gnb -->
		<div class="sub_gnb">
			<!-- 로고 -->
			<h1 id="logo">
				<a href="/index.do" title="라이언벅스 메인페이지">라이언벅스 코리아</a>
			</h1>
			<!-- // 로고 -->
			
			<!-- 유틸리티 네비게이션-->
			<nav class="util_nav">
				<ul>
					<%if(session.getAttribute("id") != null) { %>
					<!-- 로그인 상태 -->
					<li class="util_nav01 sign_out">
						<a href="javascript:void(0);" onclick="logout()">Sign Out</a>
					</li>
					<!-- // 로그인 상태 -->
					
					<%} else { %>
					<!-- 로그아웃 상태 -->
					<li class="util_nav01 sign_in">
						<a href="/login/login.do">Sign In</a>
					</li>
					
					<li class="util_nav02">
						<a href="/mem/join.do">Join</a>
					</li>
					<!-- // 로그아웃 상태 -->
					<%} %>
					
					<li class="util_nav03">
						<a href="/my/index.do">My Ryanbucks</a>
					</li>
				</ul>
			</nav>
			<!-- // 유틸리티 네비게이션-->
			
			<!-- 장바구니 -->
			<div class="btn_cart">
				<a href="/cart/cart.do" role="button">
					<img alt="장바구니" src="/common/img/icon_cart.png">
				</a>
			</div>
			<!-- // 장바구니 -->
		</div>
		<!-- // sub gnb -->
	
		<!-- gnb 네비게이션 -->
		<nav class="gnb_nav">
			<div class="gnb_nav_inner">
				<ul>
					<!-- INFO -->
					<li class="gnb_nav01">
						<h2>
							<a href="/info/index.do">INFO</a>
						</h2>
					</li>
					<!-- // INFO -->
					
					<!-- COFFEE -->
					<li class="gnb_nav02">
						<h2>
							<a href="/coffee/index.do">COFFEE</a>
						</h2>
					</li>
					<!-- // COFFEE -->
					
					<!-- MENU -->
					<li class="gnb_nav03">
						<h2>
							<a href="/menu/index.do">MENU</a>
						</h2>
					</li>
					<!-- //MENU -->
					
					<!-- EVENT -->
					<li class="gnb_nav04">
						<h2>
							<a href="/event/index.do">EVENT</a>
						</h2>
					</li>
					<!-- //EVENT -->
					
					<!-- NOTICE -->
					<li class="gnb_nav05">
						<h2>
							<a href="/notice/index.do">NOTICE</a>
						</h2>
					</li>
					<!-- // NOTICE -->
				</ul>
			</div>
		</nav>
		<!-- // gnb 네비게이션 -->
	
	</div>
	<!-- // gnb_wrap -->
	
</div>
<!-- // header -->