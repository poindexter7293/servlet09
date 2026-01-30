package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("welcome")
public class WelcomeServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
	
	PrintWriter out = resp.getWriter();
	Cookie[] cookies = req.getCookies();
	String userid = null;
	
	if (cookies != null) {
		for(Cookie cookie : cookies) {
			if("user_id".equals(cookie.getName())) {
				userid = URLDecoder.decode(cookie.getValue(),"UTF-8");
				}
			
			}
		}
	
	if(userid != null) {
		out.println("<h1>환영합니다, "+userid + "님! 로그인 중...</h1>");
		out.println("<a href='logout'>로그아웃</a>");
		}else {
			resp.sendRedirect("login3.html");
		}
	}
	
}
