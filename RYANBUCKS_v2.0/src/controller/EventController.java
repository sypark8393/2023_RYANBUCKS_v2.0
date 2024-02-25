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

import dao.EventBoardDAO;
import dao.EventImageDAO;
import dto.EventBoardDTO;

@WebServlet("/event/*")
public class EventController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public void init(ServletConfig config) throws ServletException {
		System.out.println("EventController init 메소드 호출");
	}

	public void destroy() {
		System.out.println("EventController destory 메소드 호출");
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
		System.out.println("====================> /event" + action);
		
		// event
		if(action.equals("/index.do")) {
			request.getRequestDispatcher("/event/event_list.do").forward(request, response);
		
		// 이벤트 목록
		} else if(action.equals("/event_list.do")) {
			// DAO 생성 후 Oracle DB 연결
			EventBoardDAO eventBoardDao = new EventBoardDAO(request.getServletContext());
			EventImageDAO eventImageDao = new EventImageDAO(request.getServletContext());
			
			// 데이터 처리
			List<EventBoardDTO> eventList = eventBoardDao.selectIngList();					// 진행중인 이벤트 목록 조회
			List<String> eventImageList = eventImageDao.selectThumFileNameList(eventList);	// 진행중인 이벤트 썸네일 목록 조회
			
			List<EventBoardDTO> endEventList = eventBoardDao.selectEndList();					// 종료된 이벤트 목록 조회
			List<String> endEventImageList = eventImageDao.selectThumFileNameList(endEventList);	// 종료된 이벤트 썸네일 목록 조회
			
			// DB 연결 해제
			eventBoardDao.close();
			eventImageDao.close();
			
			// 결과 데이터
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("eventList", eventList);
			resultMap.put("eventImageList", eventImageList);
			resultMap.put("endEventList", endEventList);
			resultMap.put("endEventImageList", endEventImageList);
			
			request.setAttribute("resultMap", resultMap);
			request.getRequestDispatcher("/event_board/event_list.jsp").forward(request, response);
			
		// 이벤트 상세보기
		} else if(action.equals("/event_view.do")) {
			int no = Integer.parseInt(request.getParameter("no"));	// 이벤트 게시물 번호
			
			// DAO 생성 후 Oracle DB 연결 (1)
			EventBoardDAO eventBoardDao = new EventBoardDAO(request.getServletContext());
			
			// 데이터 처리 (1)
			EventBoardDTO eventBoardDto = eventBoardDao.select(no);				// 이벤트 게시물 내용 조회
			
			// 없는 게시물이거나 숨김 상태인 경우
			if(eventBoardDto == null || eventBoardDto.getVisibility().equals("hidden")) {
				// DB 연결 해제
				eventBoardDao.close();
				
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			// DAO 생성 후 Oracle DB 연결 (2)
			EventImageDAO eventImageDao = new EventImageDAO(request.getServletContext());
			
			// 데이터 처리 (2)
			eventBoardDao.updateVisitCount(no);										// 조회수 증가 (*처리 성공 여부에 따른 분기 처리 생략) 
			List<String> eventImageList = eventImageDao.selectContentFileNameList(no);	// 이벤트 게시물 내용 파일명 목록 조회
			
			// DB 연결 해제
			eventBoardDao.close();
			eventImageDao.close();
			
			// 결과 데이터
			request.setAttribute("eventBoardDto", eventBoardDto);
			request.setAttribute("eventImageList", eventImageList);
			request.getRequestDispatcher("/event_board/event_view.jsp").forward(request, response);
			
		// 처리 불가능한 요청
		} else {
			response.sendRedirect("/error.jsp");
			
		}
	}

}
