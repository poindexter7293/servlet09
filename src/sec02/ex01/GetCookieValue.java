package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCookieValue extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset = UTF-8");
		PrintWriter out = resp.getWriter();
		
		Cookie[] allValue = req.getCookies();
		
		for (int i = 0; i < allValue.length; i++) {
			if(allValue[i].getName().equals("cookieTest")) {
				out.print("웹브라우저 쿠키 저장소에서 가져온 Cookie 객체 내부의 쿠키명 = cookieTest");
				out.print(URLDecoder.decode(allValue[i].getValue(),"UTF-8")); 
			
			}
			
			
		}
	
	}
	
}
