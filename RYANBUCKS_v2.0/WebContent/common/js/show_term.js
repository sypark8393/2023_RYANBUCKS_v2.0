// 약관 표시
function showTerm(term) {
	var modal = $(term);
	var closeBtn = $(term + " .close_button");
	
	modal.css("display", "block");	// 약관 내용을 화면에 보여주기
	
	closeBtn.on("click", function(event) {	// 닫기(x) 클릭 시 약관을 화면에서 숨김
		modal.css("display", "none");
	});
	
	$(window).on("click", function(event) { // 약관 동의 영역 밖을 클릭한 경우 화면에서 숨김
        if (event.target == modal[0]) {
            modal.css("display", "none");
        }
    });
}
