package dao;

import java.sql.Connection;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.AddressDTO;

public class AddressDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public AddressDAO(ServletContext application) {
		super(application);
	}
	
	// Connection 객체를 매개변수로 받는 생성자
	public AddressDAO(Connection con) {
		super(con);
	}
	
	/* 메소드명: select
	 * 파라미터: String id (아이디)
	 * 반환값: AddressDTO addressDto (주소 정보가 저장된 AddressDTO 객체)
	 * 설명: 회원의 주소 정보를 조회한다.
	 */
	public AddressDTO select(String id) {
		System.out.println("AddressDAO -> select 메소드 호출");
		
		AddressDTO addressDto = null;
		
		// 주소 조회하는 쿼리문
		String query = "SELECT * FROM address"
						+ " WHERE id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 주소 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, id);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 정보 가져오기
			if(rs.next()) {
				addressDto = new AddressDTO();
				
				addressDto.setId(rs.getString("id"));
				addressDto.setName(rs.getString("name"));
				addressDto.setPostCode(rs.getString("post_code"));
				addressDto.setRoadAddress(rs.getString("road_address"));
				addressDto.setDetailAddress(rs.getString("detail_address"));
			}
			
		} catch(Exception e) {
			System.out.println("주소 조회 중 예외 발생");
			System.out.println("AddressDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return addressDto;
		
	}
	
	/* 메소드명: insert
	 * 파라미터:AddressDTO addressDto (주소 정보를 가지고 있는 객체)
	 * 반환값: int result (주소 추가 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 회원의 주소 정보를 저장한다.
	 */
	public int insert(AddressDTO addressDto) {
		System.out.println("AddressDAO -> insert 메소드 호출");
		
		int result = 0;
		
		// 주소 추가하는 쿼리문
		String query = "INSERT INTO address"
						+ "(id, name, post_code, road_address, detail_address)"
						+ " VALUES(?, ?, ?, ?, ?)";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 주소 추가
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, addressDto.getId());
			psmt.setString(2, addressDto.getName());
			psmt.setString(3, addressDto.getPostCode());
			psmt.setString(4, addressDto.getRoadAddress());
			psmt.setString(5, addressDto.getDetailAddress());
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("주소 추가 중 예외 발생");
			System.out.println("AddressDAO -> insert 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}

	/* 메소드명: update
	 * 파라미터:AddressDTO addressDto (주소 정보를 가지고 있는 객체)
	 * 반환값: int result (주소 업데이트 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 회원의 주소 정보를 수정한다.
	 * 		(주소 정보가 없을 경우에는 주소 정보를 저장한다.)
	 */
	public int update(AddressDTO addressDto) {
		System.out.println("AddressDAO -> update 메소드 호출");
		
		int result = 0;
		
		// 주소 업데이트 쿼리문: 이미 주소가 저장되어 있으면 UPDATE, 없으면 INSERT 수행
		String query = "MERGE INTO address dest"
						+ " USING (SELECT ? AS id, ? AS name, ? AS post_code, ? AS road_address, ? AS detail_address FROM dual) src"
						+ " ON (dest.id = src.id)"
						+ " WHEN MATCHED THEN"
						+ " UPDATE SET dest.name = src.name, dest.post_code = src.post_code, dest.road_address = src.road_address, dest.detail_address = src.detail_address"
						+ " WHEN NOT MATCHED THEN"
						+ " INSERT VALUES(src.id, src.name, src.post_code, src.road_address, src.detail_address)";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 주소 업데이트
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, addressDto.getId());
			psmt.setString(2, addressDto.getName());
			psmt.setString(3, addressDto.getPostCode());
			psmt.setString(4, addressDto.getRoadAddress());
			psmt.setString(5, addressDto.getDetailAddress());
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("주소 업데이트 중 예외 발생");
			System.out.println("AddressDAO -> update 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}

}
