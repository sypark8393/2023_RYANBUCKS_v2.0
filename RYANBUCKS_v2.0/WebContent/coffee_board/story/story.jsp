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
				<h2>커피 이야기</h2>
				
				<ul class="smap">
					<li>
						<a href="/"><img src="/common/img/icon_home.png" alt="홈으로"></a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li class="en">
						<a href="/coffee/index.do">COFFEE</a>
					</li>
					<li>
						<img class="arrow" src="/common/img/icon_arrow.png" alt="하위메뉴">
					</li>
					<li>
						<a href="/coffee/story.do" class="this">커피 이야기</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="coffee_story">
			<dl class="content_tabmenu">
				<!-- 1번 -->
				<dt class="tab tab_01 on">
					<a href="javascript:void(0);">농장에서 우리의 손으로</a>
				</dt>
				<dd class="panel panel_01 on">
					<jsp:include page="tab_01_panel.html" />
				</dd>
				<!-- // 1번 -->
				
				<!-- 2번 -->
				<dt class="tab tab_02">
					<a href="javascript:void(0);">최상의 아라비카 원두</a>
				</dt>
				<dd class="panel panel_02">
					<jsp:include page="tab_02_panel.html" />
				</dd>
				<!-- // 2번 -->
				
				<!-- 3번 -->
				<dt class="tab tab_03">
					<a href="javascript:void(0);">라이언벅스 로스트 스펙트럼</a>
				</dt>
				<dd class="panel panel_03">
					<jsp:include page="tab_03_panel.html" />
				</dd>
				<!-- // 3번 -->
				
				<!-- 4번 -->
				<dt class="tab tab_04">
					<a href="javascript:void(0);">라이언벅스 디카페인</a>
				</dt>
				<dd class="panel panel_04">
					<jsp:include page="tab_04_panel.html" />
				</dd>
				<!-- // 4번 -->
			</dl>
			
		</div>
		<!-- // 내용 -->
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>

	<script>
	$(document).ready(function () {
		// 탭을 클릭한 경우
		$(".tab").on("click", function() {
			$(".tab").removeClass("on");			// 클래스명이 "tab"인 요소의 클래스에서 "on" 제거
			$(".panel").removeClass("on");			// 클래스명이 "panel"인 요소의 클래스에서 "on" 제거
			
			$(this).addClass("on");					// 클릭 이벤트가 발생한 요소의 클래스에 "on" 추가
			$(this).next("dd").addClass("on");		// 클릭 이벤트가 발생한 요소 바로 다음에 오는 <dd>요소의 클래스에 "on" 추가
			
		});
		
	});
	</script>
	<script src="/common/js/logout.js"></script>
</body>
</html>