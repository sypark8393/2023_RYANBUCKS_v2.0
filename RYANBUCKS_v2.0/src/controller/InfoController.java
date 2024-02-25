package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/info/*")
public class InfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		System.out.println("InfoController init 메소드 호출");
	}

	public void destroy() {
		System.out.println("InfoController destory 메소드 호출");
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
		System.out.println("====================> /info" + action);
		
		// info
		if(action.equals("/index.do")) {
			request.getRequestDispatcher("/info/sitemap.do").forward(request, response);
		
		// 사이트맵
		} else if(action.equals("/sitemap.do")) {
			request.getRequestDispatcher("/sitemap/sitemap.jsp").forward(request, response);
		
		// 처리 불가능한 요청
		} else {
			response.sendRedirect("/error.jsp");
			
		}
				
	}
	
}
