package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//로그아웃 요청이 들어오면
//웹브라우저를 사용하는 사용자PC에 저장된 쿠키파일의 정보를 삭제해서 로그아웃 서비스를 하는  서블릿 

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//재료
		//1. 한글처리
		request.setCharacterEncoding("utf-8");
		//2. 브라우저로 응답할 메세지 유형 설정 및 한글처리
		response.setContentType("text/html; charset=utf-8");
		//3. 브라우저에 응답할 출력스트림 생성
		PrintWriter out = response.getWriter();
		
		//실제 로그아웃 처리 작업
		//1. "user_id"라는 이름의 Cookie객체를 삭제 하기 위해  
		//    동일한 이름을 가진 새로운 Cookie객체를 하나 만든다.
		//    (쿠키값은 의미가 없으므로 ""빈문자열로 넣는다)
		//     -> 쿠키는 "이름"(user_id)이 같아야 덮어 쓰기/삭제 가 가능하다.
		Cookie cookie = new Cookie("user_id","");
		
		//2. Cookie객체의 정보가 저장되는 유효시간을 0초로 설정한다.
		//   -> 브라우저에게 "이 Cookie객체의 정보는 지금 당장 삭제해라"라는 의미
		//   -> setMaxAge(0); = 즉시 삭제 
		cookie.setMaxAge(0);
		
		//3.Cookie객체의 정보를 사용하기 위한 클라이언트의 요청 URL 경로 Cookie에 설정
		//  -> 처음 LoginServlet.java 파일에서 Cookie객체를 만들어 설정한것 처럼 동일하게 설정해야 한다.
		//	     이유 : 브라우저가 같은 Cookie객체임을 인식하고 정상적으로 삭제한다.
		cookie.setPath("/");
		
		//4. 삭제 설정된 Cookie객체의 정보를 브라우저에게 다시 내보내어 응답하기 위해 
		//   HttpServletResponse객체에 위 새로 생성된 Cookie객체를 추가 해서 저장시킨다.
		//  -> 브라우저는 이 정보를 보고 기존 Cookie객체를 제거 한다.
		response.addCookie(cookie);
		
		//5. 4.에서 로그아웃 처리 후 로그인 요청 화면(login3.html)을 재요청해서 보여준다.
		response.sendRedirect("login3.html");
	}
	
}







