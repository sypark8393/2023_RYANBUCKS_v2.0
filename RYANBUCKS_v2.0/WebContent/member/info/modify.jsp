<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Map"%>
<%@page import="dto.MemberDTO"%>
<%@page import="dto.AddressDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
@SuppressWarnings("unchecked")
Map<String, Object> resultMap = (Map<String, Object>) request.getAttribute("resultMap");

MemberDTO memberDto = (MemberDTO) resultMap.get("memberDto");
AddressDTO addressDto = (AddressDTO) resultMap.get("addressDto");
if(addressDto == null) {	// javascript에서 NullException 예외를 막기 위해 addressDto가 null이면 새로 객체 생성
	addressDto = new AddressDTO();
}

String selectableLastBirthDate = LocalDate.now().toString();
%>

<html>
<head>
<meta charset="UTF-8">
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
			<h2>개인정보 확인 및 수정</h2>
			
			<form id="frm_modify" action="/mem/modify_process" method="post">
				<section>
					<p class="mem_guide">
						<img src="/img/mem/login_ryan.png">
						<span><span class="green"><%=session.getAttribute("name")%></span>님의 개인정보 입니다.</span>
					</p>
					
					<div class="input_box border_none">
						<strong>아이디</strong>
						<input id="id" name="id" type="text" maxlength="20" disabled>
					</div>
				</section>
				
				<section>
					<div class="input_box">
						<strong>이름</strong>
						<input id="name" name="name" type="text" disabled>
					</div>
					
					<div class="input_box">
						<strong>성별</strong>
						
						<div class="gender">
							<div id="gender_m">남</div>
							<div id="gender_f">여</div>
						</div>
					</div>
					
					<div class="input_box">
						<strong>생일</strong>
						<input id="birth" name="birth" type="date">
					</div>
					
					<div class="input_box">
						<strong>연락처</strong>
						<input id="tel" name="tel" type="text" disabled>
					</div>
					
					<div class="input_box">
						<label for="email_id"><strong>이메일</strong></label>
						
						<div class="email">
							<input id="email_id" name="email_id" type="text" maxlength=20 placeholder="최대 30자리">
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
							<input type="button" id="search_address" onclick="daumPostcode()" value="검색">
							<br>
			  				<input type="text" name="road_address" id="road_address" placeholder="도로명주소" readonly>
			  				<input type="text" name="detail_address" id="detail_address" maxlength=30 placeholder="상세주소(최대 30자리)">
						</div>
					</div>
				</section>
				
				<div class="join_terms_agree_wrap">
					<div>
						<input type="checkbox" id="mem_advertise" name="mem_advertise">
						<label for="mem_advertise"></label><a href="javascript:void(0);" onclick="showTerm('#mem_advertise_wrap')">광고성 정보 수신동의</a>
						<jsp:include page="/member/term/mem_advertise.html" />
					</div>
				</div>
				
				<div class="button_box">
					<a href="javascript:void(0);" role="submit" onclick="modify()">정보수정</a>
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
		// 아이디
		$("#id").attr("value", "<%=session.getAttribute("id")%>");
		
		// 이름
		$("#name").attr("value", "<%=memberDto.getName()%>");
		
		// 성별
		if(<%=memberDto.getGender().equals("M") %>) { // 남자인 경우
			$("#gender_m").addClass("checked");
		
		} else { // 여자인 경우
			$("#gender_f").addClass("checked");
		
		}
		
		// 생일
		$("#birth").attr("max", "<%=selectableLastBirthDate %>");	// 선택 가능한 날짜
		
		if(<%=(memberDto.getBirth() != null) %>) { // 저장된 생일 정보가 있는 경우
			$("#birth").attr("value", "<%=memberDto.getBirth() %>");
		
		}
		
		// 연락처
		$("#tel").attr("value", "<%=memberDto.getTel() %>");
		
		
		// 이메일
		if(<%=(memberDto.getEmail() != null) %>) { // 저장된 이메일 정보가 있는 경우
			// 아이디
			$("#email_id").attr("value", "<%=memberDto.getEmail() %>".split('@')[0]);
		
			// 도메인
			$("#email_domain option").each(function() {								// 선택 가능한 도메인 옵션에 순차적으로 접근하면서
			    if ($(this).val() === "<%=memberDto.getEmail() %>".split('@')[1]) {	// 일치하는 도메인 탐색
			      $(this).prop("selected", true);
			      return;
			    }
			});
		}
		
		// 주소
		if(<%=(addressDto.getPostCode() != null) %>) { // 저장된 주소가 있는 경우
			$("#post_code").attr("value", "<%=addressDto.getPostCode() %>");
			$("#road_address").attr("value", "<%=addressDto.getRoadAddress() %>");
			$("#detail_address").attr("value", "<%=addressDto.getDetailAddress() %>");
		}
		
		// 광고성 정보 수신에 동의
		if(<%=(memberDto.getMarketingInfoAgree().equals("Y")) %>) { // 광고성 정보 수신에 동의한 경우
			$("#mem_advertise").attr("checked", true);
		}
		
	});
	
	// 개인정보 수정
	function modify() {
		var form = $("#frm_modify");
		
		$.ajax({
			type : "POST",
			url : "/mem/modify_process",
			data : form.serialize(),
			success : function(result) {
				if(result != 0) { // 개인정보 수정 성공 시
					alert("개인정보가 변경 되었습니다.");
					location.href = "/my/index.do";
					
				} else { // 개인정보 수정 실패 시
					alert("개인정보 변경에 실패 하였습니다.");
				}
			}
		});
		
	}
	</script>
	
	<script src="/common/js/logout.js"></script>
	<script src="/common/js/show_term.js"></script>
</body>
</html>