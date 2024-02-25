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

import dao.NoticeBoardDAO;
import dto.NoticeBoardDTO;
import utils.NoticeList;

@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("NoticeController init 메소드 호출");
	}

	public void destroy() {
		System.out.println("NoticeController destory 메소드 호출");
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
		System.out.println("====================> /notice" + action);
	
		// notice
		if(action.equals("/index.do")) {
			request.getRequestDispatcher("/notice/notice_list.do").forward(request, response);
		
		// 공지사항 목록
		} else if(action.equals("/notice_list.do")) {
			int postCountPerPage = Integer.parseInt(request.getServletContext().getInitParameter("PostCountPerPage"));		// 한 페이지에 출력할 게시물의 개수
			
			// 출력 조건이 저장된 맵
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("keyword", "");				// 검색어
			condition.put("start", 1);					// 출력 범위: 시작
			condition.put("end", postCountPerPage);		// 출력 범위: 종료
			
			// DAO 생성 후 Oracle DB 연결
			NoticeBoardDAO noticeBoardDao = new NoticeBoardDAO(request.getServletContext());
			
			// 데이터 처리
			int totalCount = noticeBoardDao.selectCount("");					// 검색 조건에 맞는 공지사항 개수 조회
			List<NoticeBoardDTO> list = noticeBoardDao.selectList(condition);	// 공지사항 목록 조회
			
			// DB 연결 해제
			noticeBoardDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("totalCount", totalCount);	// 검색 조건에 맞는 공지사항 개수
			resultMap.put("pageNum", 1);				// 페이지 번호
			resultMap.put("list", list);				// 출력할 공지사항 목록
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/notice_board/notice_list.jsp").forward(request, response);
		
		// 공지사항 목록 불러오기 프로세스
		} else if(action.equals("/load_list_process")) {
			int postCountPerPage = Integer.parseInt(request.getServletContext().getInitParameter("PostCountPerPage"));		// 한 페이지에 출력할 게시물의 개수
			
			String keyword = request.getParameter("keyword"); 					// 검색어
			int pageNum = Integer.parseInt(request.getParameter("page_num"));	// 페이지 번호
			
			//System.out.println("keyword: " + keyword);
			//System.out.println("pageNum: " + pageNum);
			
			// 출력 조건이 저장된 맵
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("keyword", keyword);	// 검색어
			condition.put("start", (pageNum - 1) * postCountPerPage + 1);	// 출력 범위: 시작
			condition.put("end", pageNum * postCountPerPage);				// 출력 범위: 종료
			
			// DAO 생성 후 Oracle DB 연결
			NoticeBoardDAO noticeBoardDao = new NoticeBoardDAO(request.getServletContext());
			
			// 데이터 처리
			int totalCount = noticeBoardDao.selectCount(keyword);				// 검색 조건에 맞는 공지사항 개수 조회
			List<NoticeBoardDTO> list = noticeBoardDao.selectList(condition);	// 공지사항 목록 조회
			
			// DB 연결 해제
			noticeBoardDao.close();
			
			// 결과 데이터
			String data = "{"
							+ "\"list\":\"" +  NoticeList.getListString(list) + "\","
							+ "\"paging\":\"" +  NoticeList.getPagingString(request.getServletContext(), totalCount, pageNum) + "\""
							+ "}";
			response.getWriter().print(data);
			
		// 공지사항 상세보기
		} else if(action.equals("/notice_view.do")) {
			int no = Integer.parseInt(request.getParameter("no")); // 공지사항 번호
			
			// DAO 생성 후 Oracle DB 연결
			NoticeBoardDAO noticeBoardDao = new NoticeBoardDAO(request.getServletContext());
			
			// 데이터 처리
			NoticeBoardDTO noticeBoardDto = noticeBoardDao.select(no);			// 공지사항 내용 조회
			
			// 없는 게시물이거나 숨김 상태인 경우
			if(noticeBoardDto == null || noticeBoardDto.getVisibility().equals("hidden")) {	
				// DB 연결 해제
				noticeBoardDao.close();
				
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			noticeBoardDao.updateVisitCount(no);									// 조회수 증가 (*처리 성공 여부에 따른 분기 처리 생략) 
			
			Map<String, Object> pNContentMap = noticeBoardDao.selectPNInfo(no);	// 이전글, 다음글 정보 조회

			// DB 연결 해제
			noticeBoardDao.close();
			
			// 결과 데이터
			request.setAttribute("noticeBoardDto", noticeBoardDto);
			request.setAttribute("pNContentMap", pNContentMap);
			request.getRequestDispatcher("/notice_board/notice_view.jsp").forward(request, response);
		
		// 처리 불가능한 요청
		} else {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
			
		}
	}
}
