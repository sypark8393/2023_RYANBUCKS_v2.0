package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dao.MenuDAO;
import dao.MenuImageDAO;
import dao.MenuOptionDAO;
import dao.ReviewByMenuDAO;
import dto.CategoryDTO;
import dto.MenuDTO;
import dto.MenuOptionDTO;
import dto.ReviewByMenuDTO;
import utils.ReviewList;

@WebServlet("/menu/*")
public class MenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("MenuController init 메소드 호출");
	}

	public void destroy() {
		System.out.println("MenuController destory 메소드 호출");
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
		System.out.println("====================> /menu" + action);
		
		// menu
		if(action.equals("/index.do")) {
			request.getRequestDispatcher("/shop/menu/menu.jsp").forward(request, response);
		
		// 음료 목록, 푸드 목록
		} else if(action.equals("/drink_list.do") || action.equals("/food_list.do")) {
			String type;
			
			// 음료 목록 페이지인 경우
			if(action.equals("/drink_list.do")) {
				type = "drink";
			
			// 푸드 목록 페이지인 경우
			} else {
				type = "food";
			}
			
			// DAO 생성 후 Oracle DB 연결
			CategoryDAO categoryDao = new CategoryDAO(request.getServletContext());
			MenuDAO menuDao = new MenuDAO(request.getServletContext());
			MenuImageDAO menuImageDao = new MenuImageDAO(request.getServletContext());
			
			// 데이터 처리
			List<CategoryDTO> categoryList = categoryDao.selectList(type);
			Map<String, List<MenuDTO>> menuMap = menuDao.selectList(type);
			List<MenuDTO> newMenuList = menuDao.selectNewList(type);
			Map<Integer, String> menuImageMap = menuImageDao.selectThumFileNameList(type);
			
			// DB 연결 해제
			categoryDao.close();
			menuDao.close();
			menuImageDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("type", type);
			resultMap.put("categoryList", categoryList);
			resultMap.put("menuMap", menuMap);
			resultMap.put("newMenuList", newMenuList);
			resultMap.put("menuImageMap", menuImageMap);
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/shop/menu/menu_list.jsp").forward(request, response);
		
		// 메뉴 상세보기, 음료 상세보기, 푸드 상세보기
		} else if(action.equals("/menu_view.do") || action.equals("/drink_view.do") || action.equals("/food_view.do")) {
			int postCountPerPage = Integer.parseInt(request.getServletContext().getInitParameter("PostCountPerPage"));		// 한 페이지에 출력할 게시물의 개수
			int no = Integer.parseInt(request.getParameter("no"));	// 메뉴 번호
			
			//System.out.println("no: " + no);
			
			// 출력 조건이 저장된 맵
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("menuNo", no);				// 검색어
			condition.put("sort", "latest");			// 정렬 방법
			condition.put("start", 1);					// 출력 범위: 시작
			condition.put("end", postCountPerPage);		// 출력 범위: 종료
			
			// DAO 생성 후 Oracle DB 연결 (1)
			MenuDAO menuDao = new MenuDAO(request.getServletContext());
			
			// 데이터 처리 (1)
			MenuDTO menuDto = menuDao.select(no);	// 메뉴 정보 조회
			
			// DB 연결 해제 (1)
			menuDao.close();
			
			// 숨긴 상태의 메뉴이거나 존재하지 않는 메뉴인 경우
			if(menuDto == null) {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			// DAO 생성 후 Oracle DB 연결 (2)
			CategoryDAO categoryDao = new CategoryDAO(request.getServletContext());
						
			// 데이터 처리 (2)
			CategoryDTO categoryDto = categoryDao.select(menuDto.getCategoryId());
			
			// DB 연결 해제 (2)
			categoryDao.close();
			
			// 숨긴 상태의 카테고리 경우
			if(categoryDto == null) {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			// DAO 생성 후 Oracle DB 연결 (3)
			MenuImageDAO menuImageDao = new MenuImageDAO(request.getServletContext());
			MenuOptionDAO menuOptionDao = new MenuOptionDAO(request.getServletContext());
			ReviewByMenuDAO reviewByMenuDao = new ReviewByMenuDAO(request.getServletContext());
			
			// 데이터 처리 (3)
			List<String> imageList = menuImageDao.selectFileNameList(no);		// 이미지 파일명 목록 조회
			List<MenuOptionDTO> optionList = menuOptionDao.selectList(no);		// 옵션 목록 조회
			Double averageRate = reviewByMenuDao.selectAverageRate(no);					// 평균 평점 조회
			int totalCount = reviewByMenuDao.selectCount(no);							// 리뷰 개수 조회
			List<Integer> countByRateList = reviewByMenuDao.selectCountByRate(no);		// 별점 비율 조회
			List<ReviewByMenuDTO> reviewList = reviewByMenuDao.selectList(condition);	// 리뷰 목록 조회
			
			// DB 연결 해제 (3)
			categoryDao.close();
			menuImageDao.close();
			menuOptionDao.close();
			reviewByMenuDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("pageNum", 1); // 페이지 번호
			resultMap.put("menuDto", menuDto);
			resultMap.put("categoryDto", categoryDto);
			resultMap.put("imageList", imageList);
			resultMap.put("optionList", optionList);
			resultMap.put("averageRate", averageRate);
			resultMap.put("totalCount", totalCount);
			resultMap.put("countByRateList", countByRateList);
			resultMap.put("reviewList", reviewList);
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/shop/menu/menu_view.jsp").forward(request, response);
		
		// 리뷰 목록 불러오기 프로세스
		} else if(action.equals("/load_review_list_process")) {
			int postCountPerPage = Integer.parseInt(request.getServletContext().getInitParameter("PostCountPerPage"));		// 한 페이지에 출력할 게시물의 개수
			int no = Integer.parseInt(request.getParameter("no"));				// 메뉴 번호
			String sort = request.getParameter("sort");							// 정렬 기준
			int pageNum = Integer.parseInt(request.getParameter("page_num"));	// 페이지 번호
			
			//System.out.println("no: " + no);
			//System.out.println("sort: " + sort);
			//System.out.println("pageNum: " + pageNum);
			
			// 출력 조건이 저장된 맵
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("menuNo", no);									// 검색어
			condition.put("sort", sort);									// 정렬 방법
			condition.put("start", (pageNum - 1) * postCountPerPage + 1);	// 출력 범위: 시작
			condition.put("end", pageNum * postCountPerPage);				// 출력 범위: 종료
			
			// DAO 생성 후 Oracle DB 연결
			ReviewByMenuDAO reviewByMenuDao = new ReviewByMenuDAO(request.getServletContext());
			
			// 데이터 처리
			int totalCount = reviewByMenuDao.selectCount(no);							// 리뷰 개수 조회
			List<ReviewByMenuDTO> reviewList = reviewByMenuDao.selectList(condition);	// 리뷰 목록 조회
			
			// DB 연결 해제
			reviewByMenuDao.close();
			
			// 결과 데이터
			String data = "{"
							+ "\"list\":\"" + ReviewList.getListString(reviewList) + "\","
							+ "\"paging\":\"" + ReviewList.getPagingString(request.getServletContext(), totalCount, pageNum) + "\""
							+ "}";
			response.getWriter().print(data);
			
		// 처리 불가능한 요청
		} else {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
			
		}
	}
}
