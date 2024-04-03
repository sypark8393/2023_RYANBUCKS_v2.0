<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>My Ryanbucks | Ryanbucks Korea</title>
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
					<h4 class="en">My Ryanbucks</h4>
					
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
					</ul>
				</div>
			</div>
		</header>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="my_cont_wrap">
			<div class="my_cont">
				<section class="my_user_info">
					<article class="my_user_info_left">
						<div></div>
					</article>
					
					<article class="my_user_info_right">
						<p class="my_user_stat">
							<strong><%=session.getAttribute("name") %></strong>님,<br>안녕하세요.
						</p>
						
						<div class="my_user_stat_btns">
							<ul>
								<li><a href="/my/order_list.do">주문 내역</a></li>
								<li><a href="/my/review.do">상품 리뷰</a></li>
								<li class="btn_black"><a href="/my/myinfo_modify.do">개인정보 수정</a></li>
								<li class="btn_gray"><a href="/my/myinfo_modify_pwd.do">비밀번호 변경</a></li>
							</ul>
						</div>
					</article>
				</section>
			</div>
			
			<jsp:include page="my_navigation.html" />
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