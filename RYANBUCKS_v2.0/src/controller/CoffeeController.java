package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/coffee/*")
public class CoffeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("CoffeeController init 메소드 호출");
	}

	public void destroy() {
		System.out.println("CoffeeController destory 메소드 호출");
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
		System.out.println("====================> /coffee" + action);
		
		// coffee
		if(action.equals("/index.do")) {
			request.getRequestDispatcher("/coffee_board/coffee.jsp").forward(request, response);
			
		// 커피 이야기
		} else if(action.equals("/story.do")) {
			request.getRequestDispatcher("/coffee_board/story/story.jsp").forward(request, response);
		
		// 에스프레소 음료
		} else if(action.equals("/espresso.do")) {
			request.getRequestDispatcher("/coffee_board/espresso/espresso.jsp").forward(request, response);
			
		// 처리 불가능한 요청
		} else {
			response.sendRedirect("/error.jsp");
			
		}
	}
}
