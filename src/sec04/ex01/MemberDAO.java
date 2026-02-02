package sec04.ex01;



//주제 : Statement 를  PreparedStatement 로 업그레이드 


/*
	DAO 클래스 역할
	- 오라클 DBMS 서버에 만들어져 있는 t_member테이블과 연결 하여 
	  데이터베이스 작업(비즈니스로직 처리) - SELECT, INSERT, DELETE, UPDATE 을 하는 클래스.
*/


import java.sql.*;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private DataSource dataSource;
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context)ctx.lookup("java:/comp/env");
			dataSource = (DataSource)envCtx.lookup("jdbc/oracle");
			
		} catch (Exception e) {
			System.out.println("DataSource 커넥션풀 객체 얻기 실패 : "+e.toString());
		}
	}
	
	//isExisted
	
	public boolean isExisted(MemberVO memberVO) {
		
		boolean result = false;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select decode(count(*), 1, 'true', 'false') as result from t_member "
			+ "where id=? and pwd=?";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, memberVO.getId());
			pstmt.setString(2, memberVO.getPwd());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				result = Boolean.parseBoolean(rs.getString("RESULT"));
				
				
			}
			
		} catch (Exception e) {
			System.out.println("MemberDAO의 isExited 메소드 내부에서 select SQL문 실행 오류 : " + e);
		}finally {
			ResourceClose();
		}
		
		return false;
	}

	public void delMember(String id) {
		int result = 0;
		try {
			con = dataSource.getConnection();
			String query = "delete from t_member where id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("MemberDAO의 delMember 메소드 내부의 코드에서 delete문 실행 오류 : "+e);
		}finally {
			ResourceClose();
		}
	}
	public void fixMember(MemberVO vo) {
		int result = 0;
		try {
			con = dataSource.getConnection();
			String[] fix = new String[3];
			fix[0] = vo.getPwd();
			fix[1] = vo.getName();
			fix[2] = vo.getEmail();
			
			for (int i = 0; i < 3; i++) {
				if (fix[i] == null) {
					
				} else {
					String query = "update t_member set ";
					pstmt = con.prepareStatement(query);
				}
				
			}
			
			
			pstmt.setString(1, vo.getId());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("MemberDAO의 delMember 메소드 내부의 코드에서 delete문 실행 오류 : "+e);
		}finally {
			ResourceClose();
		}
		
		
	}
	
	public int addMember(MemberVO vo) {
		int result = 0;
		
		try {
			con = dataSource.getConnection();
			
			String id = vo.getId();
			String pwd = vo.getPwd();
			String name = vo.getName();
			String email = vo.getEmail();
			
			//String query = "insert into t_member(id, pwd, name, email)" 
			//			 + "values('"+id+"', '" +pwd+ "', "+name+", "+email+")";
			
			String query = "insert into t_member(id, pwd, name, email)"
							+ "values(?, ?, ?, ?)" ;
						
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("MemberDAO 클래스 addmember 메소드 내부 코드에서 insert sql문 실행 오류 : "+ e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return result;
	}
	
	public ArrayList listMembers() {
		
		//t_member테이블에 저장된 모든 회원 레코드들을 조회 해서 가져와 
		//가변길이 배열의 각 index위치 칸에 임시로 저장할 배열공간인? ArrayList 배열 생성 
		ArrayList  list = new ArrayList();
		
		try {
			
			con = dataSource.getConnection();
			
			String query = "select * from t_member";
			
			//순서5.1. T4CConnection객체의 prepareStatement메소드 호출시~~~ 매개변수로 순서5.에서 미리 준비한 select * from t_member 문장을 전달하면
			//        OraclePreparedStatementWrapper 실행 객체 메모리에  select * from t_member 문장 전체를 저장 한 후 
			//         OraclePreparedStatementWrapper 실행 객체 주소 자체를 반환 해 줍니다.
			pstmt = con.prepareStatement(query);
			/*
			OraclePreparedStatementWrapper 실행 객체 메모리 안에 저장된 모습
			----------------------------------------------
			select * from t_member id=?
			-----------------------------------------------	
			*/
			//pstmt.setString(1,"hong");
			/*
			OraclePreparedStatementWrapper 실행 객체 메모리 안에 저장된 모습
			----------------------------------------------
			select * from t_member where id='hong'
			-----------------------------------------------	
			*/
			
			
			//순서6. SQL문장을  오라클 DBMS 서버의 XE 데이터베이스의 t_member테이블에 전송(전달)해서 실행!(조회)
			//"select * from t_member" SQL문을 이용하여 조회 후 조회한 결과 데이터들을 ResultSet객체 메모리에 저장후 반환 받습니다.
			//단! 조회된 화면의 커서(화살표) 위치는 가장 처음에 조회된 표형태의 제목열 행 가리키고 있다.
			rs = pstmt.executeQuery(); //<- ResultSet 객체 메모리 반환 
			
			
			//순서7. 조회된 회원 레코드들이 ResultSet임시 객체 메모리에 표형태로 저장되어 있으면 계속반복해서
			//회원 레코드(행)단위의 조회된 열(컬럼)값들을 차례대로 얻어
			//MemberVO객체를 행단위로 생성하여 각 인스턴스변수에 저장시킵니다.
			//마지막으로 생성된 MemberVO객체들을 차례대로 ArrayList배열에 반복해서 추가시킵니다.
			while(rs.next()) {
				
				//커서가 위치한 조회된 회원 레코드(한 행의 데이터)의 열의 값들을 차례대로 얻어 변수에 저장
				String id = rs.getString("ID"); //"hong" , "lee",    "kim"
				String pwd = rs.getString("PWD");//"1212",  "1212",  "1212"
				String name = rs.getString("NAME");//"홍길동", "이순신",  "김유신"
				String email = rs.getString("EMAIL");//"hong@gamil.com", "lee@test.com", "kim@web.com"
				Date   joinDate =  rs.getDate("JOINDATE"); // new Date("2026/01/27"); 
														   // new Date("2026/01/27");
														   // new Date("2026/01/27");
										
				
				//MemberVO객체를 행단위로 생성하여 각 인스턴스변수에 저장시킵니다.
				MemberVO vo = new MemberVO();
						 vo.setId(id);
						 vo.setPwd(pwd);
						 vo.setName(name);
						 vo.setEmail(email);
						 vo.setJoinDate(joinDate);
						 
				//마지막으로 생성된 MemberVO객체를 차례대로 ArrayList배열에 반복해서 추가시킵니다.
				list.add(vo);
				
				//ArrayList가변길이 배열 모습
				//[ MemberVO,  MemberVO,  MemberVO ]
				//    0          1            2       index	
			}		
					
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//순서9. DB작업 관련 객체 메모리를 모두 사용하고 난 다음  필요 없으면 메모리 낭비 이므로 메모리 톰캣에서 제거 
			ResourceClose();
		}
		
		return  list; //ArrayList 배열 메모리 자체를 MemberServlet으로 반환
	}
	
	//DB 작업 관련 객체 메모리들 사용이 끝난 후 자원 해제 하는 기능의 메소드
	public void ResourceClose() {		
		try {
			//ResultSet객체는 SQL문(SELECT)의 조회된 결과데이터를 임시로 저장하는 객체 입니다.
			//이 객체를 사용하고 난다음에  제거시키자.
			if(rs != null) rs.close();
			
			//PreparedStatement객체는 SQL문(SELECT)를 실행하는 객체 입니다.
			//이 객체를 사용하고 난 다음에 제거 시키자.
			if(pstmt != null) pstmt.close();
			
			//T4CConnection객체는  데이터베이스와의 연결을 관리하는 객체로, 더이상 데이터베이스 연결할 필요가 없으면 
			//이객체를 사용하고 난 다음에 제거 시키자.
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	

	
}// class MemberDAO  











