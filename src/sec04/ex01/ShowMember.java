
package sec04.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/show")
public class ShowMember extends HttpServlet{
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req, resp);
	}
	
	protected void doHandle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		Boolean isLogon = false;
		
		
		HttpSession httpSession = req.getSession(false);
		
		String user_id = req.getParameter("user_id");
		String user_pw = req.getParameter("user_pw");	
		
		if(httpSession.isNew()) {
			
		}else {
			
		}
		
	}
	
}