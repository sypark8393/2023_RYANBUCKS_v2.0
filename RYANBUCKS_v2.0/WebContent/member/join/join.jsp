<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String selectableLastBirthDate = LocalDate.now().toString();
%>

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
			<h2>회원가입</h2>
		
			<form id="frm_join" action="/mem/join_process" method="post">
				<section>
					<p class="mem_guide">
						<img src="/img/mem/login_ryan.png">
						<span>회원가입을 위한 정보 입력 및 약관 동의를 진행합니다.</span>
					</p>
	
					<div class="input_box">
						<input id="id" name="id" type="text" maxlength="20" placeholder="영문 소문자, 숫자를 혼합하여 6~20자리 이내로 입력해 주세요.">
						<p class="limit_txt" id="limit_id" style="display: none;"></p>
					</div>
					
					<div class="input_box">
						<input id="password" name="password" type="password" maxlength="20" placeholder="영문, 숫자, 특수문자를 혼합하여 8~20자리 이내로 입력해 주세요." autoComplete="off">
						<p class="limit_txt" id="limit_password" style="display: none;"></p>
					</div>
					
					<div class="input_box border_none">
						<input id="password_check" type="password" maxlength="20" placeholder="비밀번호를 다시 한번 입력해 주세요." autoComplete="off">
						<p class="limit_txt" id="limit_password_check"></p>
					</div>
				</section>
				
				<section>
					<div class="input_box">
						<label for="name"><strong>이름<span class="green">(필수)</span></strong></label>
						<input id="name" name="name" type="text" maxlength="10" placeholder="이름" autocomplete="off">
					</div>
					
					<div class="input_box">
						<strong>성별<span class="green">(필수)</span></strong>
						
						<div class="gender">
							<input id="gender_m" name="gender" type="radio" value="M" checked><label for="gender_m">남</label>
							<input id="gender_f" name="gender" type="radio" value="F"><label for="gender_f">여</label>
						</div>
					</div>
					
					<div class="input_box">
						<strong>생일<span class="green"></span></strong>
						<input name="birth" type="date" max="<%=selectableLastBirthDate %>">
					</div>
					
					<div class="input_box">
						<label for="tel_first"><strong>연락처<span class="green">(필수)</span></strong></label>
						
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
					
					<div class="input_box">
						<label for="email_id"><strong>이메일</strong></label>
						
						<div class="email">
							<input id="email_id" name="email_id" type="text" maxlength=20 placeholder="최대 20자리">
							@
							<select name="email_domain" id="email_domain">
								<option value="naver.com">naver.com</option>
								<option value="gmail.com">gmail.com</option>
								<option value="hanmail.net">hanmail.net</option>
							</select>
						</div>
					</div>
					
					<div class="input_box border_none">
						<strong>주소</strong>
						
						<div class="address">
							<input type="text" name="post_code" id="post_code" placeholder="우편번호" readonly>
							<a href="javascript:void(0);" id="search_address" role="button" onclick="daumPostcode()">검색</a>
							<br>
			  				<input type="text" name="road_address" id="road_address" placeholder="도로명주소" readonly>
			  				<input type="text" name="detail_address" id="detail_address" maxlength=30 placeholder="상세주소(최대 30자리)">
						</div>
					</div>
				</section>
				
				<div class="join_terms_agree_wrap">
					<div>
						<input type="checkbox" id="mem_agreement" name="mem_agreement">
						<label for="mem_agreement"></label><a href="javascript:void(0);" onclick="showTerm('#mem_agreement_wrap')">홈페이지 이용약관 동의<span class="green">(필수)</span></a>
						<jsp:include page="/member/term/mem_agreement.html" />
					</div>
					<div>
						<input type="checkbox" id="mem_purpose" name="mem_purpose">
						<label for="mem_purpose"></label><a href="javascript:void(0);" onclick="showTerm('#mem_purpose_wrap')">개인정보 수집 및 이용 동의<span class="green">(필수)</span></a>
						<jsp:include page="/member/term/mem_purpose.html" />
					</div>
					<div>
						<input type="checkbox" id="mem_advertise" name="mem_advertise">
						<label for="mem_advertise"></label><a href="javascript:void(0);" onclick="showTerm('#mem_advertise_wrap')">광고성 정보 수신 동의</a>
						<jsp:include page="/member/term/mem_advertise.html" />
					</div>
				</div>
				
				<div class="button_box">
					<a href="javascript:void(0);" role="submit" onclick="join()">가입하기</a>
				</div>
			</form>
		</div>
		<!-- // 내용 -->

	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="/common/js/daum_postcode.js"></script>

	<script>
	$(document).ready(function () {
		// form 내부의 input 요소에서 Enter키 클릭 시 회원가입 처리를 위한 함수 호출
		$("#frm_join input").on("keydown", function(event) {
			if(event.keyCode === 13) {
				join();
			}
		});
		
		// 아이디 유효성 검사
		$("#id").on("focusout", function() {
			// 아이디 정책: 영문(소문자), 숫자를 포함하여 6~20자
			var idRegex = /^[a-z0-9]{6,20}$/;
			
			$("#limit_id").css("display", "block");
			
			// 아이디 정책에 위배되는 경우
			if (!idRegex.test($(this).val())) {
				$(this).addClass("input_warn");
				$("#limit_id").addClass("input_warn_text");
				$("#limit_id").text("영문 소문자, 숫자를 혼합하여 6~20자리 이내로 입력해 주세요.");
			
			// 아이디 정책에 위배되지 않는 경우
			} else {
				// 아이디 사용 가능 여부 확인
				$.ajax({
					type : "POST",
					url : "/mem/check_id_process",
					data : "id=" + $(this).val(),
					success : function(result) {
						if(result != 0) { // 사용 가능한 아이디인 경우
							$("#id").removeClass("input_warn");
							$("#limit_id").removeClass("input_warn_text");
							$("#limit_id").text("사용 가능한 아이디 입니다.");
							
						} else { // 사용 불가능한 아이디인 경우
							$("#id").addClass("input_warn");
							$("#limit_id").addClass("input_warn_text");
							$("#limit_id").text("사용 불가능한 아이디 입니다.");
						}
					}
				});
			}
			
		});
		
		// 비밀번호 유효성 검사
		$("#password").on("focusout", function() {
			// 비밀번호 정책: 영문(대소문자), 숫자, 특수문자(@$!%*?&)를 포함하여 8~20자
			var passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
			
			$("#limit_password").css("display", "block");
			
			// 비밀번호 정책에 위배되는 경우
			if (!passwordRegex.test($(this).val())) {
				$(this).addClass("input_warn");
				$("#limit_password").addClass("input_warn_text");
				$("#limit_password").text("영문 대/소문자, 숫자, 특수문자(@, $, !, %, *, ?, &)를 혼합하여 8~20자리 이내로 입력해 주세요.");
				
			// 사용가능한 비밀번호인 경우
			} else {
				$(this).removeClass("input_warn");
				$("#limit_password").removeClass("input_warn_text");
				$("#limit_password").text("사용가능한 비밀번호 입니다.");
			}
			
			// 비밀번호 확인 요소에 입력된 값이 있으면 focusout 이벤트 발생시킴
			if($("#password_check").val()) {
				$("#password_check").trigger("focusout");
			}
			
		});
		
		// 비밀번호 확인 검사
		$("#password_check").on("focusout", function() {
			var new_password = $("#password:not(.input_warn)").val();	// 사용 가능한 비밀번호를 입력한 상태일 때의 새 비밀번호
			
			// 비밀번호가 유효한 경우
			if(new_password) {
				$("#limit_password_check").css("display", "block");
				
				// 비밀번호가 일치하지 않은 경우
				if($(this).val() != new_password) {
					$(this).addClass("input_warn");
					$("#limit_password_check").addClass("input_warn_text");
					$("#limit_password_check").text("일치하지 않습니다.");
					
				// 비밀번호가 일치하는 경우
				} else {
					$(this).removeClass("input_warn");
					$("#limit_password_check").removeClass("input_warn_text");
					$("#limit_password_check").text("일치합니다.");
				}
				
			// 비밀번호가 유효하지 않은 경우
			} else {
				$("#limit_password_check").css("display", "none");
			}
			
		});
	});
	
	// 회원가입
	function join() {
		var form = $("#frm_join");
		var id = form.find("#id");
		var password = form.find("#password");
		var password_check = form.find("#password_check");
		var name = form.find("#name");
		var tel_middle = form.find("#tel_middle");
		var tel_last = form.find("#tel_last");
		var mem_agreement = form.find("#mem_agreement");
		var mem_purpose = form.find("#mem_purpose");
		
		// 아이디를 입력하지 않았을 때
		if(!id.val()) {
			alert("아이디를 입력해 주세요.");
			id.focus();
			return;
		}
		
		// 아이디가 유효하지 않을 떄
		if($("#id.input_warn").val()) {
			id.focus();
			return;
		}
		
		// 비밀번호를 입력하지 않았을 때
		if(!password.val()) {
			alert("비밀번호를 입력해 주세요.");
			password.focus();
			return;
		}
		
		// 비밀번호가 유효하지 않을 떄
		if($("#password.input_warn").val()) {
			password.focus();
			return;
		}
		
		// 비밀번호 확인을 입력하지 않았을 때
		if(!password_check.val()) {
			alert("비밀번호를 다시 입력해 주세요.");
			password_check.focus();
			return;
		}
		
		// 비밀번호 확인이 유효하지 않을 때
		if($("#password_check.input_warn").val()) {
			password_check.focus();
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

		// 홈페이지 이용약관에 동의하지 않았을 때
		if(!mem_agreement.prop("checked")) {
			alert("홈페이지 이용약관에 동의해 주세요.");
			mem_agreement.focus();
			return;
		}
		
		// 개인정보 수집 및 이용에 동의하지 않았을 때
		if(!mem_purpose.prop("checked")) {
			alert("개인정보 수집 및 이용에 동의해 주세요.");
			mem_purpose.focus();
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "/mem/join_process",
			data : form.serialize(),
			success : function(result) {
				if(result != 0) { // 회원가입 성공 시
					alert("가입 되었습니다.");
					location.href = "/login/login.do";
					
				} else { // 회원가입 실패 시
					alert("가입에 실패하였습니다.");
				}
			}
		});
		
	}
	</script>
	
	<script src="/common/js/logout.js"></script>
	<script src="/common/js/input_len_constraints.js"></script>
	<script src="/common/js/show_term.js"></script>
</body>
</html>