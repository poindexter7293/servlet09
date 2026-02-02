package sec04.ex01;

import java.sql.Date;

public class MemberVO {

	private String id;
	private String pwd;
	private String name;
	private String email;
	private String joinDate;
	
	public MemberVO() {
		
		System.out.println("MemberVO 생성자 호출");
	}
	
	public MemberVO(String id, String pwd, String name, String email) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate2) {
		// TODO Auto-generated method stub
		this.joinDate = joinDate;
		
	}
	
/*	public void setJoinDate(String joinDate) {
	}*/
	
}
