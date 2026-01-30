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
/*
			response.setContentType("text/html; charset=UTF-8") 과
			response.setCharacterEncoding("UTF-8") 차이점
			
			- 두 코드는 서블릿(Servlet)에서 클라이언트(브라우저)에게 보내는
			  응답(HTTP Response)의 문자 인코딩을 설정하는 역할을 하지만
			  적용되는 위치와 목적이 서로 다르다.
			
			────────────────────────────────────────
			1. response.setContentType("text/html; charset=UTF-8")
			
			[역할]
			- HTTP 응답 헤더(Header)의 Content-Type 정보를 설정한다.
			- 브라우저에게
			  "이 데이터는 HTML 문서이고(text/html),
			   UTF-8 방식으로 글자를 해석하세요"
			     라고 알려주는 안내판 역할을 한다.
			
			[특징]
			- 브라우저가 어떤 형식의 데이터인지 판단하는 기준이 된다.
			- charset=UTF-8을 명시하지 않으면
			  브라우저가 기본 인코딩(예: ISO-8859-1 등)으로
			  잘못 해석하여 한글이 깨질 수 있다.
			
			────────────────────────────────────────
			2. response.setCharacterEncoding("UTF-8")
			
			[역할]
			- 서버(서블릿)가 실제로 문자열을 만들 때 사용하는 문자 변환 규칙을 설정한다.
			- response.getWriter() 로 글자를 출력할 때
			  UTF-8 규칙으로 변환하도록 지정하는 것이다.
			
			[특징]
			- 서버 내부에서 “어떤 문자 규칙으로 글자를 만들 것인지”를 정하는 설정이다.
			- 브라우저에게 직접 알려주는 기능은 없다.
			- 따라서 setContentType()과 함께 사용하는 것이 가장 안전하다.
			
			────────────────────────────────────────
			정리하면
			
			- setContentType()        → 브라우저에게 “이 문서는 UTF-8이야” 라고 알려줌
			- setCharacterEncoding()  → 서버가 “UTF-8로 글자를 만들게” 라고 내부 설정
			
			즉,
			하나는 브라우저용 안내,
			하나는 서버 내부 문자 변환 설정이다.
			
			================================================================
			인코딩(Encoding)과 디코딩(Decoding)의 의미
			
			1. 인코딩(Encoding)
			- 사람이 읽는 문자나 데이터를
			  컴퓨터 또는 다른 시스템이 이해할 수 있는 형식으로 변환하는 과정
			- 또는 한 형식을 다른 형식으로 바꾸는 과정
			
			[대표적인 인코딩 예시]
			- 문자 인코딩 : UTF-8, EUC-KR, ISO-8859-1 등
			- URL 인코딩 : 한글, 공백, 특수문자를 %XX 형태로 변환
			- Base64 인코딩 : 바이너리 데이터를 문자 형태로 변환
			  (이메일 첨부파일, JWT 토큰 등에서 사용)
			
			────────────────────────────────────────
			2. 디코딩(Decoding)
			- 인코딩된 데이터를 다시 사람이 읽을 수 있는 원래 형태로 되돌리는 과정
			- 컴퓨터 형식 → 사람 형식으로 복원하는 작업
			
			────────────────────────────────────────
			정리 표
			
			구분        설명                                           예시
			인코딩      데이터를 특정 형식으로 변환             안녕하세요 → %EC%95%88...
			디코딩      인코딩된 데이터를 원래대로 복원         %EC%95%88... → 안녕하세요
			
			즉,
			인코딩 = 변환
			디코딩 = 복원
*/




//사용자가 로그인 했는지 Cookie객체의 정보를 확인 하고, 로그인 유지 상태를 화면에 보여주는 서블릿
@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//재료
		//1. 한글처리
		request.setCharacterEncoding("UTF-8");
		
		//2. 브라우저로 응답할 메시지 유형 설정 및 한글처리1
		//   코드가 실행되는 시점 :  브라우저가 응답메세지를 해석할 문자셋 방식의 값을 utf-8을 결정하며 클라이언트의 브라우저가 문서를 해석할때 실행됨
		response.setContentType("text/html; charset=utf-8");//응답할 메세지 종류 설정  +  문자를 처리할수 있는 문자셋 방식을 UTF-8 값 으로 설정
		
		//2.1. 브라우저로 응답할 메세지  한글처리2
		//   코드가 실행되는 시점 : 서블릿이 응답 메세지를 UTF-8로 설정하며 톰캣서버가 응답메세지를 생성할때 실행됨
		response.setCharacterEncoding("UTF-8");
		
		//3. 브라우저로 응답할 출력스트림 통로 생성
		PrintWriter out = response.getWriter();
		
		
		//실제 작업
		//---------------------------------------------------------------------
		//1. 사용자의 브라우저가 설치된 PC의 쿠키파일로 저장된 Cookie객체의 정보 모두를 가져온다.
		//  -> HttpServletRequest객체의 getCookies() 메소드는 브라우저가 서버로 보낸 Cookie객체가 저장된 배열을 반환해주는 메소드이다.
		//     첫 방문자라면 Cookie객체가 없을수 있으므로 null값을 반환 하는 메소드이다.
		Cookie[] cookies = request.getCookies();
		
		//------------------------------------------------------------------
		//2. Cookie객체를 얻었으면 Cookie객체에 저장되어 있었던 쿠키값(로그인 처리시 저장했던 아이디)를 저장할 변수 선언
		String userid = null;
		
		//-----------------------------------------------------------------
		//3. Cookie객체가 Cookie[] 배열에 저장되어 있는지 확인
		if(cookies != null) {
			
			//----------------------------------------------
			//4. Cookie[] 배열에 저장되어 있는 Cookie객체를 꺼내서 확인
			// -> Cookie객체가 여러개 있을수 있으므로 검사 
			for(Cookie cookie  : cookies  ) {
				
				//---------------------------------------------
				//5. Cookie객체정보 중에서 쿠키명이 "user_id"인 Cookie객체가 있는지 검사
				if("user_id".equals(cookie.getName())) {
					
					//-----------------------------------
					//6. 찾은 Cookie객체 안에 실제 저장된 쿠키값(로그인 처리시 저장했었던 아이디 "admin")을 가져온다.
					userid = URLDecoder.decode(cookie.getValue(),"UTF-8");
					
				}//if
				
			}//for
		
		}//if
		
		//-------------------------------------------------------------------
		// 7. 이미 로그인한 사용로 판단 (userid변수에 로그인 처리시 입력한 아이디 "admin"이 저장되어 있는지 판단)
		if(userid != null) {
			//--------------------------------
			//8. 브라우저 화면에 환영 메세지 출력
			out.println("<h1>환영합니다, " +  userid + "님! 로그인중.....</h1>" );
			
			//--------------------------------
			//9. 로그아웃 처리 할수 있는 LogoutServlet을 포워딩(재요청)할 <a>링크 제공
			out.println("<a href='logout'>로그아웃</a>");
			
		}else {
			
			//----------------------------------
			//10. 로그인 된 사용자가 아닌데 이페이지를 보고 있다면?(userid변수에 로그인 처리시 입력한 아이디 "admin"가 저장되어 있지 않다면?)
			//-> 미로그인 된 상태이기떄문에 로그인 요청할수 있는 디자인 페이지 포워딩(재요청)
			response.sendRedirect("login3.html");
		}//else 
			
	}//doGet
	
}//WelcomeServlet















