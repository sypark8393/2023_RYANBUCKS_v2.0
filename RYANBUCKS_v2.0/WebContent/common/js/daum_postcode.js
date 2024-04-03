// 다음 우편번호 서비스(API)
function daumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
        	// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분

			// 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
			var roadAddr = data.roadAddress; // 도로명 주소 변수
			var extraRoadAddr = ''; // 참고 항목 변수

        	// 법정동명이 있을 경우 추가
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }
			
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y') {
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('post_code').value = data.zonecode;
            document.getElementById('road_address').value = roadAddr;
        	
            // 상세 주소를 초기화한다.
            document.getElementById('detail_address').value = '';
            
            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            /*
            if(roadAddr !== ''){
                document.getElementById('extra_address').value = extraRoadAddr;
            } else {
                document.getElementById('extra_address').value = '';
            }
            */
		}
	}).open();
}
