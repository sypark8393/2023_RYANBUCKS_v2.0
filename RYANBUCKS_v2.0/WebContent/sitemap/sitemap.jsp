<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>사이트맵 | Ryanbucks Korea</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/info.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="wrap">
	<jsp:include page="/header.jsp" />
	
	<!-- container -->
	<div id="container">
		
		<!-- 서브 타이틀 -->
		<header class="info_sub_tit_wrap">
			<div class="info_sub_tit_bg">
				<div class="info_sub_tit_inner">
					<h4>사이트 맵</h4>
					
					<ul class="smap">
						<li>
							<a href="/"><img src="/common/img/icon_home_w.png" alt="홈으로"></a>
						</li>
						<li>
							<img class="arrow" src="/common/img/icon_arrow_w.png" alt="하위메뉴">
						</li>
						<li class="en">
							<a href="/info/index.do">INFO</a>
						</li>
					</ul>
				</div>
			</div>
		</header>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="info_cont_wrap">
			<p class="sitemap_tit">라이언벅스 코리아 사이트 맵입니다.</p>
			
			<table class="sitemap">
				<tr>
					<th class="fth"><a href="/info/index.do">INFO</a></th>
					<td class="ftd">
						<ul class="sitemap">
							<li>
								<dl>
									<dt><a href="/info/sitemap.do">사이트 맵</a></dt>
								</dl>
							</li>
						</ul>
					</td>
				</tr>
				
				<tr>
					<th><a href="/coffee/index.do">COFFEE</a></th>
					<td>
						<ul class="sitemap">
							<li>
								<dl>
									<dt><a href="/coffee/story.do">커피 이야기</a>
								</dl>
							</li>
							<li>
								<dl>
									<dt><a href="/coffee/espresso.do">에스프레소 음료</a>
								</dl>
							</li>
						</ul>
					</td>
				</tr>
				
				<tr>
					<th><a href="/menu/index.do">MENU</a></th>
					<td>
						<ul class="sitemap">
							<li>
								<dl>
									<dt><a href="/menu/drink_list.do">음료</a>
								</dl>
							</li>
							<li>
								<dl>
									<dt><a href="/menu/food_list.do">푸드</a>
								</dl>
							</li>
						</ul>
					</td>
				</tr>
				
				<tr>
					<th><a href="/event/index.do">EVENT</a></th>
					<td>
						<ul class="sitemap">
							<li>
								<dl>
									<dt><a href="/event/event_list.do">이벤트</a></dt>
								</dl>
							</li>
						</ul>
					</td>
				</tr>
				
				<tr>
					<th ><a href="/notice/index.do">NOTICE</a></th>
					<td>
						<ul class="sitemap">
							<li>
								<dl>
									<dt><a href="/notice/notice_list.do">공지사항</a></dt>
								</dl>
							</li>
						</ul>
					</td>
				</tr>
				
				<tr>
					<th><a href="/login/login.do">LOGIN</a></th>
					<td>
						<ul class="sitemap">
							<li>
								<dl>
									<dt><a href="/login/login.do">로그인</a></dt>
								</dl>
							</li>
							<li>
								<dl>
									<dt><a href="/mem/join.do">회원가입</a></dt>
								</dl>
							</li>
							<li>
								<dl>
									<dt><a href="/mem/find_id.do">아이디 찾기</a></dt>
								</dl>
							</li>
							<li>
								<dl>
									<dt><a href="/mem/find_pwd.do">비밀번호 찾기</a></dt>
								</dl>
							</li>
						</ul>
					</td>
				</tr>
				
				<tr>
					<th scope="row"><a href="/my/index.do">MY<br>RYANBUCKS</a></th>
					<td>
						<ul class="sitemap">
							<li>
								<dl>
									<dt><a href="/my/order_list.do">주문내역</a></dt>	
								</dl>
							</li>
							<li>
								<dl>
									<dt><a href="/my/review.do">상품 리뷰</a></dt>
								</dl>
							</li>
							<li>
								<dl>
									<dt><a href="javascript:void(0);">개인정보관리</a></dt>
									<dd><a href="/my/myinfo_modify.do">- 개인정보 확인 및 수정 </a></dd>
									<dd><a href="/my/myinfo_out.do">- 회원 탈퇴 </a></dd>
									<dd><a href="/my/myinfo_modify_pwd.do">- 비밀번호 변경 </a></dd>
								</dl>
							</li>
						</ul>
					</td>
				</tr>
				
				<tr>
					<th><a href="/cart/cart.do">CART</a></th>
					<td>
						<ul class="sitemap">
							<li>
								<dl>
									<dt><a href="/cart/cart.do">장바구니</a></dt>
								
								</dl>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>

	<script src="/common/js/logout.js"></script>
</body>
</html>