package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.JDBConnect;
import dao.*;
import dto.*;

@WebServlet("/order/*")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("OrderController init 메소드 호출");
	}

	public void destroy() {
		System.out.println("OrderController destory 메소드 호출");
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
		System.out.println("====================> /order" + action);
		
		// 세션 얻어오기
		HttpSession session = request.getSession();
		
		// 주문 페이지
		if(action.equals("/order_sheet.do")) {
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				System.out.println("로그아웃 상태이므로 로그인 페이지로 이동");
				response.sendRedirect("/login/login.do");
				return;
			
			}
			
			String memberId = session.getAttribute("id").toString();	// 회원 id
			
			String src = request.getParameter("src");					// 요청 유형
			List<Integer> cartNoList = null;							// 장바구니 번호 목록
			List<OrderDetailDTO> orderDetailList;						// 상세 주문 정보 목록
			
			// DAO 생성 후 Oracle DB 연결(1)
			OrderDetailDAO orderDetailDao = new OrderDetailDAO(request.getServletContext());
			
			// menu_view에서 요청
			if(src.equals("menu_view")) { 
				orderDetailList = new Vector<OrderDetailDTO>();
				
				// 메뉴 번호, 옵션 번호, 수량 받기
				int menuNo = Integer.parseInt(request.getParameter("menu_no"));
				int optionNo = (request.getParameter("option_no") != null)? Integer.parseInt(request.getParameter("option_no")) : 0;
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				//System.out.println("menuNo: " + menuNo);
				//System.out.println("optionNo: " + optionNo);
				//System.out.println("quantity: " + quantity);
				
				// 데이터 처리 (1)
				OrderDetailDTO orderDetailDto = orderDetailDao.select(menuNo, optionNo, quantity);
				orderDetailList.add(orderDetailDto);
				
			// cart에서 요청
			} else {
				String[] tmp = request.getParameterValues("cart_item");
				cartNoList = new ArrayList<Integer>();
				
				for(String str : tmp) {
					cartNoList.add(Integer.parseInt(str));
				}
				
				// 데이터 처리 (1)
				orderDetailList = orderDetailDao.selectList(cartNoList);
			}
			
			// DAO 생성 후 Oracle DB 연결 (2)
			MemberDAO memberDao = new MemberDAO(request.getServletContext());
			AddressDAO addressDao = new AddressDAO(request.getServletContext());
			MenuDAO menuDao = new MenuDAO(request.getServletContext());
			MenuImageDAO menuImageDao = new MenuImageDAO(request.getServletContext());
			MenuOptionDAO menuOptionDao = new MenuOptionDAO(request.getServletContext());
			
			// 데이터 처리 (2)
			MemberDTO memberDto = memberDao.select(memberId);		// 회원 정보 정보 조회
			AddressDTO addressDto = addressDao.select(memberId);	// 회원 주소 정보 조회
			List<String> menuNameList = menuDao.selectNameList(orderDetailList);						// 메뉴명 목록 조회
			List<String> menuThumFileNameList = menuImageDao.selectThumFileNameList(orderDetailList);	// 메뉴 썸네일 조회
			List<String> menuOptionInfoList = menuOptionDao.selectInfoList(orderDetailList);			// 메뉴 옵션 정보 조회
			
			// DB 연결 해제
			orderDetailDao.close();
			addressDao.close();
			menuDao.close();
			menuImageDao.close();
			menuOptionDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("src", src);
			resultMap.put("memberDto", memberDto);
			resultMap.put("addressDto", addressDto);
			resultMap.put("cartNoList", cartNoList);
			resultMap.put("orderDetailList", orderDetailList);
			resultMap.put("menuNameList", menuNameList);
			resultMap.put("menuThumFileNameList", menuThumFileNameList);
			resultMap.put("menuOptionInfoList", menuOptionInfoList);
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/shop/order/order_sheet.jsp").forward(request, response);
	
		// 주문 프로세스
		} else if(action.equals("/order_process")) {
			String src = request.getParameter("src");					// 요청 유형
			List<Integer> cartNoList = null;							// 장바구니 번호 목록
			List<OrderDetailDTO> orderDetailList;						// 상세 주문 정보 목록
			
			// DAO 생성 후 Oracle DB 연결(1)
			OrderDetailDAO orderDetailDao = new OrderDetailDAO(request.getServletContext());
			
			// menu_view에서 요청
			if(src.equals("menu_view")) { 
				orderDetailList = new Vector<OrderDetailDTO>();
				
				// 메뉴 번호, 옵션 번호, 수량 받기
				int menuNo = Integer.parseInt(request.getParameter("menu_no"));
				int optionNo = (request.getParameter("option_no") != null)? Integer.parseInt(request.getParameter("option_no")) : 0;
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				System.out.println("menuNo: " + menuNo);
				System.out.println("optionNo: " + optionNo);
				System.out.println("quantity: " + quantity);
				
				// 데이터 처리 (1)
				OrderDetailDTO orderDetailDto = orderDetailDao.select(menuNo, optionNo, quantity);
				orderDetailList.add(orderDetailDto);
				
			// cart에서 요청
			} else {
				String[] tmp = request.getParameterValues("cart_item");
				cartNoList = new ArrayList<Integer>();
				
				for(String str : tmp) {
					cartNoList.add(Integer.parseInt(str));
				}
				
				// 데이터 처리 (1)
				orderDetailList = orderDetailDao.selectList(cartNoList);
			}
			
			// DB 연결 해제 (1)
			orderDetailDao.close();
			
			// 저장할 데이터
			String id = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + String.format("%04d", new Random().nextInt(10000));		// 주문 번호
			String memberId = session.getAttribute("id").toString();	// 회원 id
			String recipientName = request.getParameter("name");		// 수령인 이름
			String recipientTel = request.getParameter("tel_first") + "-" + request.getParameter("tel_middle") + "-" + request.getParameter("tel_last");	// 수령인 연락처
			String recipientEmail = request.getParameter("email_id") + "@" + request.getParameter("email_domain");	// 수령인 이메일
			String type = request.getParameter("type");		// 주문 유형
			String pickupBranch = null;		// 픽업 지점
			Timestamp pickupTime = null;	// 픽업 시간
			String postCode = null;			// 우편 번호
			String roadAddress = null;		// 도로명 주소
			String detailAddress = null;	// 상세 주소
			String payMethod = request.getParameter("pay_method");		// 결제 수단
			String cardName = OrderTotalDTO.cartNameList[new Random().nextInt(OrderTotalDTO.cartNameList.length)];;
			String cardNo = String.format("%06d", new Random().nextInt(100000)) + "******" + String.format("%04d", new Random().nextInt(1000));;
			String cardQuota = "00";
			String authCode = String.format("%08d", new Random().nextInt(10000000));
			
			// 선택 항목 데이터 처리
			// 이메일이 입력되지 않은 경우
			if(recipientEmail.charAt(0) == '@') { 
				recipientEmail = null;
			}
			
			// 픽업 정보
			if(type.equals("pickup")) {
				pickupBranch = request.getParameter("branch");
				
				String pickupTimeStr = request.getParameter("date") + " " + String.format("%02d", Integer.parseInt(request.getParameter("hour"))) + ":"
										+ String.format("%02d", Integer.parseInt(request.getParameter("minutes"))) + ":00.000";							// 문자열을 LocalDateTime으로 형변환하는 문제가 있어 ":00.000" 추가
			    LocalDateTime pickup_time_localDateTime = LocalDateTime.parse(pickupTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
			    pickupTime = Timestamp.valueOf(pickup_time_localDateTime);
			
			// 배송지 정보
			} else {
				// 기본 배송지
				if(request.getParameter("address").equals("existed")) {	
					postCode = request.getParameter("existed_post_code");
					roadAddress = request.getParameter("existed_road_address");
					detailAddress = request.getParameter("existed_detail_address");
					
				// 신규 배송지
				} else {
					postCode = request.getParameter("new_post_code");
					roadAddress = request.getParameter("new_road_address");
					detailAddress = request.getParameter("new_detail_address");
				}
			}
			
			// 총 거래 금액
			int amount = 0;
			for(OrderDetailDTO orderDetailDto : orderDetailList) {
				orderDetailDto.setOrderId(id);	// 주문 번호 저장
				
				amount = (orderDetailDto.getMenuPrice() + orderDetailDto.getOptionPrice()) * orderDetailDto.getQuantity();
			}
			
			/*
			System.out.println("id: " + id);
			System.out.println("memberId: " + memberId);
			System.out.println("recipientName: " + recipientName);
			System.out.println("recipientTel: " + recipientTel);
			System.out.println("recipientEmail: " + recipientEmail);
			System.out.println("type: " + type);
			System.out.println("pickupBranch: " + pickupBranch);
			System.out.println("pickupTime: " + pickupTime);
			System.out.println("postCode: " + postCode);
			System.out.println("roadAddress: " + roadAddress);
			System.out.println("detailAddress: " + detailAddress);
			System.out.println("amount: " + amount);
			System.out.println("payMethod: " + payMethod);
			System.out.println("cardName: " + cardName);
			System.out.println("cardNo: " + cardNo);
			System.out.println("cardQuota: " + cardQuota);
			System.out.println("authCode: " + authCode);
			*/
			
			// 데이터 전송을 위한 OrderTotalDTO 객체
			OrderTotalDTO orderTotalDto = new OrderTotalDTO();
			orderTotalDto.setId(id);
			orderTotalDto.setMemberId(memberId);
			orderTotalDto.setRecipientName(recipientName);
			orderTotalDto.setRecipientTel(recipientTel);
			orderTotalDto.setRecipientEmail(recipientEmail);
			orderTotalDto.setType(type);
			orderTotalDto.setPickupBranch(pickupBranch);
			orderTotalDto.setPickupTime(pickupTime);
			orderTotalDto.setPostCode(postCode);
			orderTotalDto.setRoadAddress(roadAddress);
			orderTotalDto.setDetailAddress(detailAddress);
			orderTotalDto.setAmount(amount);
			orderTotalDto.setPayMethod(payMethod);
			orderTotalDto.setCardName(cardName);
			orderTotalDto.setCardNo(cardNo);
			orderTotalDto.setCardQuota(cardQuota);
			orderTotalDto.setAuthCode(authCode);
			
			// Oracle DB 연결
			JDBConnect jdbc = new JDBConnect(request.getServletContext());
			jdbc.setAutoCommit(false);	// 오류 발생 시 rollback 하기 위해 auto-commit 해제
			
			// DAO 생성 후 Oracle DB 연결 (2)
			orderDetailDao = new OrderDetailDAO(jdbc.getConnection());
			OrderTotalDAO orderTotalDao = new OrderTotalDAO(jdbc.getConnection());
			MenuDAO menuDao = new MenuDAO(jdbc.getConnection());
			MenuOptionDAO menuOptionDao = new MenuOptionDAO(jdbc.getConnection());
			CartDAO cartDao = new CartDAO(jdbc.getConnection());
			
			// 데이터 처리 (2)
			int result = orderTotalDao.insert(orderTotalDto);	// 통합 주문 정보 추가
			
			if(result != 0) {	// 통합 주문 정보 추가에 성공한 경우
				result = orderDetailDao.insertList(orderDetailList);	// 상세 주문 정보 추가
			}
			
			if(result != 0) {	// 상세 주문 정보 추가에 성공한 경우
				result = menuDao.updateStock(orderDetailList);			// 메뉴 재고 업데이트
			}
			
			if(result != 0) {	// 메뉴 재고 업데이트에 성공한 경우
				result = menuOptionDao.updateStock(orderDetailList);	// 옵션 재고 업데이트
			}
			
			if(result != 0 && cartNoList != null) {	// 옵션 재고 업데이트에 성공했고, 장바구니에 담긴 상품을 주문하는 경우
				result = cartDao.deleteList(cartNoList, memberId);		// 장바구니에 담긴 상품 삭제
			}
			
			// 데이터 처리에 모두 성공한 경우 commit
			if(result != 0) {
				jdbc.commit();
			
			// 데이터 처리에 한번이라도 실패한 경우 rollback
			} else {
				jdbc.rollback();
			}
			
			// DB 연결 해제
			orderDetailDao.close();
			orderTotalDao.close();
			menuDao.close();
			menuOptionDao.close();
			cartDao.close();
			jdbc.close();
			
			// 주문 요청 성공 시
			if(result != 0) {
				System.out.println("주문 요청 성공: " + id);
				
				response.getWriter().print(id);
				
			// 주문 요청 실패 시
			} else {
				System.out.println("주문 요청 실패: " + id);
				
				response.getWriter().print(0);
			}
			
			
		// 주문 완료 페이지
		} else if(action.equals("/order_finish.do")) {
			String orderId = request.getParameter("order_id");	// 주문 번호
			String memberId = session.getAttribute("id").toString();	// 회원 id
			
			// DAO 생성 후 Oracle DB 연결
			OrderTotalDAO orderTotalDao = new OrderTotalDAO(request.getServletContext());
			
			// 데이터 처리
			OrderTotalDTO orderTotalDto = orderTotalDao.select(orderId, memberId);
			
			// DB 연결 해제
			orderTotalDao.close();
			
			// 존재하지 않는 주문이거나 로그인한 회원의 주문 건이 아닌 경우
			if(orderTotalDto == null) {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			request.setAttribute("orderTotalDto", orderTotalDto);
			request.getRequestDispatcher("/shop/order/order_finish.jsp").forward(request, response);
			
		// 처리 불가능한 요청
		} else {
			response.sendRedirect("/error.jsp");
			
		}
		
	}

}
