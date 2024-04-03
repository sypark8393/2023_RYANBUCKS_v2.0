<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원탈퇴 | Ryanbucks Korea</title>
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
			<h2>회원탈퇴</h2>
			
			<form id="frm_out" action="/mem/out_process" method="post">
				<section>
					<p class="mem_guide">
						<img src="/img/mem/login_ryan.png">
						<span>회원탈퇴 절차 및 유의사항을 알려드립니다.</span>
					</p>
					<div class="out_term">
						<p class="tit">라이언벅스 회원탈퇴 약관</p>
						<p class="t1">이 약관은 라이언벅스 회원탈퇴 절차 및 조건을 규정합니다.</p>
						
						<p class="pri_tit">회원탈퇴 절차</p>
						<ul>
							<li>- 회원은 서비스 내 "회원탈퇴" 기능을 이용하여 탈퇴할 수 있습니다.</li>
							<li>- 탈퇴 시, 회원정보와 관련된 모든 데이터는 영구적으로 삭제됩니다.</li>
						</ul>
						
						<p class="pri_tit">회원탈퇴 시 유의사항</p>
						<ul>
							<li>- 회원탈퇴 시, 현재 진행 중인 서비스는 모두 중단되며, 이전 내역은 복구할 수 없습니다.</li>
							<li>- 탈퇴 이후 재가입을 원할 경우, 새로운 회원정보로 가입해야 합니다.</li>
							<li>- 회원은 탈퇴 전에 개인정보의 보안을 유지해야 합니다.</li>
							<li>- 탈퇴 후 발생하는 문제에 대한 책임은 회원 본인에게 있습니다.</li>
						</ul>	
								
						<p>
						본 약관은 언제든지 수정될 수 있으며, 수정 시 본 페이지에 공지됩니다.<br>
						회원은 변경된 약관을 숙지하고 동의하여야 다시 가입할 수 있습니다.
						</p>
					</div>
				</section>
				
				<div class="out_term_agree_wrap">
					<input type="checkbox" id="out_agreement" name="out_agreement">
					<label for="out_agreement">모든 회원탈퇴 절차 및 유의사항을 숙지하였으며, 이에 동의합니다.<span class="green">(필수)</span></label>
				</div>
				
				<div class="button_box">
					<a href="javascript:void(0);" role="submit" onclick="out()">탈퇴하기</a>
				</div>
			</form>
		</div>
		<!-- // 내용 -->
		
	</div>
	<!-- // container -->
	
	<jsp:include page="/footer.jsp" />
	</div>
	
	<script>
		// 회원탈퇴
		function out() {
			var form = $("#frm_out");
			var out_agreement = form.find("#out_agreement");
			
			// 회원탈퇴 절차 및 유의사항에 동의하지 않았을 때
			if(!out_agreement.prop("checked")) {
				alert("회원탈퇴 절차 및 유의사항에 동의해 주세요.");
				out_agreement.focus();
				return;
				
			}
			
			$.ajax({
				type : "POST",
				url : "/mem/out_process",
				data : form.serialize(),
				success : function(result) {
					if(result != 0) { // 회원탈퇴 성공 시
						alert("탈퇴 되었습니다.");
						location.href = "/login/login.do";
						
					} else { // 회원탈퇴 실패 시
						alert("탈퇴에 실패하였습니다.");
						
					}
				}
			});
		}
	</script>
	<script src="/common/js/logout.js"></script>
</body>
</html>