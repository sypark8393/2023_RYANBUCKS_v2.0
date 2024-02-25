package dao;

import java.sql.Connection;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.MemberDTO;

public class MemberDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public MemberDAO(ServletContext application) {
		super(application);
	}

	// Connection 객체를 매개변수로 받는 생성자
	public MemberDAO(Connection con) {
		super(con);
	}
	
	/* 메소드명: isAvailableId
	 * 파라미터: String id (아이디)
	 * 반환값: boolean result (아이디 사용 가능 여부 -> true: 사용 가능, false: 사용 불가)
	 * 설명: 입력된 아이디의 사용 가능 여부를 확인한다.
	 */
	public boolean isAvailableId(String id) {
		System.out.println("MemberDAO -> isAvailableId 메소드 호출");
		
		boolean result = false;
		
		// 아이디 조회하는 쿼리문
		String query = "SELECT id result FROM member"
						+ " WHERE id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 아이디 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, id);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 아이디 가져오기
			if(!rs.next()) {	// 일치하는 아이디가 없는 경우 true(사용 가능)
				result = true; 
			}
			
		} catch(Exception e) {
			System.out.println("아이디 조회 중 예외 발생");
			System.out.println("MemberDAO -> isAvailableId 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* 메소드명: selectName
	 * 파라미터: String id (아이디), String password (비밀번호)
	 * 반환값: String name (회원 이름)
	 * 설명: 로그인을 시도한 회원의 이름을 조회한다.
	 */
	public String selectName(String id, String password) {
		System.out.println("MemberDAO -> selectName 메소드 호출");

		String name = null;
		
		// 회원 조회하는 쿼리문
		String query = "SELECT name FROM member"
						+ " WHERE id=? AND password=? AND out_date IS NULL";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 회원 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, id);
			psmt.setString(2, password);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 이름 가져오기
			if(rs.next()) {
				name = rs.getString("name");
			}
			
		} catch(Exception e) {
			System.out.println("회원 조회 중 예외 발생");
			System.out.println("MemberDAO -> selectName 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return name;
	}
	
	/* 메소드명: selectId
	 * 파라미터: String name (이름), String tel (연락처)
	 * 반환값: String id (회원 아이디)
	 * 설명: 회원의 아이디를 조회한다.
	 */
	public String selectId(String name, String tel) {
		System.out.println("MemberDAO -> selectId 메소드 호출");

		String id = null;
		
		// 회원 조회하는 쿼리문
		String query = "SELECT id FROM member"
						+ " WHERE name=? AND tel=? AND out_date IS NULL";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 회원 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, name);
			psmt.setString(2, tel);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 아이디 가져오기
			if(rs.next()) {
				id = rs.getString("id");
			}
			
		} catch(Exception e) {
			System.out.println("회원 조회 중 예외 발생");
			System.out.println("MemberDAO -> selectId 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return id;
	}
	
	/* 메소드명: selectPassword
	 * 파라미터: String id (아이디), String name (이름), String tel (연락처)
	 * 반환값: String password (회원 비밀번호)
	 * 설명: 회원의 비밀번호를 조회한다.
	 */
	public String selectPassword(String id, String name, String tel) {
		System.out.println("MemberDAO -> selectPassword 메소드 호출");

		String password = null;
		
		// 회원 조회하는 쿼리문
		String query = "SELECT password FROM member"
						+ " WHERE id=? AND name=? AND tel=? AND out_date IS NULL";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 회원 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, id);
			psmt.setString(2, name);
			psmt.setString(3, tel);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 이름 가져오기
			if(rs.next()) {
				password = rs.getString("password");
			}
			
		} catch(Exception e) {
			System.out.println("회원 조회 중 예외 발생");
			System.out.println("MemberDAO -> selectPassword 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return password;
		
	}
	
	/* 메스드명: select
	 * 파라미터: String id (아이디)
	 * 반환값: MemberDTO memberDTO (회원 정보가 저장된 MemberDTO 객체)
	 * 설명: 회원의 회원 정보를 조회한다.
	 */
	public MemberDTO select(String id) {
		System.out.println("MemberDAO -> select 메소드 호출");
		
		MemberDTO memberDto = null;
		
		// 회원 조회하는 쿼리문
		String query = "SELECT * FROM member"
						+ " WHERE id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 회원 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, id);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 정보 가져오기
			if(rs.next()) {
				memberDto = new MemberDTO();
				
				memberDto.setId(rs.getString("id"));
				//memberDto.setPassword(rs.getString("password"));
				memberDto.setName(rs.getString("name"));
				memberDto.setJoinDate(rs.getDate("join_date"));
				memberDto.setEmail(rs.getString("email"));
				memberDto.setTel(rs.getString("tel"));
				memberDto.setOutDate(rs.getDate("out_date"));
				memberDto.setGender(rs.getString("gender"));
				memberDto.setBirth(rs.getDate("birth"));
				memberDto.setMarketingInfoAgree(rs.getString("marketing_info_agree"));
			}
			
		} catch(Exception e) {
			System.out.println("회원 조회 중 예외 발생");
			System.out.println("MemberDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return memberDto;
		
	}
	
	/* 메소드명: insert
	 * 파라미터: MemberDTO memberDto (회원 정보를 가지고 있는 객체)
	 * 반환값: int result (회원 추가 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 회원 정보를 저장한다.
	 */
	public int insert(MemberDTO memberDto) {
		System.out.println("MemberDAO -> insert 메소드 호출");
		
		int result = 0;
		
		// 회원 추가하는 쿼리문
		String query = "INSERT INTO member"
						+ "(id, password, name, email, tel, gender, birth, marketing_info_agree)"
						+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		//System.out.println("SQL 쿼리문: " + query);

		// 회원 추가
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, memberDto.getId());
			psmt.setString(2, memberDto.getPassword());
			psmt.setString(3, memberDto.getName());
			psmt.setString(4, memberDto.getEmail());
			psmt.setString(5, memberDto.getTel());
			psmt.setString(6, memberDto.getGender());
			psmt.setDate(7, memberDto.getBirth());
			psmt.setString(8, memberDto.getMarketingInfoAgree());
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("회원 추가 중 예외 발생");
			System.out.println("MemberDAO -> insert 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* 메소드명: updateOutDate
	 * 파라미터: String id (아이디)
	 * 반환값: int result (회원 정보 업데이트 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 회원의 탈퇴일자 정보를 수정한다.
	 */
	public int updateOutDate(String id) {
		System.out.println("MemberDAO -> updateOutDate 메소드 호출");
		
		int result = 0;
		
		// 회원 추가하는 쿼리문
		String query = "UPDATE member SET out_date=SYSDATE"
						+ " WHERE id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 회원 추가
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, id);
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("회원 탈퇴일자 업데이트 중 예외 발생");
			System.out.println("MemberDAO -> updateOutDate 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}

	/* 메소드명: updatePassword
	 * 파라미터: String id (아이디), String newPassword (새 비밀번호)
	 * 반환값: int result (비밀번호 업데이트 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 회원의 비밀번호를 수정한다.
	 */
	public int updatePassword(String id, String newPassword) {
		System.out.println("MemberDAO -> updatePassword 메소드 호출");
		
		int result = 0;
		
		// 비밀번호 업데이트하는 쿼리문
		String query = "UPDATE member SET password = ?"
						+ " WHERE id = ?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 비밀번호 업데이트
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, newPassword);
			psmt.setString(2, id);
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("비밀번호 업데이트 중 예외 발생");
			System.out.println("MemberDAO -> updatePassword 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}

	/* 메소드명: update
	 * 파라미터: 파라미터: MemberDTO memberDto (회원의 정보를 가지고 있는 객체)
	 * 반환값: int result (회원정보 업데이트 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 회원의 정보를 수정한다.
	 */
	public int update(MemberDTO memberDto) {
		System.out.println("MemberDAO -> update 메소드 호출");
		
		int result = 0;
		
		// 회원정보 업데이트하는 쿼리문
		String query = "UPDATE member SET birth=?, email=?, marketing_info_agree=?"
						+ " WHERE id = ?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 회원정보 업데이트
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setDate(1, memberDto.getBirth());
			psmt.setString(2, memberDto.getEmail());
			psmt.setString(3, memberDto.getMarketingInfoAgree());
			psmt.setString(4, memberDto.getId());
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("회원정보 업데이트 중 예외 발생");
			System.out.println("MemberDAO -> update 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}
}

