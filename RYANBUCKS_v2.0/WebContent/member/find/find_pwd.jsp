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
	<link href="/common/css/mem.css" rel="stylesheet">
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="wrap">
	<jsp:include page="/header.jsp" />
	
	<!-- container -->
	<div id="container">
	
		<!-- 내용 -->
		<div id="mem_wrap">
			<h2>비밀번호 찾기</h2>
			
			<form id="frm_find_password" action="/mem/find_pwd_process" method="post">
			
				<section>
					<p class="mem_guide">
						<img src="/img/mem/login_ryan.png">
						<strong>비밀번호가 기억나지 않으세요?</strong>
						<span>아이디와 이름, 연락처를 입력하시면 비밀번호를 확인 하실 수 있습니다.</span>
					</p>
					
					<div class="input_box">
						<label for="id"><strong>아이디</strong></label>
						<input id="id" name="id" type="text" maxlength="20" placeholder="아이디">
					</div>
					
					<div class="input_box">
						<label for="name"><strong>이름</strong></label>
						<input id="name" name="name" type="text" maxlength="10" placeholder="이름">
					</div>
					
					<div class="input_box border_none">
						<label for="tel_first"><strong>연락처</strong></label>
						
						<div class="tel">
							<select id="tel_first" name="tel_first">
							<option value="010">010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="019">019</option>
							</select>
							-
							<input id="tel_middle" name="tel_middle" type="number" maxlength="4" oninput="numberMaxLength(this);" placeholder="4자리">
							-
							<input id="tel_last" name="tel_last" type="number" maxlength="4" oninput="numberMaxLength(this);" placeholder="4자리">
						</div>
					</div>
				</section>
				
				<div class="button_box">
					<a href="javascript:void(0);" role="submit" onclick="findPassword()">비밀번호 찾기</a>
				</div>
			</form>
			
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script>
	$(document).ready(function () {
		// form 내부의 input 요소에서 Enter키 클릭 시 비밀번호 찾기 처리를 위한 함수 호출
		$("#frm_find_password input").on("keydown", function(event) {
			if(event.keyCode === 13) {
				findPassword();
			}
		});
	});
	
	function findPassword() {
		var form = $("#frm_find_password");
		var id = form.find("#id");
		var name = form.find("#name");
		var tel_middle = form.find("#tel_middle");
		var tel_last = form.find("#tel_last");
		
		// 아이디를 입력하지 않았을 때
		if(!id.val()) {
			alert("아이디를 입력해 주세요.");
			id.focus();
			return;
		}
		
		// 이름을 입력하지 않았을 때
		if(!name.val()) {
			alert("이름을 입력해 주세요.");
			name.focus();
			return;
			
		}
		
		// 연락처를 입력하지 않았을 때
		if(!tel_middle.val()) {
			alert("연락처를 입력해 주세요.");
			tel_middle.focus();
			return;
			
		} else if(tel_middle.val().length != 4) { // 연락처가 올바르지 않을 때
			alert("연락처가 올바르지 않습니다.");
			tel_middle.focus();
			return;
			
		}
		
		if(!tel_last.val()) {
			alert("연락처를 입력해 주세요.");
			tel_last.focus();
			return;
			
		} else if(tel_last.val().length != 4) { // 연락처가 올바르지 않을 때
			alert("연락처가 올바르지 않습니다.");
			tel_last.focus();
			return;
			
		}
		
		$.ajax({
			type : "POST",
			url : "/mem/find_pwd_process",
			data : form.serialize(),
			success : function(password) {
				if(password != "null") { // 비밀번호 찾기 성공 시
					console.log(password);
					
					var answer = confirm("고객님의 비밀번호를 연락처로 보내드렸습니다.\n로그인 페이지로 이동하시겠습니까?")
					
					if(answer) {
						location.href = "/login/login.do"
						
					}
					
				} else { // 비밀번호 찾기 실패 시
					alert("회원 정보가 존재하지 않습니다.");
				
				}
			}
		});
		
		
	}
	</script>
	
	<script src="/common/js/logout.js"></script>
	<script src="/common/js/input_len_constraints.js"></script>
</body>
</html>