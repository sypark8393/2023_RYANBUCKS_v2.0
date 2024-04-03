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
				<h2>에스프레소 음료</h2>
				
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
						<a href="/coffee/espresso.do" class="this">에스프레소 음료</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<section class="espresso_top_wrap">
			<article class="espresso_top_inner">
				<p class="espresso_img">
					<img src="/img/coffee/espresso/espresso_drink.jpg">
				</p>
				<dl class="espresso_info">
					<dt>
						“오늘날, 라이언벅스에 있어 꼼꼼하고 세심한 노력은 다양한 음료 메뉴만큼이나 중요합니다. ”
					</dt>
					<dd>
						<p>
							1983년 하워드 슐츠는 이탈리아 밀라노의 한 커피숍에서 카페라떼를 주문했습니다. 그는 바리스타의 정성 어린 손길로 제조된 에스프레소를 보면서,<br>
							자신이 주문한 음료를 만들기 위해서는 바리스타의 커피를 향한 열정과 진심이 가장 중요하다는 것을 느꼈습니다.<br>
							또한, 커피를 마시며 느끼는 감동과 여운은 커피 한 잔을 완성하기 위한 공동체의 세심한 노력이 담겨 있다는 것도 깨닫게 되었습니다.
						</p>
						<p class="last">
							오늘날 라이언벅스에게 커피를 향한 세심한 노력은 다양한 음료 메뉴만큼 중요합니다. <br>
							강렬한 에스프레소 풍미와 도피오 한 잔, 완벽하게 만들어진 크림 블렌드의 라떼 한 잔처럼 모든 커피 한 잔에는 우리의 열정이 담겨있습니다.
						</p>
					</dd>
				</dl>
			</article>
		</section>
		
		<section class="espresso_bottom_wrap">
			<article class="espresso_bottom_inner">
				<ul class="espresso_list">
					<li>
						<dl>
							<dt>
								<img src="/img/coffee/espresso/doppio.jpg" alt="doppio">
							</dt>
							<dd>
								도피오는 에스프레소를 2샷으로 추출한 음료입니다.<br>
								라이언벅스 음료의 가장 핵심이자 중요한 에스프레소입니다.
							</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>
								<img src="/img/coffee/espresso/espresso_macchiato.jpg" alt="espresso macchiato">
							</dt>
							<dd>
								에스프레소 마키아또는 깊고 강렬한 에스프레소 샷에<br>
								우유 거품이 살짝 올라간 음료입니다.
							</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>
								<img src="/img/coffee/espresso/americano.jpg" alt="americano">
							</dt>
							<dd>
								진한 에스프레소에 뜨거운 물을 부어 만든 아메리카노는<br>
								에스프레소의 진한 풍미와 원두의 다크한 맛을 느낄 수<br>
								있습니다.
							</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>
								<img src="/img/coffee/espresso/macchiato.jpg" alt="macchiato">
							</dt>
							<dd>
								마키아또는 라이언벅스 에스프레소, 스팀, 밀크, 부드러운<br>
								우유거품에 달콤한 드리즐로 마무리하여 달콤한 끝 맛을<br>
								느낄 수 있는 음료입니다.
							</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>
								<img src="/img/coffee/espresso/cappuccino.jpg" alt="cappuccino">
							</dt>
							<dd>
								두텁게 쌓은 우유거품 아래에 다크하고 진한 에스프레소가<br>
								숨어진 카푸치노는 바리스타의 숙련된 솜씨를 느낄 수 있는<br>
								음료입니다.
							</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>
								<img src="/img/coffee/espresso/latte.jpg" alt="latte">
							</dt>
							<dd>
								진한 에스프레소와 부드러운 스팀 밀크가 섞인 라떼는<br>
								더하고자 하는 어떠한 맛과도 잘 어울리는 음료입니다.
							</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>
								<img src="/img/coffee/espresso/mocha.jpg" alt="mocha">
							</dt>										
							<dd>
								모카는 달콤 쌉싸름한 초콜릿이 진한 에스프레소와 스팀 밀크에<br>
								혼합되어 달콤함이 부각되는 음료입니다.
							</dd>
						</dl>
					</li>
				</ul>
			</article>
		</section>
		<!-- // 내용 -->
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>

	<script src="/common/js/logout.js"></script>
</body>
</html>