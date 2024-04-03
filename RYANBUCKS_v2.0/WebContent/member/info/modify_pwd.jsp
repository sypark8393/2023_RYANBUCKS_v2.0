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
			<h2>비밀번호 변경</h2>
			
			<form id="frm_modify_pwd" action="/mem/modify_pwd_process" method="post">
				<section>
					<p class="mem_guide">
						<img src="/img/mem/login_ryan.png">
						<span>새로운 비밀번호로 변경 하실 수 있습니다.</span>
					</p>
					
					<div class="input_box">
						<strong>아이디</strong>
						<input id="id" name="id" type="text" maxlength="20" disabled>
					</div>
					
					<div class="input_box">
						<label for="user_password"><strong>현재 비밀번호</strong></label>
						<input id="user_password" name="user_password" type="password" maxlength="20" placeholder="비밀번호를 입력해 주세요." autoComplete="off">
						<p class="limit_txt" id="limit_user_password" style="display: none;"></p>
					</div>
					
					<div class="input_box border_none">
						<label for="new_password"><strong>새 비밀번호</strong></label>
						<input id="new_password" name="new_password" type="password" maxlength="20" placeholder="영문, 숫자, 특수문자를 혼합하여 8~20자리 이내로 입력해 주세요." autoComplete="off">
						<p class="limit_txt" id="limit_new_password" style="display: none;"></p>
						<input id="new_password_check" type="password" maxlength="20" placeholder="비밀번호를 다시 한번 입력해 주세요." autoComplete="off">
						<p class="limit_txt" id="limit_new_password_check" style="display: none;"></p>
					</div>
				</section>
				
				<div class="button_box">
					<a href="javascript:void(0);" role="submit" onclick="modifyPwd()">확인</a>
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
		// 아이디
		$("#id").attr("value", "<%=session.getAttribute("id")%>");
		
		// form 내부의 input 요소에서 Enter키 클릭 시 비밀번호 변경 처리를 위한 함수 호출
		$("#frm_modify_pwd input").on("keydown", function(event) {
			if(event.keyCode === 13) {
				modifyPwd();
			}
		});
		
		// 현재 비밀번호 검사
		$("#user_password").on("focusout", function() {
			// 입력된 내용이 없으면
			if(!$(this).val()) {
				$(this).addClass("input_warn");
				$("#limit_user_password").addClass("input_warn_text");
				$("#limit_user_password").text("비밀번호를 입력해 주세요.");
				
				$("#limit_user_password").css("display", "block");
			
			// 입력된 내용이 있으면
			} else {
				$(this).removeClass("input_warn");
				
				$("#limit_user_password").css("display", "none");
			}
			
		});
		
		// 비밀번호 유효성 검사
		$("#new_password").on("focusout", function() {
			// 비밀번호 정책: 영문(대소문자), 숫자, 특수문자(@$!%*?&)를 포함하여 8~20자
			var passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
			
			$("#limit_new_password").css("display", "block");
			
			// 비밀번호 정책에 위배되는 경우
			if (!passwordRegex.test($(this).val())) {
				$(this).addClass("input_warn");
				$("#limit_new_password").addClass("input_warn_text");
				$("#limit_new_password").text("영문, 숫자, 특수문자(@, $, !, %, *, ?, &)를 혼합하여 8~20자리 이내로 입력해 주세요.");
				
			// 사용가능한 비밀번호인 경우
			} else {
				$(this).removeClass("input_warn");
				$("#limit_new_password").removeClass("input_warn_text");
				$("#limit_new_password").text("사용가능한 비밀번호 입니다.");
			}
			
			// 비밀번호 확인 요소에 입력된 값이 있으면 focusout 이벤트 발생시킴
			if($("#new_password_check").val()) {
				$("#new_password_check").trigger("focusout");
			}
			
		});
		
		// 비밀번호 확인 검사
		$("#new_password_check").on("focusout", function() {
			var new_password = $("#new_password:not(.input_warn)").val();	// 사용 가능한 비밀번호를 입력한 상태일 때의 새 비밀번호
			
			// 새 비밀번호가 유효한 경우
			if(new_password) {
				$("#limit_new_password_check").css("display", "block");
				
				// 비밀번호가 일치하지 않은 경우
				if($(this).val() != new_password) {
					$(this).addClass("input_warn");
					$("#limit_new_password_check").addClass("input_warn_text");
					$("#limit_new_password_check").text("일치하지 않습니다.");
					
				// 비밀번호가 일치하는 경우
				} else {
					$(this).removeClass("input_warn");
					$("#limit_new_password_check").removeClass("input_warn_text");
					$("#limit_new_password_check").text("일치합니다.");
				}
				
			// 새 비밀번호가 유효하지 않은 경우
			} else {
				$("#limit_new_password_check").css("display", "none");
			}
			
		});
		
	});
	
	// 비밀번호 변경
	function modifyPwd() {
		var form = $("#frm_modify_pwd");
		var user_password = form.find("#user_password");
		var new_password = form.find("#new_password");
		var new_password_check = form.find("#new_password_check");
		
		// 현재 비밀번호를 입력하지 않았을 때
		if(!user_password.val()) {
			alert("현재 비밀번호를 입력해 주세요.");
			user_password.focus();
			return;
		}
		
		// 새 비밀번호를 입력하지 않았을 때
		if(!new_password.val()) {
			alert("새 비밀번호를 입력해 주세요.");
			new_password.focus();
			return;
		}
		
		// 새 비밀번호가 유효하지 않을 때
		if($("#new_password.input_warn").val()) {
			new_password.focus();
			return;
		}
		
		// 비밀번호 확인을 입력하지 않았을 때
		if(!new_password_check.val()) {
			alert("비밀번호를 다시 입력해 주세요.");
			new_password_check.focus();
			return;
		}
		
		// 비밀번호 확인이 유효하지 않을 때
		if($("#new_password_check.input_warn").val()) {
			new_password_check.focus();
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "/mem/modify_pwd_process",
			data : form.serialize(),
			success : function(result) {
				if(result == -1) { // 회원 조회 실패 시
					alert("비밀번호가 다릅니다.\n\n다시 입력해 주십시오.");
					$("#user_password").focus();
				
				} else if(result != 0) { // 비밀번호 변경 성공 시
					alert("비밀번호가 변경 되었습니다.");
					location.href = "/my/index.do";
					
				} else { // 비밀번호 변경 실패 시
					alert("비밀번호 변경에 실패 하였습니다.");
					
				}
			}
		});
	}
	
	</script>
	<script src="/common/js/logout.js"></script>
</body>
</html>