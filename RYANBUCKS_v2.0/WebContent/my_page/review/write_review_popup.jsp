<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>리뷰 작성</title>
	<link href="/common/img/logo.png" rel="short icon" type="image/ico">
	
	<link href="/common/css/reset.css" rel="stylesheet">
	<link href="/common/css/style.css" rel="stylesheet">
	<link href="/common/css/my.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="wrap">
		<div class="wrtie_review">
			<header>
				<h1>리뷰 작성</h1>
				<a href="javascript:window.close();" role="button">&times;</a>
			</header>
			
			<form id="frm_write_review" action="/my/write_review_process" method="post">
				<input type="hidden" name="no" value=<%=request.getParameter("no") %>>
			
				<div class="star">
					<strong>상품은 만족하셨나요?</strong>
					
					<fieldset>
						<input type="radio" name="reviewStar" value="5" id="rate1"><label for="rate1">⭐</label>
						<input type="radio" name="reviewStar" value="4" id="rate2"><label for="rate2">⭐</label>
						<input type="radio" name="reviewStar" value="3" id="rate3"><label for="rate3">⭐</label>
						<input type="radio" name="reviewStar" value="2" id="rate4"><label for="rate4">⭐</label>
						<input type="radio" name="reviewStar" value="1" id="rate5"><label for="rate5">⭐</label>
					</fieldset>
					
					<span>선택하세요.</span>
				</div>
				
				<div class="review">
					<strong>리뷰를 남겨주세요.</strong>
					
					<div class="review_text">
						<textarea class="content" name="content" id="content" cols="30" maxlength="1000" placeholder="솔직한 리뷰를 남겨주세요."></textarea>
						<span id="counter">(<span>0</span> / 1,000)</span>
					</div>
				</div>
				
				<div class="ctrl_btns">
					<div class="cancel">
						<a href="javascript:window.close();" role="button">취소</a>
					</div>
					
					<div class="regist">
						<a href="javascript:void(0);" class="disabled" role="button" onclick="regist()">등록</a>
					</div>
				</div>
			</form>
		</div>
		
		<div class="complete_wrap">
			<div>
				<strong>리뷰 작성 완료!</strong>
				<span>고객님의 소중한 리뷰 감사합니다.<br>더욱 만족을 드릴 수 있도록 노력하겠습니다.</span>
				
				<a href="javascript:void(0);" role="button" onclick="finish()">확인</a>
			</div>
		</div>
	</div>
	
	<script>
	$(document).ready(function () {
		// textarea에 키 입력 이벤트가 발생한 경우
		$('.content').keyup(function (e) {
			// 글자수 세기
			var count = $(this).val().length;
			
			// 글자수 표시
			$("#counter > span").text(count.toLocaleString('ko-KR'));
			
			checkAvailableRegist();
			
			if(count == 1000) {
				alert('최대 1000자까지 입력 가능합니다.');
			}
			
		});
		
		// 별점 입력 이벤트가 발생한 경우
		$("input[name='reviewStar']").change(function(){
			checkAvailableRegist();
		});
		
	})
	
	// 등록 버튼 활성화 가능 여부 검사
	function checkAvailableRegist() {
		var star = $("input[name='reviewStar']:checked").length;	// 별점 입력을 위한 radio 버튼 중 체크된 항목 개수
		var review = $("textarea[name='content']").val().length;	// 입력된 리뷰 글자수
		
		// 별점이 입력되었고 작성된 리뷰가 있는 경우 등록 버튼 활성화
		if(star != 0 && review != 0) {
			$("div.regist > a").removeClass("disabled");
		
		// 별점이 입력되지 않았거나 작성된 리뷰가 없는 경우 등록 버튼 비활성화
		} else {
			$("div.regist > a").addClass("disabled");
		}
		
	}
	
	function regist() {
		// 등록 버튼이 비활성화 상태인 경우 등록이 불가능
		if(!$("div.regist > a:not(.disabled)").text()) {
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "/my/write_review_process",
			data : $("#frm_write_review").serialize(),
			success : function(result) {
				if(result != 0) { // 리뷰 작성 성공 시
					$("div.complete_wrap").css("display", "block");
					
				} else { // 리뷰 작성 실패 시
					alert("리뷰 작성에 실패하였습니다.")
				}
			}
		});
	}
	
	function finish() {
		self.close();						// 팝업창 닫기
		opener.document.location.reload();	// 팝업창을 호출한 페이지 새로고침
	}
	</script>
</body>
</html>