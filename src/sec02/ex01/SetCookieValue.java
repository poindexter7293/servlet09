package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/set")
public class SetCookieValue extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
		PrintWriter out = resp.getWriter();
		
		Date date = new Date();
		Cookie cookie = new Cookie("cookieTest", URLEncoder.encode("JSP프로그래밍입니다.", "UTF-8"));
		
		cookie.setMaxAge(60*60*24);
		cookie.setMaxAge(-1);
		
		resp.addCookie(cookie);
		
		out.println("현재 시간 : " + date.toString());
		out.println("Cookie객체를 생성해서 웹브라우저의 쿠키저장소 공간으로 보냈습니다.");
		
	}
	
	
	
	
	
	
	
	
}