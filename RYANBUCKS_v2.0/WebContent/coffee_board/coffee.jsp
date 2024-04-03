<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>COFFEE | Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/coffee.css" rel="stylesheet">
	
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
				<h2 class="en">COFFEE</h2>
				
				<ul class="smap">
					<li>
						<a href="/"><img src="/common/img/icon_home.png" alt="홈으로"></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li class="en">
						<a href="/coffee/index.do" class="this">COFFEE</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<!-- 커피 이야기 -->
		<section class="coffee_stroy_wrap">
			<div class="coffee_wrap_inner">
				<p class="coffee_story_txt">
					<img src="/img/coffee/coffee_story_txt.png">
				</p>
				<div class="coffee_story_btn">
					<a href="/coffee/story.do">자세히 보기</a>
				</div>
			</div>
		</section>
		<!-- // 커피 이야기 -->
		
		<!-- 에스프레소 음료 -->
		<section class="coffee_espresso_wrap">
			<div class="coffee_wrap_inner">
				<p class="coffee_espresso_txt">
					<img src="/img/coffee/coffee_espresso_txt.png">
				</p>
				<div class="coffee_espresso_btn">
					<a href="/coffee/espresso.do">자세히 보기</a>
				</div>
			</div>
		</section>
		<!-- // 에스프레소 음료 -->
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>

	<script src="/common/js/logout.js"></script>
</body>
</html>