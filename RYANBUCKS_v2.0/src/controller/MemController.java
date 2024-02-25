package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.JDBConnect;
import dao.AddressDAO;
import dao.MemberDAO;
import dto.AddressDTO;
import dto.MemberDTO;

@WebServlet("/mem/*")
public class MemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("MemServlet init 메소드 호출");
	}

	public void destroy() {
		System.out.println("MemServlet destory 메소드 호출");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String action = request.getPathInfo();		// URL에서 요청명을 가져옴
		System.out.println("====================> /mem" + action);
		
		// 세션 얻어오기
		HttpSession session = request.getSession();
		
		// 회원가입
		if(action.equals("/join.do")) {
			request.getRequestDispatcher("/member/join/join.jsp").forward(request, response);
		
		// 아이디 사용 가능 여부 확인 프로세스
		} else if(action.equals("/check_id_process")) {
			String id = request.getParameter("id");
			
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(request.getServletContext());
			
			// 데이터 처리
			boolean result = memberDao.isAvailableId(id);
			
			// DB 연결 해제
			memberDao.close();
			
			// 사용 가능한 아이디인 경우
			if(result) {
				System.out.println("사용 가능한 아이디: " + id);
				
				response.getWriter().print(1);
			
			// 사용 불가능한 아이디인 경우
			} else {
				System.out.println("사용 불가능한 아이디: " + id);
				
				response.getWriter().print(0);
			}
			
		// 회원가입 프로세스
		} else if(action.equals("/join_process")) {
			java.sql.Date birth = null;

			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			String tel = request.getParameter("tel_first") + "-" + request.getParameter("tel_middle") + "-" + request.getParameter("tel_last");
			String strBirth = request.getParameter("birth");
			String email = request.getParameter("email_id") + "@" + request.getParameter("email_domain");
			String postCode = request.getParameter("post_code");
			String roadAddress = request.getParameter("road_address");
			String detailAddress = request.getParameter("detail_address");
			String marketingInfoAgree = request.getParameter("mem_advertise");
			
			// 선택 항목 데이터 처리
			// 생일이 입력되지 않은 경우
			if(strBirth.equals("")) {
				strBirth = null;
			
			// 생일이 입력된 경우
			} else {
				birth = java.sql.Date.valueOf(strBirth);
			}
			
			// 이메일이 입력되지 않은 경우
			if(email.charAt(0) == '@') { 
				email = null;
			}
			
			// 주소가 입력되지 않은 경우
			if(postCode.equals("")) {
				postCode = null;
				roadAddress = null;
				detailAddress = null;
			}
			
			// 광고성 정보 수신에 동의하지 않은 경우
			if(marketingInfoAgree == null) { 
				marketingInfoAgree = "N";
			
			// 광고성 정보 수신에 동의한 경우
			} else { 
				marketingInfoAgree = "Y";
				
			}
			
			/*
			System.out.println("id: " + id);
			System.out.println("password: " + password);
			System.out.println("name: " + name);
			System.out.println("gender: " + gender);
			System.out.println("tel: " + tel);
			System.out.println("birth: " + strBirth);
			System.out.println("email: " + email);
			System.out.println("post_code: " + postCode);
			System.out.println("road_address: " + roadAddress);
			System.out.println("detail_address: " + detailAddress);
			System.out.println("marketing_info_agree: " + marketingInfoAgree);
			*/
			
			// 데이터 전송을 위한 MemberDTO 객체
			MemberDTO memberDto = new MemberDTO();
			memberDto.setId(id);
			memberDto.setPassword(password);
			memberDto.setName(name);
			memberDto.setGender(gender);
			memberDto.setBirth(birth);
			memberDto.setTel(tel);
			memberDto.setEmail(email);
			memberDto.setMarketingInfoAgree(marketingInfoAgree);

			// 데이터 전송을 위한 AddressDTO 객체
			AddressDTO addressDto = new AddressDTO();
			addressDto.setId(id);
			addressDto.setName("기본 주소지");
			addressDto.setPostCode(postCode);
			addressDto.setRoadAddress(roadAddress);
			addressDto.setDetailAddress(detailAddress);
			
			// Oracle DB 연결
			JDBConnect jdbc = new JDBConnect(request.getServletContext());
			jdbc.setAutoCommit(false);	// 오류 발생 시 rollback 하기 위해 auto-commit 해제
			
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(jdbc.getConnection());
			AddressDAO addressDao = new AddressDAO(jdbc.getConnection());
			
			// 데이터 처리
			int result = memberDao.insert(memberDto); 		// 회원 데이터 추가
			
			if(result != 0 && postCode != null) {			// 회원 데이터 추가에 성공했고, 추가해야할 주소가 있는 경우
				result =  addressDao.insert(addressDto);	// 주소 데이터 추가
				
			}
			
			// 데이터 추가에 성공한 경우 commit
			if(result != 0) { 		
				jdbc.commit();
			
			// 데이터 추가에 실패한 경우 rollback
			} else { 				
				jdbc.rollback();
				
			}
			
			// DB 연결 해제
			memberDao.close();
			addressDao.close();
			jdbc.close();
			
			// 회원가입 성공 시
			if(result != 0) { 
				System.out.println("회원가입 성공: " + id);
				
				response.getWriter().print(1);
			
			// 회원가입 실패 시
			} else {
				System.out.println("회원가입 실패: " + id);
				
				response.getWriter().print(0);
			}
		
		// 아이디 찾기
		} else if(action.equals("/find_id.do")) {
			request.getRequestDispatcher("/member/find/find_id.jsp").forward(request, response);
			
		// 아이디 찾기 프로세스
		} else if(action.equals("/find_id_process")) {
			String name = request.getParameter("name");
			String tel = request.getParameter("tel_first") + "-" + request.getParameter("tel_middle") + "-" + request.getParameter("tel_last");
			
			//System.out.println("name: " + name);
			//System.out.println("tel: " + tel);
			
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(request.getServletContext());
			
			// 데이터 처리
			String id = memberDao.selectId(name, tel);	// 아이디 조회
			
			// DB 연결 해제
			memberDao.close();
			
			if(id != null) { // 아이디 찾기 성공 시
				System.out.println("아이디 찾기 성공: " + name);
				
				response.getWriter().print(id);
				
			} else { // 아이디 찾기 실패 시
				System.out.println("아이디 찾기 실패: " + name);
				
				response.getWriter().print("null");
				
			}
		
		// 비밀번호 찾기
		} else if(action.equals("/find_pwd.do")) { 
			request.getRequestDispatcher("/member/find/find_pwd.jsp").forward(request, response);
			
		// 비밀번호 찾기 프로세스
		} else if(action.equals("/find_pwd_process")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String tel = request.getParameter("tel_first") + "-" + request.getParameter("tel_middle") + "-" + request.getParameter("tel_last");
			
			//System.out.println("id: " + id);
			//System.out.println("name: " + name);
			//System.out.println("tel: " + tel);
			
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(request.getServletContext());
			
			// 데이터 처리
			String password = memberDao.selectPassword(id, name, tel);	// 비밀번호 조회
			
			// DB 연결 해제
			memberDao.close();
			
			// 비밀번호 찾기 성공 시
			if(password != null) {
				System.out.println("비밀번호 찾기 성공: " + id);
				
				response.getWriter().print(password);
				
			// 비밀번호 찾기 실패 시
			} else {
				System.out.println("비밀번호 찾기 실패: " + id);
				
				response.getWriter().print("null");
				
			}
		
		// 개인정보 수정 프로세스
		} else if(action.equals("/modify_process")){
			java.sql.Date birth = null;
			
			String id = session.getAttribute("id").toString();
			String strBirth = request.getParameter("birth");
			String email = request.getParameter("email_id") + "@" + request.getParameter("email_domain");
			String postCode = request.getParameter("post_code");
			String roadAddress = request.getParameter("road_address");
			String detailAddress = request.getParameter("detail_address");
			String marketingInfoAgree = request.getParameter("mem_advertise");
			
			// 생일이 입력되지 않은 경우
			if(strBirth.equals("")) {
				strBirth = null;
			
			// 생일이 입력된 경우
			} else {
				birth = java.sql.Date.valueOf(strBirth);
			}
			
			// 이메일이 입력되지 않은 경우
			if(email.charAt(0) == '@') { 
				email = null;
			}
			
			// 주소가 입력되지 않은 경우
			if(postCode.equals("")) {
				postCode = null;
				roadAddress = null;
				detailAddress = null;
			}
			
			// 광고성 정보 수신에 동의하지 않은 경우
			if(marketingInfoAgree == null) { 
				marketingInfoAgree = "N";
			
			// 광고성 정보 수신에 동의한 경우
			} else { 
				marketingInfoAgree = "Y";
			}
			
			System.out.println("id: " + id);
			System.out.println("birth: " + strBirth);
			System.out.println("email: " + email);
			System.out.println("post_code: " + postCode);
			System.out.println("road_address: " + roadAddress);
			System.out.println("detail_address: " + detailAddress);
			System.out.println("marketing_info_agree: " + marketingInfoAgree);
			
		
			// 데이터 전송을 위한 MemberDTO 객체
			MemberDTO memberDto = new MemberDTO();
			memberDto.setId(id);
			memberDto.setBirth(birth);
			memberDto.setEmail(email);
			memberDto.setMarketingInfoAgree(marketingInfoAgree);

			// 데이터 전송을 위한 AddressDTO 객체
			AddressDTO addressDto = new AddressDTO();
			addressDto.setId(id);
			addressDto.setName("기본 주소지");
			addressDto.setPostCode(postCode);
			addressDto.setRoadAddress(roadAddress);
			addressDto.setDetailAddress(detailAddress);
			
			// Oracle DB 연결
			JDBConnect jdbc = new JDBConnect(request.getServletContext());
			jdbc.setAutoCommit(false);	// 오류 발생 시 rollback 하기 위해 auto-commit 해제
			
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(jdbc.getConnection());
			AddressDAO addressDao = new AddressDAO(jdbc.getConnection());
			
			// 데이터 처리
			int result = memberDao.update(memberDto); 		// 회원정보 업데이트
			
			if(result != 0 && postCode != null) {			// 회원정보 업데이트에 성공했고 입력할 주소가 있는 경우
				result *=  addressDao.update(addressDto);	// 주소 업데이트 추가
				
			}
			
			// 데이터 업데이트에 성공한 경우
			if(result != 0) { 		
				jdbc.commit();
			
			// 데이터 업데이트에 실패한 경우
			} else { 				
				jdbc.rollback();
				
			}
			
			// DB 연결 해제
			memberDao.close();
			addressDao.close();
			jdbc.close();
			
			// 데이터 업데이트 성공 시
			if(result != 0) { 
				System.out.println("데이터 업데이트 성공: " + id);
				
				response.getWriter().print(1);
			
			// 데이터 업데이트 실패 시
			} else {
				System.out.println("데이터 업데이트 실패: " + id);
				
				response.getWriter().print(0);
			}
			
		// 회원탈퇴 프로세스
		} else if(action.equals("/out_process")){
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(request.getServletContext());
			
			// 데이터 처리
			String id = session.getAttribute("id").toString();	// 세션에 저장된 회원 ID
			int result = memberDao.updateOutDate(id);			// 회원탈퇴
			
			// DB 연결 해제
			memberDao.close();
			
			// 회원탈퇴 성공 시
			if(result != 0) {
				System.out.println("회원탈퇴 성공: " + id);
				
				session.removeAttribute("id");		// 세션에 저장된 회원 ID 삭제
				session.removeAttribute("name");	// 세션에 저장된 회원 이름 삭제
				
				response.getWriter().print(1);
				
			// 회원탈퇴 실패 시
			} else {
				System.out.println("회원탈퇴 실패: " + id);
				
				response.getWriter().print(0);
				
			}
		
		// 비밀번호 변경 프로세스
		} else if(action.equals("/modify_pwd_process")) {
			String id = session.getAttribute("id").toString();
			String userPassword = request.getParameter("user_password");
			String newPassword = request.getParameter("new_password");
			
			//System.out.println("id: " + id);
			//System.out.println("user_password: " + userPassword);
			//System.out.println("new_password: " + newPassword);
			
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(request.getServletContext());
			
			// 데이터 처리
			int result;
			
			String name = memberDao.selectName(id, userPassword);	// 회원 조회 (비밀번호 일치 여부 확인)
			
			// 회원 조회 성공 시
			if(name != null) {
				result = memberDao.updatePassword(id, newPassword);	// 비밀번호 변경
				
			// 회원 조회 실패 시
			} else {
				result = -1;
			}
			
			// DB 연결 해제
			memberDao.close();
			
			// 결과 데이터
			response.getWriter().print(result);
			
		// 처리 불가능한 요청
		} else {
			response.sendRedirect("/error.jsp");
			
		}
		
	}
	
}
