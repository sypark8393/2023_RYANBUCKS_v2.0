// 네비게이션 하위 카테고리 열고 닫기
$(document).on('click', 'nav.my_nav li:not(.myRnb_btn)', function() {
	var span_class = $(this).find('span').attr('class');

	if (span_class === 'arrow_down') { // 하위 목록이 접힌 상태인 경우
		$(this).find('span').removeClass('arrow_down').addClass('arrow_up');
		$(this).find('ul').slideDown(300);
		
	} else { // 하위 목록이 열린 상태인 경우
		$(this).find('span').removeClass('arrow_up').addClass('arrow_down');
		$(this).find('ul').slideUp(300);
	}

});