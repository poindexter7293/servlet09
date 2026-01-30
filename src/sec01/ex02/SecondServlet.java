package sec01.ex02;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/second")
public class SecondServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		String user_id = req.getParameter("user_id");
		String user_pw = req.getParameter("user_pw");
		String user_address = req.getParameter("user_address");
		
		if (user_id != null && user_id.length() != 0) {
			out.print("이미 로그인 된 상태입니다. <br><br>");
			out.print("첫번째 서블릿 LoginServlet으로 부터 재요청 받아 전달받은 입력한 아이디 : " + user_id+ "<br>");
			out.print("첫번째 서블릿 LoginServlet으로 부터 재요청 받아 전달받은 입력한 비밀번호 : " + user_pw+ "<br>");
			out.print("첫번째 서블릿 LoginServlet으로 부터 재요청 받아 전	달받은 입력한 주소 : " + user_address+ "<br>");
		}else {
			out.print("로그인 하지 않고 두번째 서블릿인 SecondServlet 페이지를 보여주고 있습니다. <br><br>");
			out.print("다시 로그인 하고 오세요.<br>");
			out.print("<a href='/pro09/login2.html'> 로그인 요청 화면으로 다시 이동</a>");
			
		}
		
//http://localhost:8090/pro09/second?user_id=admin&user_pw=1234&user_address=%EC%84%9C%EC%9A%B8%EC%8B%9C+%EC%84%B1%EB%B6%81%EA%B5%AC
		
	}
}
