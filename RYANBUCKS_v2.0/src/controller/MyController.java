package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import dao.MenuDAO;
import dao.MenuImageDAO;
import dao.MenuOptionDAO;
import dao.OrderDetailDAO;
import dao.OrderTotalDAO;
import dao.ReviewBoardDAO;
import dao.WriteableReviewsDAO;
import dto.AddressDTO;
import dto.MemberDTO;
import dto.OrderDetailDTO;
import dto.OrderTotalDTO;
import dto.ReviewBoardDTO;
import dto.WriteableReviewsDTO;
import utils.OrderList;

@WebServlet("/my/*")
public class MyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("MyController init 메소드 호출");
	}

	public void destroy() {
		System.out.println("MyController destory 메소드 호출");
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
		System.out.println("====================> /my" + action);
		
		// 세션 얻어오기
		HttpSession session = request.getSession();
		
		// my_ryanbucks
		if(action.equals("/index.do")) {
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				String redirectUrl = URLEncoder.encode("/my/index.do", "UTF-8");
				response.sendRedirect("/login/login.do?redirect_url=" + redirectUrl);
				return;
			}
						
			request.getRequestDispatcher("/my_page/mypage.jsp").forward(request, response);
		
		// 주문 내역
		} else if(action.equals("/order_list.do")) {
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				String redirectUrl = URLEncoder.encode("/my/order_list.do", "UTF-8");
				response.sendRedirect("/login/login.do?redirect_url=" + redirectUrl);
				return;
			}
			
			int postCountPerPage = Integer.parseInt(request.getServletContext().getInitParameter("PostCountPerPage"));		// 한 페이지에 출력할 게시물의 개수
			
			// 출력 조건이 저장된 맵
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("member_id", session.getAttribute("id"));
			condition.put("period", "1");			// 기간
			condition.put("type", "whole");			// 주문유형
			condition.put("pay_method", "whole");	// 결제수단
			condition.put("start", 1);				// 출력 범위: 시작
			condition.put("end", postCountPerPage);	// 출력 범위: 종료
			
			// DAO 생성 후 Oracle DB 연결
			OrderTotalDAO orderTotalDao = new OrderTotalDAO(request.getServletContext());
			OrderDetailDAO orderDetailDao = new OrderDetailDAO(request.getServletContext());
			
			// 데이터 처리
			int totalCount = orderTotalDao.selectCount(condition);						// 검색 조건에 맞는 주문 개수 조회
			List<OrderTotalDTO> orderList = orderTotalDao.selectList(condition);		// 주문 목록 조회
			List<String> menuSummaryList = orderDetailDao.selectMenuSummary(orderList);	// 주문 건의 메뉴 요약정보 목록 조회
			
			// DB 연결 해제
			orderTotalDao.close();
			orderDetailDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("totalCount", totalCount);			// 검색 조건에 맞는 주문 개수
			resultMap.put("pageNum", 1);						// 페이지 번호
			resultMap.put("orderList", orderList);				// 주문 목록
			resultMap.put("menuSummaryList", menuSummaryList);	// 주문 건의 메뉴 요약정보 목록
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/my_page/order/order_list.jsp").forward(request, response);
		
		// 주문 목록 불러오기 프레스
		} else if(action.equals("/load_order_list_process")) {
			int postCountPerPage = Integer.parseInt(request.getServletContext().getInitParameter("PostCountPerPage"));		// 한 페이지에 출력할 게시물의 개수
			
			String period = request.getParameter("period");						// 기간
			String type = request.getParameter("type");							// 주문유형
			String pay_method = request.getParameter("pay_method");				// 결제수단
			int pageNum = Integer.parseInt(request.getParameter("page_num"));	// 페이지 번호
			
			/*
			System.out.println("period: " + period);
			System.out.println("type: " + type);
			System.out.println("pay_method: " + pay_method);
			System.out.println("pageNum: " + pageNum);
			*/
			
			// 출력 조건이 저장된 맵
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("member_id", session.getAttribute("id"));
			condition.put("period", period);								// 기간
			condition.put("type", type);									// 주문유형
			condition.put("pay_method", pay_method);						// 결제수단
			condition.put("start", (pageNum - 1) * postCountPerPage + 1);	// 출력 범위: 시작
			condition.put("end", pageNum * postCountPerPage);				// 출력 범위: 종료
			
			// DAO 생성 후 Oracle DB 연결
			OrderTotalDAO orderTotalDao = new OrderTotalDAO(request.getServletContext());
			OrderDetailDAO orderDetailDao = new OrderDetailDAO(request.getServletContext());
			
			// 데이터 처리
			int totalCount = orderTotalDao.selectCount(condition);						// 검색 조건에 맞는 주문 개수
			List<OrderTotalDTO> orderList = orderTotalDao.selectList(condition);		// 주문 목록 조회
			List<String> menuSummaryList = orderDetailDao.selectMenuSummary(orderList);	// 주문 건의 메뉴 요약정보 목록 조회
			
			// DB 연결 해제
			orderTotalDao.close();
			orderDetailDao.close();
			
			// 결과 데이터
			String data = "{"
					+ "\"list\":\"" + OrderList.getListString(orderList, menuSummaryList) + "\","
					+ "\"paging\":\"" + OrderList.getPagingString(request.getServletContext(), totalCount, pageNum) + "\""
					+ "}";
			response.getWriter().print(data);
		
		// 주문내역 상세보기
		} else if(action.equals("/order_view.do")) {
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				String redirectUrl = URLEncoder.encode("/my/order_list.do", "UTF-8");
				response.sendRedirect("/login/login.do?redirect_url=" + redirectUrl);
				return;
			}
			
			String memberId = session.getAttribute("id").toString();
			String orderId = request.getParameter("order_id");
			
			// DAO 생성 후 Oracle DB 연결 (1)
			OrderTotalDAO orderTotalDao = new OrderTotalDAO(request.getServletContext());
			
			// 데이터 처리 (1)
			OrderTotalDTO orderTotalDto = orderTotalDao.select(orderId, memberId); // 통합 주문 정보 조회
			
			// DB 연결 해제 (1)
			orderTotalDao.close();
			
			// 존재하지 않는 주문이거나 해당 회원의 주문내역이 아닌 경우
			if(orderTotalDto == null) {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			// DAO 생성 후 Oracle DB 연결 (2)
			OrderDetailDAO orderDetailDao = new OrderDetailDAO(request.getServletContext());
			MenuDAO menuDao = new MenuDAO(request.getServletContext());
			MenuImageDAO menuImageDao = new MenuImageDAO(request.getServletContext());
			MenuOptionDAO menuOptionDao = new MenuOptionDAO(request.getServletContext());
			
			// 데이터 처리 (2)
			List<OrderDetailDTO> orderDetailList = orderDetailDao.selectList(orderId);	// 상세 주문 정보 목록 조회
			List<String> menuNameList = menuDao.selectNameList(orderDetailList);		// 메뉴명 목록 조회
			List<String> menuThumFileNameList = menuImageDao.selectThumFileNameList(orderDetailList);	// 메뉴 썸네일 조회
			List<String> menuOptionInfoList = menuOptionDao.selectInfoList(orderDetailList);			// 메뉴 옵션 정보 조회
			
			// DB 연결 해제
			orderDetailDao.close();
			menuDao.close();
			menuImageDao.close();
			menuOptionDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("orderTotalDto", orderTotalDto);
			resultMap.put("orderDetailList", orderDetailList);
			resultMap.put("menuNameList", menuNameList);
			resultMap.put("menuThumFileNameList", menuThumFileNameList);
			resultMap.put("menuOptionInfoList", menuOptionInfoList);
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/my_page/order/order_view.jsp").forward(request, response);
		
		// 상품 리뷰
		} else if(action.equals("/review.do")) {
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				String redirectUrl = URLEncoder.encode("/my/review.do", "UTF-8");
				response.sendRedirect("/login/login.do?redirect_url=" + redirectUrl);
				return;
			}
			
			String id = session.getAttribute("id").toString();	// 세션에 저장된 회원 ID
			
			// DAO 생성 후 Oracle DB 연결
			WriteableReviewsDAO writeableReviewsDao = new WriteableReviewsDAO(request.getServletContext());
			MenuImageDAO menuImageDao = new MenuImageDAO(request.getServletContext());
			
			// 데이터 처리
			List<WriteableReviewsDTO> reviewList = writeableReviewsDao.selctList(id);				// 작성 가능한 리뷰 목록 조회
			List<String> menuThumFileNameList = menuImageDao.selectThumFileNameList(reviewList);	// 메뉴 썸네일 조회
			
			// DB 연결 해제
			writeableReviewsDao.close();
			menuImageDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("reviewList", reviewList);
			resultMap.put("menuThumFileNameList", menuThumFileNameList);

			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/my_page/review/review.jsp").forward(request, response);
		
		// 리뷰 작성 팝업
		} else if(action.equals("/write_review_popup.do")) {
			
			request.getRequestDispatcher("/my_page/review/write_review_popup.jsp").forward(request, response);
		
		// 리뷰 작성 프로세스
		} else if(action.equals("/write_review_process")) {
			
			String memberId = session.getAttribute("id").toString();	// 회원 id
			int orderDetailNo = Integer.parseInt(request.getParameter("no"));
			int rate = Integer.parseInt(request.getParameter("reviewStar"));
			String content = request.getParameter("content");
			
			/*
			System.out.println("memberId: " + memberId);
			System.out.println("orderDetailNo: " + orderDetailNo);
			System.out.println("rate: " + rate);
			System.out.println("content: " + content);
			*/
			
			// 데이터 전송을 위한 ReviewBoardDTO 객체
			ReviewBoardDTO reviewBoardDto = new ReviewBoardDTO();
			reviewBoardDto.setRate(rate);
			reviewBoardDto.setContent(content);
			reviewBoardDto.setMemberId(memberId);
			
			// Oracle DB 연결
			JDBConnect jdbc = new JDBConnect(request.getServletContext());
			jdbc.setAutoCommit(false);	// 오류 발생 시 rollback 하기 위해 auto-commit 해제
			
			// DAO 생성 후 Oracle DB 연결
			ReviewBoardDAO reviewBoardDao = new ReviewBoardDAO(jdbc.getConnection());
			OrderDetailDAO orderDetailDao = new OrderDetailDAO(jdbc.getConnection());
			
			// 데이터 처리
			int result = reviewBoardDao.insert(reviewBoardDto); // 리뷰 작성
			
			if(result != 0) {	// 리뷰 작성에 성공한 경우
				result = orderDetailDao.updateReviewPostNo(orderDetailNo, result);	// 리뷰 포스트 번호 업데이트
			}
			
			// 데이터 처리에 모두 성공한 경우 commit
			if(result != 0) {
				jdbc.commit();
			
			// 데이터 처리에 한번이라도 실패한 경우 rollback
			} else {
				jdbc.rollback();
			}
			
			// DB 연결 해제
			reviewBoardDao.close();
			orderDetailDao.close();
			jdbc.close();
			
			// 리뷰 작성 성공 시
			if(result != 0) {
				System.out.println("리뷰 작성 성공: " + orderDetailNo);
				
				response.getWriter().print(1);
				
			// 리뷰 작성 실패 시
			} else {
				System.out.println("리뷰 작성 실패: " + orderDetailNo);
				
				response.getWriter().print(0);
			}
			
		// 개인정보확인 및 수정
		} else if(action.equals("/myinfo_modify.do")) {
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				String redirectUrl = URLEncoder.encode("/my/myinfo_modify.do", "UTF-8");
				response.sendRedirect("/login/login.do?redirect_url=" + redirectUrl);
				return;
			}
			
			String id = session.getAttribute("id").toString();	// 세션에 저장된 회원 ID
			
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(request.getServletContext());
			AddressDAO addressDao = new AddressDAO(request.getServletContext());
			
			// 데이터 처리
			MemberDTO memberDto = memberDao.select(id);			// 회원 조회
			AddressDTO addressDto = addressDao.select(id);		// 주소 조회
			
			// DB 연결 해제
			memberDao.close();
			addressDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("memberDto", memberDto);
			resultMap.put("addressDto", addressDto);
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/member/info/modify.jsp").forward(request, response);
		
		// 회원탈퇴
		} else if(action.equals("/myinfo_out.do")){
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				String redirectUrl = URLEncoder.encode("/my/myinfo_out.do", "UTF-8");
				response.sendRedirect("/login/login.do?redirect_url=" + redirectUrl);
				return;
			}
			
			request.getRequestDispatcher("/member/info/out.jsp").forward(request, response);
		
		// 비밀번호 변경
		} else if(action.equals("/myinfo_modify_pwd.do")){
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				String redirectUrl = URLEncoder.encode("/my/myinfo_modify_pwd.do", "UTF-8");
				response.sendRedirect("/login/login.do?redirect_url=" + redirectUrl);
				return;
			
			}
			
			request.getRequestDispatcher("/member/info/modify_pwd.jsp").forward(request, response);
			
		// 처리 불가능한 요청
		} else {
			response.sendRedirect("/error.jsp");
			
		}
		
	}
	
}
