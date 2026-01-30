package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/login3")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
		PrintWriter out = resp.getWriter();
		
		String user_id = req.getParameter("user_id");
		String user_pw = req.getParameter("user_pw");
		
		if ( "admin".equals(user_id) && "1234".equals(user_pw) ) {
			
			Cookie userCookie = new Cookie("user_id", user_id);
			
			userCookie.setMaxAge(60 * 60 * 24 * 7);
			userCookie.setPath("/");
			
			userCookie.setHttpOnly(true);
			resp.addCookie(userCookie);
			resp.sendRedirect("welcome");
			
		}else {
			resp.getWriter().print("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
			
		}
		
		
	}
	
}
