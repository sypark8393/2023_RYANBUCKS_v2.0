package dao;

import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.WriteableReviewsDTO;

public class WriteableReviewsDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public WriteableReviewsDAO(ServletContext application) {
		super(application);
	}
	
	/* 메소드명: selectList
	 * 파라미터: String memberId (회원 id)
	 * 반환값: List<WriteableReviewsDTO> list (작성 가능한 리뷰 목록)
	 * 설명: 회원이 작성 가능한 리뷰 목록을 조회한다.
	 */
	public List<WriteableReviewsDTO> selctList(String memberId) {
		System.out.println("WriteableReviewsDAO -> selctList 메소드 호출");
		
		List<WriteableReviewsDTO> list = new Vector<WriteableReviewsDTO>();
		
		// 작성 가능한 리뷰 목록을 조회하는 쿼리문
		String query = "SELECT * FROM writeable_review"
						+ " WHERE member_id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 작성 가능한 리뷰 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, memberId);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 작성 가능한 리뷰 목록 가져오기
			while(rs.next()) {
				WriteableReviewsDTO writeableReviewsDto = new WriteableReviewsDTO();
				
				writeableReviewsDto.setNo(rs.getInt("no"));
				writeableReviewsDto.setMenuNo(rs.getInt("menu_no"));
				writeableReviewsDto.setNameKor(rs.getString("name_kor"));
				writeableReviewsDto.setoptionInfo(rs.getString("option_info"));
				writeableReviewsDto.setMemberId(rs.getString("member_id"));
				
				list.add(writeableReviewsDto);
			}
			
		} catch(Exception e) {
			System.out.println("작성 가능한 리뷰 목록 조회 중 예외 발생");
			System.out.println("WriteableReviewsDAO -> selctList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
}
