package sec03.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/sess")
public class SessionTest extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		HttpSession httpSession = req.getSession();
		
		out.println("새로 생성되어 얻은 HttpSession 객체 메모리의 아이디 : "
		+httpSession.getId()+ "<br>");
		out.println("최초 HttpSession객체 메모리 생성 시각 : "
		+ new Date(httpSession.getCreationTime())+ "<br>");
		out.println("최근 HttpSession객체 메모리에 접근한 시각 : "
		+ new Date(httpSession.getLastAccessedTime())+ "<br>" );
		out.println("HttpSession 객체 메모리가 톰캣서버 메모리에 올라가 유지 되는 시간  : "
		+httpSession.getMaxInactiveInterval());
		if (httpSession.isNew()) {
		out.println("처음 생성되어 얻어진 HttpSession객체 메모리이다.");
		}
		
	}
	
}