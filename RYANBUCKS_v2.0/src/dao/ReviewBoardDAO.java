package dao;

import java.sql.Connection;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.ReviewBoardDTO;
public class ReviewBoardDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public ReviewBoardDAO(ServletContext application) {
		super(application);
	}
	
	// Connection 객체를 매개변수로 받는 생성자
	public ReviewBoardDAO(Connection con) {
		super(con);
	}
	
	/* 메소드명: insert
	 * 파라미터: ReviewBoardDTO reviewBaordDto (리뷰 정보를 가지고 있는 객체)
	 * 반환값: int reviewNo (작성된 리뷰 번호 -> 0 이외의 값: 리뷰 작성 성공, 0: 리뷰 작성 실패)
	 * 설명: 리뷰 정보를 저장한다.
	 */
	public int insert(ReviewBoardDTO reviewBoardDto) {
		System.out.println("ReviewBoardDAO -> insert 메소드 호출");
		
		int reviewNo = 0;
		
		// 리뷰 작성하는 쿼리문
		String query = "INSERT INTO review_board (no, rate, content, member_id)"
						+ " VALUES(seq_review_board_no.NEXTVAL, ?, ?, ?)";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 리뷰 작성하기
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, reviewBoardDto.getRate());
			psmt.setString(2, reviewBoardDto.getContent());
			psmt.setString(3, reviewBoardDto.getMemberId());	
			
			// 동적 쿼리문 실행
			int result = psmt.executeUpdate();
			
			// 업데이트 성공 시
			if(result == 1) {
				// 정적 쿼리문 생성
				stmt = con.createStatement();
				
				// 정적 쿼리문 실행
				rs = stmt.executeQuery("SELECT seq_review_board_no.CURRVAL FROM DUAL");
				
				if(rs.next()) {
					reviewNo = rs.getInt(1);
				}
				
			}
			
		} catch(Exception e) {
			System.out.println("리뷰 작성 중 예외 발생");
			System.out.println("ReviewBoardDAO -> insert 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return reviewNo;
	}
}
