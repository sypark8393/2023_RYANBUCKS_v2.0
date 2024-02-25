package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.NoticeBoardDTO;

public class NoticeBoardDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public NoticeBoardDAO(ServletContext application) {
		super(application);
	}
	
	/* 메소드명: selectCount
	 * 파라미터: String keyword (검색어)
	 * 반환값: int totalCount (검색 조건에 맞는 공지사항 개수)
	 * 설명: 제목에 검색어가 들어가는 공지사항 게시글의 개수를 조회한다.
	 */
	public int selectCount(String keyword) {
		System.out.println("NoticeBoardDAO -> selectCount 메소드 호출");
		
		int totalCount = 0;
		
		// 검색 조건에 맞는 공지사항 개수를 조회하는 쿼리문
		String query = "SELECT COUNT(*) total_count FROM notice_board"
						+ " WHERE NOT visibility='hidden' AND title LIKE '%' || ? || '%'";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 검색 조건에 맞는 공지사항 개수 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, keyword);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 검색 조건에 맞는 공지사항 개수 가져오기
			if(rs.next()) {
				totalCount = rs.getInt("total_count");
			}
			
		} catch(Exception e) {
			System.out.println("검색 조건에 맞는 공지사항 개수 조회 중 예외 발생");
			System.out.println("NoticeBoardDAO -> selectCount 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	/* 메소드명: selectList
	 * 파라미터: Map<String, Object> condition (조건이 저장된 맵)
	 * 반환값: List<NoticeBoardDTO> list (공지사항 목록)
	 * 설명: 제목에 검색어가 들어가고 특정 행에 해당되는 공지사항 게시글의 목록을 조회한다.
	 */
	public List<NoticeBoardDTO> selectList(Map<String, Object> condition) {
		System.out.println("NoticeBoardDAO -> selectList 메소드 호출");
		
		List<NoticeBoardDTO> list = new Vector<NoticeBoardDTO>();
		
		// 공지사항 목록을 조회하는 쿼리문
		String query = "SELECT * FROM"
						+ " (SELECT no, title, post_date, visit_count, ROWNUM r_num FROM"
						+ " (SELECT no, title, post_date, visit_count"
						+ " FROM notice_board"
						+ " WHERE NOT visibility='hidden' AND title LIKE '%' || ? || '%'"
						+ " ORDER BY no DESC)"
						+ " )"
						+ " WHERE r_num BETWEEN ? AND ?";
		//System.out.println("SQL 쿼리문: " + query);

		// 공지사항 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, condition.get("keyword").toString());
			psmt.setString(2, condition.get("start").toString());
			psmt.setString(3, condition.get("end").toString());

			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 공지사항 목록 가져오기
			while(rs.next()) {
				NoticeBoardDTO noticeBoardDto = new NoticeBoardDTO();
				
				noticeBoardDto.setNo(rs.getInt("no"));
				noticeBoardDto.setTitle(rs.getString("title"));
				//noticeBoardDto.setContent(rs.getString("content"));
				//noticeBoardDto.setMemberId(rs.getString("member_id"));
				noticeBoardDto.setPostDate(rs.getDate("post_date"));
				noticeBoardDto.setVisitCount(rs.getInt("visit_count"));
				//noticeBoardDto.setVisibility(rs.getString("visibility"));
				
				list.add(noticeBoardDto);
			}
			
		} catch(Exception e) {
			System.out.println("공지사항 목록 조회 중 예외 발생");
			System.out.println("NoticeBoardDAO -> selectList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	/* 메소드명: updateVisitCount
	 * 파라미터: int no (공지사항 번호)
	 * 반환값: int result (조회수 증가 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 고객이 조회한 공지사항 게시글의 조회수를 1 증가한다.
	 */
	public int updateVisitCount(int no) {
		System.out.println("NoticeBoardDAO -> updateVisitCount 메소드 호출");
		
		int result = 0;
		
		// 조회수 증가하는 쿼리문
		String query = "UPDATE notice_board SET visit_count = visit_count + 1"
						+ " WHERE no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 조회수 증가
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, no);
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("조회수 증가 중 예외 발생");
			System.out.println("NoticeBoardDAO -> updateVisitCount 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* 메소드명: select
	 * 파라미터: int no (공지사항 번호)
	 * 반환값: NoticeBoardDTO noticeBoardDto (공지사항 내용을 가지고 있는 NoticeBoardDTO 객체)
	 * 설명: 특정 공지사항 게시글의 내용을 조회한다.
	 */
	public NoticeBoardDTO select(int no) {
		System.out.println("NoticeBoardDAO -> select 메소드 호출");
		
		NoticeBoardDTO noticeBoardDto = null;
		
		// 공지사항 내용을 조회하는 쿼리문
		String query = "SELECT * FROM notice_board"
						+ " WHERE no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 공지사항 내용 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, no);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 공지사항 내용 가져오기
			if(rs.next()) {
				noticeBoardDto = new NoticeBoardDTO();
				
				noticeBoardDto.setNo(rs.getInt("no"));
				noticeBoardDto.setTitle(rs.getString("title"));
				noticeBoardDto.setContent(rs.getString("content"));
				noticeBoardDto.setMemberId(rs.getString("member_id"));
				noticeBoardDto.setPostDate(rs.getDate("post_date"));
				noticeBoardDto.setVisitCount(rs.getInt("visit_count"));
				noticeBoardDto.setVisibility(rs.getString("visibility"));
			}
			
		} catch(Exception e) {
			System.out.println("공지사항 내용 조회 중 예외 발생");
			System.out.println("NoticeBoardDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return noticeBoardDto;
	}
	
	/* 메소드명: selectPNInfo
	 * 파라미터: int no (공지사항 번호)
	 * 반환값: Map<String, Object> map (이전글, 다음글의 정보가 저장된 맵)
	 * 설명: 특정 공지사항 게시글의 이전글, 다음글의 정보를 조회한다.
	 */
	public Map<String, Object> selectPNInfo(int no) {
		System.out.println("NoticeBoardDAO -> selectPNInfo 메소드 호출");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 이전글, 다음글의 정보를 조회하는 쿼리문
		String query = "SELECT * FROM("
						+ " SELECT no,"
						+ " LEAD(no, 1) OVER (ORDER BY no DESC) AS pre_no,"
						+ " LEAD(title, 1, '해당글이 없습니다.') OVER (ORDER BY no DESC) AS pre_title,"
						+ " LAG(no, 1) OVER (ORDER BY no DESC) AS next_no,"
						+ " LAG(title, 1, '해당글이 없습니다.') OVER (ORDER BY no DESC) AS next_title"
						+ " FROM notice_board"
						+ " WHERE NOT visibility='hidden')"
						+ " WHERE no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 이전글, 다음글의 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, no);
		
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 이전글, 다음글의 정보 가져오기
			if(rs.next()) {
				map.put("preNo", rs.getString("pre_no"));
				map.put("preTitle", rs.getString("pre_title"));
				map.put("nextNo", rs.getString("next_no"));
				map.put("nextTitle", rs.getString("next_title"));
			}
			
		} catch(Exception e) {
			System.out.println("이전글, 다음글의 정보를 조회 중 예외 발생");
			System.out.println("NoticeBoardDAO -> selectPNInfo 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return map;
	}

}
