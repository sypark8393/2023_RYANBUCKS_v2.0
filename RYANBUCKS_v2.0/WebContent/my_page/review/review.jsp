<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="dto.WriteableReviewsDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

@SuppressWarnings("unchecked")
List<WriteableReviewsDTO> reviewList = (List<WriteableReviewsDTO>) resultMap.get("reviewList");
@SuppressWarnings("unchecked")
List<String> menuThumFileNameList = (List<String>) resultMap.get("menuThumFileNameList");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>리뷰 작성 | Ryanbucks Korea</title>
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
					<h4>상품 리뷰</h4>
					
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
						<li>
							<img class="arrow" src="/common/img/icon_arrow_w.png" alt="하위메뉴">
						</li>
						<li>
							<a href="/my/review.do">상품 리뷰</a>
						</li>
					</ul>
				</div>
			</div>
		</header>
		<!-- // 서브 타이틀 -->
		
		<!-- 내용 -->
		<div class="my_cont_wrap">
			<div class="my_cont">
				<%if(reviewList.size() == 0) {%>
				<div class="no_list">
					<strong>작성 가능한 리뷰가 없습니다.</strong>
					<p>
						원하는 상품을 주문하고 리뷰를 작성해보세요!
					</p>
					
					<a href="/menu/index.do">쇼핑하러 가기</a>
				</div>
				
				<%} else { %>
				<div class="review_list">
					<ul class="info">
						<li>픽업 주문 건은 픽업일로부터 1일, 배송 주문 건은 주무일로부터 2일 후에 리뷰 작성이 가능합니다.</li>
					</ul>
					
					<table>
						<colgroup>
		                    <col width="15%">
		                    <col width="*%">
		                    <col width="16%">
                   	 	</colgroup>
						<tbody>
							<%
							for(int i=0; i<reviewList.size(); i++) {
								WriteableReviewsDTO writeableReviewsDto = reviewList.get(i);
							%>
							<tr>
								<td class="img">
									<a href="/menu/menu_view.do?no=<%=writeableReviewsDto.getMenuNo() %>"><img src="/img/menu/<%=menuThumFileNameList.get(i) %>"></a>
								</td>
								<td class="left">
									<a href="/menu/menu_view.do?no=<%=writeableReviewsDto.getMenuNo() %>"><strong><%=writeableReviewsDto.getNameKor() %></strong></a><br>
									<span><%=(writeableReviewsDto.getoptionInfo() != null)? writeableReviewsDto.getoptionInfo() : "" %></span>
								</td>
								<td>
									<input type="button" class="write" value="리뷰 작성" onclick="writeReview(<%=writeableReviewsDto.getNo() %>)">
								</td>
							</tr>
							<%} %>
						</tbody>
					</table>
				</div>
				<%} %>
			</div>
			
			<jsp:include page="/my_page/my_navigation.html" />
		</div>
		<!-- // 내용 -->
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script>
	function writeReview(no) {
		window.open("/my/write_review_popup.do?no=" + no, "write_review_popup", "width=450, height=530, top=280, left=765");
	}
	</script>
	
	<script src="/common/js/logout.js"></script>
	<script src="/common/js/my_nav.js"></script>
</body>
</html>