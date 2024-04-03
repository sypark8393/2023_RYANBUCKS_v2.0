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
		
		<!-- login_wrap -->
		<div id="login_wrap">
			<h2>로그인</h2>
			
			<form id="frm_login" action="/login/login_process" method="post">
				<section>
					<p class="mem_guide">
						<img src="/img/mem/login_ryan.png">
						<span class="green">Welcome!</span>라이언벅스 코리아에 오신 것을 환영합니다.
					</p>
					
					<div class="input_box">
						<input id="id" name="id" type="text" maxlength="20" placeholder="아이디를 입력해 주세요.">
						<input id="password" name="password" type="password" maxlength="20" placeholder="비밀번호를 입력해 주세요." autocomplete="off">
						
						<a href="javascript:void(0);" role="submit" onclick="login()">로그인</a>
						
						<span class="green">
						* 타 사이트와 비밀번호를 동일하게 사용할 경우 도용의 위험이 있으므로, 정기적인 비밀번호 변경을 해주시길 바랍니다.<br>
						* 라이언벅스 코리아의 공식 홈페이지는 Chrome 브라우저에 최적화 되어있습니다.
						</span>
					</div>

					<div class="login_btn_wrap">
						<ul>
							<li><a href="/mem/join.do">회원가입</a></li>
							<li><a href="/mem/find_id.do">아이디 찾기</a></li>
							<li class="last"><a href="/mem/find_pwd.do">비밀번호 찾기</a></li>
						</ul>
					</div>
				</section>
			</form>
		</div>
		<!-- // login_wrap -->
	
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script>
	$(document).ready(function () {
		// form 내부의 input 요소에서 Enter키 클릭 시 로그인 처리를 위한 함수 호출
		$("#frm_login input").on("keydown", function(event) {
			if(event.keyCode === 13) {
				login();
			}
		});
	});
	
	function login() {
		var form = $("#frm_login");
		var id = form.find("#id");
		var password = form.find("#password");
		
		// 아이디를 입력하지 않았을 때
		if(!id.val()) {
			alert("아이디를 입력해 주세요.");
			id.focus();
			return;
		}

		// 비밀번호를 입력하지 않았을 때
		if(!password.val()) {
			alert("비밀번호를 입력해 주세요.");
			password.focus();
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "/login/login_process",
			data : form.serialize(),
			success : function(name) {
				if(name != "null") { // 로그인 성공 시
					alert(name + "님 환영합니다.");
					
					if('<%=request.getParameter("redirect_url") %>' === 'null') {
						location.href = "/index.do";
					
					} else {
						location.href = '<%=request.getParameter("redirect_url") %>';
					}

				} else { // 로그인 실패 시
					alert("아이디와 비밀번호를 정확하게 입력해 주세요.");
					
				}
			}
		});
	}
	</script>
	
	<script src="/common/js/logout.js"></script>
</body>
</html>