package sec04.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login5")
public class LoginServlet extends HttpServlet{

	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String user_id = req.getParameter("user_id");
		String user_pw = req.getParameter("user_pw");
		
		MemberVO memberVO = new MemberVO();
				 memberVO.setId(user_id);
				 memberVO.setPwd(user_pw);
	
		MemberDAO memberDAO = new MemberDAO();
		
		boolean result = memberDAO.isExisted(memberVO);
		
		if(result) {
			HttpSession session = req.getSession();
			session.setAttribute("isLogon", true);
			session.setAttribute("login.id", user_id);
			session.setAttribute("login.pw", user_pw);
			
			out.print("<html>");
			out.print("<body>");
			
			out.print("로그인 되셨습니다 "+user_id+"님 환영합니다.");
			out.print("<a href='show'>회원정보 조회</a>");
			
			out.print("</body>");
			out.print("</html>");
		}else {
			out.print("<html>");
			out.print("<body>");
			
			out.print("<center>회원 아이디 또는 비밀번호가 틀립니다. 다시 확인 해 주세요.</center>");
			out.print("<a href = 'login5.html'>다시 로그인 요청하러 가기</a>");
			out.print("</body>");
			out.print("</html>");
			
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		doHandle(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req,resp);
		
		
	}
	
	
	
	
	
	
}
