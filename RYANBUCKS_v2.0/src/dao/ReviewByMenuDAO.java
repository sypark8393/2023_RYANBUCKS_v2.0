package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.ReviewByMenuDTO;

public class ReviewByMenuDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public ReviewByMenuDAO(ServletContext application) {
		super(application);
	}

	/* 메소드명: selectAverageRate
	 * 파라미터: int menuNo (메뉴 번호)
	 * 반환값: double averageRate (평균 평점)
	 * 설명: 특정 메뉴에 부여된 별점의 평균을 조회한다.
	 */
	public double selectAverageRate(int menuNo) {
		System.out.println("ReviewByMenuDAO -> averageRate 메소드 호출");
		
		double averageRate = 0;
		
		// 평균 평점을 조회하는 쿼리문
		String query = "SELECT TRUNC(avg(rate), 1) result FROM review_by_menu"
						+ " WHERE menu_no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 평균 평점 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, menuNo);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
				
			// 평균 평점 가져오기
			if(rs.next()) {
				averageRate = rs.getDouble("result");
			}
			
		} catch(Exception e) {
			System.out.println("평균 평점 조회 중 예외 발생");
			System.out.println("ReviewByMenuDAO -> selectAverageRate 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return averageRate;
		
	}

	/* 메소드명: selectCountByRate
	 * 파라미터: int menuNo (메뉴 번호)
	 * 반환값: List<Integer> list
	 * 설명: 툭정 메뉴의 부여된 별점별 개수 목록을 조회한다.
	 */
	public List<Integer> selectCountByRate(int menuNo) {
		System.out.println("ReviewByMenuDAO -> selectCountByRate 메소드 호출");
		
		List<Integer> countBylist = new ArrayList<Integer>();
		
		// 별점 비율을 조회하는 쿼리문
		String query = "SELECT COUNT(*) result FROM review_by_menu"
						+ " WHERE rate=? AND menu_no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 별점 비율 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(int i=1; i<=5; i++) {
				// 칼럼 값 채우기
				psmt.setInt(1,  i);
				psmt.setInt(2, menuNo);
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
				
				// 별점 비율 가져오기
				if(rs.next()) {
					countBylist.add(rs.getInt("result"));
				}
			}
		
		} catch(Exception e) {
			System.out.println("별점 비율 조회 중 예외 발생");
			System.out.println("ReviewByMenuDAO -> selectCountByRate 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return countBylist;
	}

	/* 메소드명: selectCount
	 * 파라미터: int menuNo (메뉴 번호)
	 * 반환값: int totalCount (해당 메뉴의 리뷰 개수)
	 * 설명: 특정 메뉴에 작성된 리뷰의 개수를 조회한다.
	 */
	public int selectCount(int menuNo) {
		System.out.println("ReviewByMenuDAO -> selectCount 메소드 호출");
		
		int totalCount = 0;
		
		// 리뷰 개수를 조회하는 쿼리문
		String query = "SELECT COUNT(*) total_count FROM review_by_menu"
						+ " WHERE menu_no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 리뷰 개수 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, menuNo);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 리뷰 개수 가져오기
			if(rs.next()) {
				totalCount = rs.getInt("total_count");
			}
			
		} catch(Exception e) {
			System.out.println("리뷰 개수 조회 중 예외 발생");
			System.out.println("ReviewByMenuDAO -> selectCount 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	/* 메소드명: selectList
	 * 파라미터: Map<String, Object> condition (데이터 조건이 저장된 맵)
	 * 반환값: List<ReviewByMenuDTO> list (리뷰 목록)
	 * 설명: 특정 메뉴의 작성된 리뷰 중 특정 행에 해당되는 리뷰를 정렬 기준(최신순, 별점높은순, 별점낮은순)에 맞게 조회한다. 
	 */
	public List<ReviewByMenuDTO> selectList(Map<String, Object> condition) {
		System.out.println("ReviewByMenuDAO -> selectList 메소드 호출");
		
		List<ReviewByMenuDTO> list = new Vector<ReviewByMenuDTO>();
		
		// 리뷰 목록을 조회하는 쿼리문
		String query = "SELECT * FROM"
						+ " (SELECT menu_no, post_date, rate, content, visibility, member_id, option_info, ROWNUM r_num FROM"
						+ " (SELECT menu_no, post_date, rate, content, visibility, member_id, option_info"
						+ " FROM review_by_menu"
						+ " WHERE menu_no=?"
						+ " ORDER BY ";
		
		if(condition.get("sort").equals("latest")) { // 최신순
			query += " post_date DESC";
		
		} else if(condition.get("sort").equals("highest")) { // 별점높은순
			query += " rate DESC";
		
		} else if(condition.get("sort").equals("lowest")) { // 별점낮은순
			query += " rate";
		}
		
		query += ")"
				+ " )"
				+ " WHERE r_num BETWEEN ? AND ?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 리뷰 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, Integer.parseInt(condition.get("menuNo").toString()));
			psmt.setString(2, condition.get("start").toString());
			psmt.setString(3, condition.get("end").toString());
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 리뷰 목록 가져오기
			while(rs.next()) {
				ReviewByMenuDTO reviewByMenuDto = new ReviewByMenuDTO();
				
				reviewByMenuDto.setMenuNo(rs.getInt("menu_no"));
				reviewByMenuDto.setPostDate(rs.getDate("post_date"));
				reviewByMenuDto.setRate(rs.getInt("rate"));
				reviewByMenuDto.setContent(rs.getString("content"));
				reviewByMenuDto.setVisibility(rs.getString("visibility"));
				reviewByMenuDto.setMemberId(rs.getString("member_id"));
				reviewByMenuDto.setOptionInfo(rs.getString("option_info"));
				
				list.add(reviewByMenuDto);
			}
			
		} catch(Exception e) {
			System.out.println("리뷰 조회 중 예외 발생");
			System.out.println("ReviewByMenuDAO -> selectList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
}
