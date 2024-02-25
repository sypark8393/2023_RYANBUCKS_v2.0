package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

import dao.CartDAO;
import dao.MenuDAO;
import dao.MenuImageDAO;
import dao.MenuOptionDAO;
import dto.CartDTO;
import dto.MenuDTO;
import dto.MenuOptionDTO;

@WebServlet("/cart/*")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("CartController init 메소드 호출");
	}

	public void destroy() {
		System.out.println("CartController destory 메소드 호출");
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
		System.out.println("====================> /cart" + action);
		
		// 세션 얻어오기
		HttpSession session = request.getSession();
		
		// 장바구니
		if(action.equals("/cart.do")) {
			// 로그아웃 상태일 때
			if(session.getAttribute("id") == null) {
				String redirectUrl = URLEncoder.encode("/cart/cart.do", "UTF-8");
				response.sendRedirect("/login/login.do?redirect_url=" + redirectUrl);
				return;
			}
			
			String memberId = session.getAttribute("id").toString();			// 회원 id
			
			// DAO 생성 후 Oracle DB 연결
			CartDAO cartDao = new CartDAO(request.getServletContext());
			MenuDAO menuDao = new MenuDAO(request.getServletContext());
			MenuImageDAO menuImageDao = new MenuImageDAO(request.getServletContext());
			MenuOptionDAO menuOptionDao = new MenuOptionDAO(request.getServletContext());
			
			// 데이터 처리
			List<CartDTO> cartList = cartDao.selectList(memberId);	// 장바구니에 담긴 메뉴 목록 조회
			List<MenuDTO> menuList = menuDao.selectList(cartList);	// 메뉴 목록 조회
			List<String> menuThumFileNameList = menuImageDao.selectThumFileNameList(cartList);	// 메뉴 썸네일 조회
			List<MenuOptionDTO> menuOptionList = menuOptionDao.selectList(cartList);		// 메뉴 옵션 정보 조회
			
			// DB 연결 해제
			cartDao.close();
			menuDao.close();
			menuImageDao.close();
			menuOptionDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("cartList", cartList);
			resultMap.put("menuList", menuList);
			resultMap.put("menuThumFileNameList", menuThumFileNameList);
			resultMap.put("menuOptionList", menuOptionList);
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/shop/cart/cart.jsp").forward(request, response);
		
		// 장바구니에 메뉴 담기 프로세스
		} else if(action.equals("/add_process")) {
			String memberId = session.getAttribute("id").toString();			// 회원 id
			int menuNo = Integer.parseInt(request.getParameter("menu_no"));		// 메뉴 번호
			int optionNo = 0;	// 옵션 번호
			if(!request.getParameter("option_no").equals("undefined")) {
				optionNo = Integer.parseInt(request.getParameter("option_no"));
			}
			int quantity = Integer.parseInt(request.getParameter("quantity"));	// 수량
			
			/*
			System.out.println("memberId: " + memberId);
			System.out.println("menuNo: " + menuNo);
			System.out.println("optionNo: " + optionNo);
			System.out.println("quantity: " + quantity);
			*/
			
			// 데이터 전송을 위한 CartDTO 객체
			CartDTO cartDto = new CartDTO();
			cartDto.setMemberId(memberId);
			cartDto.setMenuNo(menuNo);
			cartDto.setOptionNo(optionNo);
			cartDto.setQuantity(quantity);
			
			// DAO 생성 후 Oracle DB 연결
			CartDAO cartDao = new CartDAO(request.getServletContext());
			
			// 데이터 처리
			int result = cartDao.insert(cartDto);
			
			// DB 연결 해제
			cartDao.close();
			
			// 장바구니에 메뉴 담기 성공 시
			if(result != 0) {
				System.out.println("장바구니에 메뉴 담기 성공: " + memberId);
				
				response.getWriter().print(1);
				
			// 장바구니에 메뉴 담기 실패 시
			} else {
				System.out.println("장바구니에 메뉴 담기 실패: " + memberId);
				
				response.getWriter().print(0);
			}
		
		// 장바구니 메뉴 삭제 프로세스
		} else if(action.equals("/delete_process")) {
			String memberId = session.getAttribute("id").toString();			// 회원 id
			
			List<Integer> noList = new ArrayList<Integer>();
			if(request.getParameter("type") != null) {	// 단일 삭제인 경우
				noList.add(Integer.parseInt(request.getParameter("no")));
			
			} else {	// 다중 삭제인 경우
				String[] tmp = request.getParameterValues("cart_item");
				for(String str : tmp) {
					noList.add(Integer.parseInt(str));
				}
			}
			
			// DAO 생성 후 Oracle DB 연결
			CartDAO cartDao = new CartDAO(request.getServletContext());
			
			// 데이터 처리
			int result = cartDao.deleteList(noList, memberId);
			
			// DB 연결 해제
			cartDao.close();
			
			// 장바구니에서 메뉴 삭제 성공 시
			if(result != 0) {
				System.out.println("메뉴 삭제 성공");
				
				response.getWriter().print(1);
				
			// 장바구니에서 메뉴 삭제 실패 시
			} else {
				System.out.println("메뉴 삭제 실패");
				
				response.getWriter().print(0);
				
			}
		
		// 장바구니 주문 수정 팝업
		} else if(action.equals("/edit_cart_popup.do")) {
			String memberId = session.getAttribute("id").toString();	// 회원 id
			int no = Integer.parseInt(request.getParameter("no"));		// 장바구니 관리 번호
			
			// DAO 생성 후 Oracle DB 연결 (1)
			CartDAO cartDao = new CartDAO(request.getServletContext());
			
			// 데이터 처리 (1)
			CartDTO cartDto = cartDao.select(memberId, no);
			
			// DB 연결 해제 (1)
			cartDao.close();
			
			// 장바구니에 일치하는 정보가 없는 경우 에러 페이지로 이동
			if(cartDto == null) {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			// DAO 생성 후 Oracle DB 연결 (2)
			MenuDAO menuDao = new MenuDAO(request.getServletContext());
			MenuImageDAO menuImageDao = new MenuImageDAO(request.getServletContext());
			MenuOptionDAO menuOptionDao = new MenuOptionDAO(request.getServletContext());
			
			// 데이터 처리 (2)
			MenuDTO menuDto = menuDao.select(cartDto.getMenuNo());
			String fileName = menuImageDao.selectFileNameList(cartDto.getMenuNo()).get(0);
			List<MenuOptionDTO> optionList = menuOptionDao.selectList(cartDto.getMenuNo());
			
			// DB 연결 해제 (2)
			menuDao.close();
			menuImageDao.close();
			menuOptionDao.close();

			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("no", no);
			resultMap.put("cartDto", cartDto);
			resultMap.put("menuDto", menuDto);
			resultMap.put("fileName", fileName);
			resultMap.put("optionList", optionList);
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/shop/cart/edit_cart_popup.jsp").forward(request, response);
		
		// 장바구니 주문 수정 프로세스
		} else if(action.equals("/edit_cart_process")) {
			String memberId = session.getAttribute("id").toString();	// 회원 id
			int no = Integer.parseInt(request.getParameter("no"));
			int optionNo = Integer.parseInt(request.getParameter("option_no"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			//System.out.println("no: " + no);
			//System.out.println("optionNo: " + optionNo);
			//System.out.println("quantity: " + quantity);
			
			// DAO 생성 후 Oracle DB 연결
			CartDAO cartDao = new CartDAO(request.getServletContext());
			
			// 데이터 처리
			int result = cartDao.update(memberId, no, optionNo, quantity);
			
			// DB 연결 해제
			cartDao.close();
			
			// 장바구니 업데이트 성공 시
			if(result != 0) {
				System.out.println("장바구니 업데이트 성공: " + no);
				
				response.getWriter().print(1);
			
			// 장바구니 업데이트 실패 시
			} else {
				System.out.println("장바구니 업데이트 실패: " + no);
				
				response.getWriter().print(0);
			}
			
			
		// 처리 불가능한 요청
		} else {
			response.sendRedirect("/error.jsp");
			
		}
				
	}

}
