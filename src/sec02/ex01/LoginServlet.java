package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
실행 테스트 흐름
1. 클라이언트가 login3.html에서 아이디,비밀번호 입력 후 로그인 버튼 클릭
2. LoginServlet에서 아이디와 비밀번호 확인 후 로그인 성공 시 쿠키 생성
3. 클라이언트가 WelcomeServlet에 접근하면 쿠키를 확인하여 로그인 상태 유지
4. 클라이언트가 logout을 클릭하면 LogoutServlet에서 쿠키 삭제 후 로그아웃 처리
*/


//LoginServlet 서블릿 클래스 역할 :  로그인 요청을 받았을때  로그인 처리 및  쿠키 생성 

@WebServlet("/login3")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//재료
		//1. 한글처리
		request.setCharacterEncoding("utf-8");
		//2. 브라우저로 응답할 메세지 유형 설정 및 한글처리
		response.setContentType("text/html; charset=utf-8");
		//3. 브라우저에 응답할 출력스트림 생성
		PrintWriter out = response.getWriter();
		
		//실제 작업
		//1. 사용자가 로그인을 하기 위해 입력한 아이디, 비밀번호 얻기
		//요약 : 요청한 데이터 얻기
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		//스토리 : 데이터베이스 서버의 테이블에  저장된 아이디, 비밀번호 -> "admin" , "1234" 가 저장되어 있다고 가정하고 하자.
		
		//2. 입력한 아이디, 비밀번호 와  데이터베이스 서버의 테이블에 저장된 아이디 "admin" 과 비밀번호 "1234"가 같은지 비교
		if( "admin".equals(user_id)  &&  "1234".equals(user_pw)  ) {

			//2.1. 로그인 성공시  Cookie객체 생성 ( 쿠키명 : user_id,  쿠키 값 : username 변수에 저장된 입력한 아이디 )
			//-> 로그인에 성공하면 "사용자 아이디를 기억하기 위한 Cookie 객체" 를 생성한다.
			Cookie userCookie  = new Cookie("user_id", user_id); 
			
			//2.2. Cookie객체의 정보( "user_id", "admin" )를  파일로 저장하는 기간(유효 시간)을 7일로 설정한다.
			//-> setMaxAge() 메소드 사용 : 시간 초 단위로 값 을 설정 하는 메소드
			//
			//   60초(1분) X  60분(1시간)   X  24시간(1일)   X  7일   =  604800초
			//->  사용자가 브라우저를 껏다 켜도 Cookie객체의 정보(로그인정보)가 쿠카파일에 저장된 상태에서 쿠키파일이 삭제되 않고
			//    사용자 PC(브라우저 저장소)에 계속 7일 동안 남아 있게 된다.
			//    : 주로 "자동 로그인", "아이디 기억하기" 기능에 사용된다.
			userCookie.setMaxAge(60 * 60 * 24 * 7);
			
			//2.3. "Cookie객체의 정보(로그인 한 정보)를 어느 요청주소까지 사용할수 있는지 범위를 설정.
        	//
        	// "/" 는 현재 웹프로젝트pro09의 “전체 주소 범위”를 의미한다.
        	// 즉, 이 웹사이트 안에 있는 모든 서버 페이지(.jsp, .do, .servlet 등)에서
        	// 이 Cookie객체의 정보(로그인 정보)를 사용할 수 있게 된다.
        	//
        	// 예를 들어 프로젝트 주소가
        	//   http://localhost:8080/pro09/
        	// 라면
        	//   setPath("/")  →  /pro09/ 아래의 모든 페이지에서 Cookie객체의 정보(로그인 정보) 사용 가능
        	//   (로그인, 게시판, 회원정보, 장바구니 등 전부 포함)
        	//
        	// 만약 아래처럼 설정하면 범위가 줄어든다.
        	//   setPath("/member")
        	//   → /pro09/member 로 시작하는 주소에서만 Cookie객체의 정보(로그인 정보) 사용 가능
        	//   → /pro09/board, /pro09/main 등에서는 Cookie객체의 정보(로그인 정보) 사용 불가
        	//
        	//
        	// Cookie객체의 정보(로그인 정보)는 사이트 전체에서 필요하므로
        	// 보통 "/" 로 설정하여 모든 페이지에서 공통 사용하도록 한다.			
			userCookie.setPath("/"); //  http://localhost:8080/pro09/
			
			//2.4. Cookie객체의 정보(로그인 정보 = 쿠키명,쿠키값 = "user_id","admin")를 JavaScript코드를 사용해서 보이지않게 숨기는 보안 설정 코드 작성
        	//
        	// 웹 브라우저에는 JavaScript라는 웹용 프로그램 언어가 있는데,
        	// 정상적인 기능에도 사용되지만,
        	// 해커가 악성 JavaScript 코드를 웹페이지에 몰래 심어 놓으면
        	// 사용자의 Cookie객체의 정보(로그인 정보) 값을 읽어서 훔쳐갈 수도 있다.
        	//
        	// 이런 공격을  XSS(Cross Site Scripting, 크로스 사이트 스크립팅) 공격 이라고 부른다.
        	// Cookie객체의 정보(로그인 정보) 안에는 로그인 정보나 세션 번호처럼 중요한 데이터가 들어있을 수 있기 때문에
        	// Cookie객체의 정보(로그인 정보)가 노출되면 다른 사람이 내 계정으로 로그인할 위험이 생긴다.
        	//
        	// setHttpOnly(true)를 설정하면
        	// 브라우저의 JavaScript코드에서는 이 Cookie객체의 정보(로그인 정보)가 “존재하지 않는 것처럼” 보이게 된다.
        	// 즉, JavaScript코드로 Cookie객체의 정보(로그인 정보)값을 읽을 수 없게 되어
        	// 해커가 쿠키를 훔쳐갈 가능성을 크게 줄여준다.
        	//
        	// 정리하면
        	//   true  → JavaScript코드 접근 차단 → 보안 강함 (권장 설정)
        	//   false → JavaScript코드 접근 허용 → 보안 약함 (위험)
        	//
        	// 로그인 유지 Cookie객체의 정보(로그인 정보) 처럼
        	// 사용자 인증과 관련된 중요한 Cookie객체의 정보(로그인 정보)는
        	// 반드시 true로 설정하는 것이 안전한 보안 방법이다.			
			userCookie.setHttpOnly(true);
			
			//2.5. 톰캣 서버의 LoginServlewt이 만든 Cookie객체의 정보 (쿠키명,쿠키값)를 브라우저에게 전달하기 위해
			//     HttpServletResponse객체 메모리에 추가 한다.
			//     -> 브라우저는  이 Cookie객체의 정보(쿠키명, 쿠키값)를 사용자 PC에 쿠키파일로 자동 저장하게 됨 
			response.addCookie(userCookie);
			
			//2.6. 로그인 성공 처리 후 "WelcomeServlet" 서블릿를 포워딩(재요청)해 이동시킨다.
			//     -> 사용자는 환영 화면 또는 메인화면을 보게 될것이다.
			response.sendRedirect("welcome");
				
			
		}else {//2. 입력한 아이디, 비밀번호 가 DB의 테이블에 저장되어 있지 않은 경우!
			
			//3. 로그인 실패 시 오류 메세지를 브라우저로 출력
			//요약 :  웹브라우저로 응답 메세지 생성후 응답
			out.print("로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
		}

	}
	
}














