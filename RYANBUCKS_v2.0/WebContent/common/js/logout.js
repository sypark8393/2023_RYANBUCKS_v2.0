// 로그아웃
function logout() {
	$.ajax({
		url : "/login/logout_process",
		success : function() {
			alert("로그아웃 되었습니다.");
			location.href = "/login/login.do";
		}
	});
}