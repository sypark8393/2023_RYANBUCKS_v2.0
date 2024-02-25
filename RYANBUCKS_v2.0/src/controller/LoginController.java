package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;

@WebServlet("/login/*")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("LoginServlet init 메소드 호출");
	}

	public void destroy() {
		System.out.println("LoginServlet destory 메소드 호출");
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
		
		String action = request.getPathInfo();	// URL에서 요청명을 가져옴
		System.out.println("====================> /login" + action);
		
		// 세션 얻어오기
		HttpSession session = request.getSession();
		
		// 로그인
		if(action.equals("/login.do")) {
			request.getRequestDispatcher("/member/login/login.jsp").forward(request, response);
			
		// 로그인 프로세스
		} else if(action.equals("/login_process")) {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			//System.out.println("id: " + id);
			//System.out.println("password: " + password);
			
			// DAO 생성 후 Oracle DB 연결
			MemberDAO memberDao = new MemberDAO(request.getServletContext());
			
			// 데이터 처리
			String name = memberDao.selectName(id, password);	// 회원 조회
			
			// DB 연결 해제
			memberDao.close();
			
			// 로그인 성공 시
			if(name != null) {
				System.out.println("로그인 성공: " + id);
				
				session.setAttribute("id", id);		// 세션에 회원 ID 저장
				session.setAttribute("name", name);	// 세션에 회원 이름 저장
				
				response.getWriter().print(name);

			// 로그인 실패 시
			} else {
				System.out.println("로그인 실패: " + id);
				
				response.getWriter().print("null");
				
			}
		
		// 로그아웃 프로세스
		} else if(action.equals("/logout_process")) {
			session.removeAttribute("id");		// 세션에 저장된 회원 ID 삭제
			session.removeAttribute("name");	// 세션에 저장된 회원 이름 삭제
		
		// 처리 불가능한 요청
		} else {
			response.sendRedirect("/error.jsp");
			
		}
		
	}

}
