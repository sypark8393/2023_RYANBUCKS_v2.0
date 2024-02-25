package dao;

import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.EventBoardDTO;

public class EventBoardDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public EventBoardDAO(ServletContext application) {
		super(application);
	}
	
	/* 메소드명: selectIngList
	 * 파라미터: 없음
	 * 반환값: List<EventBoardDTO> list (진행중인 이벤트 목록)
	 * 설명: 진행중인 이벤트 목록을 조회한다.
	 */
	public List<EventBoardDTO> selectIngList() {
		System.out.println("EventBoardDAO -> selectIngList 메소드 호출");
		
		List<EventBoardDTO> list = new Vector<EventBoardDTO>();
		
		// 진행중인 이벤트 목록을 조회하는 쿼리문
		String query = "SELECT * FROM event_board"
						+ " WHERE NOT visibility='hidden' AND end_date >= SYSDATE"
						+ " ORDER BY start_date DESC";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 진행중인 이벤트 목록 조회
		try {
			// 정적 쿼리문 생성
			stmt = con.createStatement();
			
			// 정적 쿼리문 실행
			rs = stmt.executeQuery(query);
			
			// 진행중인 이벤트 목록 가져오기
			while(rs.next()) {
				EventBoardDTO eventBoardDto = new EventBoardDTO();
				
				eventBoardDto.setNo(rs.getInt("no"));
				eventBoardDto.setTitle(rs.getString("title"));
				eventBoardDto.setMemberId("member_id");
				eventBoardDto.setPostDate(rs.getDate("post_date"));
				eventBoardDto.setVisitCount(rs.getInt("visit_count"));
				eventBoardDto.setVisibility(rs.getString("visibility"));
				eventBoardDto.setStartDate(rs.getDate("start_date"));
				eventBoardDto.setEndDate(rs.getDate("end_date"));
				
				list.add(eventBoardDto);
			}
			
		} catch(Exception e) {
			System.out.println("진행중인 이벤트 목록 조회 중 예외 발생");
			System.out.println("EventBoardDAO -> selectIngList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	/* 메소드명: selectEndList
	 * 파라미터: 없음
	 * 반환값: List<EventBoardDTO> list (종료된 이벤트 목록)
	 * 설명: 종료된 이벤트 목록을 조회한다.
	 */
	public List<EventBoardDTO> selectEndList() {
		System.out.println("EventBoardDAO -> selectEndList 메소드 호출");
		
		List<EventBoardDTO> list = new Vector<EventBoardDTO>();
		
		// 종료된 이벤트 목록을 조회하는 쿼리문
		String query = "SELECT * FROM event_board"
						+ " WHERE NOT visibility='hidden' AND end_date < SYSDATE"
						+ " ORDER BY start_date DESC";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 종료된 이벤트 목록 조회
		try {
			// 정적 쿼리문 생성
			stmt = con.createStatement();
			
			// 정적 쿼리문 실행
			rs = stmt.executeQuery(query);
			
			// 종료된 이벤트 목록 가져오기
			while(rs.next()) {
				EventBoardDTO eventBoardDto = new EventBoardDTO();
				
				eventBoardDto.setNo(rs.getInt("no"));
				eventBoardDto.setTitle(rs.getString("title"));
				eventBoardDto.setMemberId("member_id");
				eventBoardDto.setPostDate(rs.getDate("post_date"));
				eventBoardDto.setVisitCount(rs.getInt("visit_count"));
				eventBoardDto.setVisibility(rs.getString("visibility"));
				eventBoardDto.setStartDate(rs.getDate("start_date"));
				eventBoardDto.setEndDate(rs.getDate("end_date"));
				
				list.add(eventBoardDto);
			}
			
		} catch(Exception e) {
			System.out.println("종료된 이벤트 목록 조회 중 예외 발생");
			System.out.println("EventBoardDAO -> selectEndList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}

	/* 메소드명: updateVisitCount
	 * 파라미터: int no (공지사항 번호)
	 * 반환값: int result (조회수 증가 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 고객이 조회한 이벤트 게시글의 조회수를 1 증가한다.
	 */
	public int updateVisitCount(int no) {
		System.out.println("EventBoardDAO -> updateVisitCount 메소드 호출");
		
		int result = 0;
		
		// 조회수 증가하는 쿼리문
		String query = "UPDATE event_board SET visit_count = visit_count + 1"
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
			System.out.println("EventBoardDAO -> updateVisitCount 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}

	/* 메소드명: select
	 * 파라미터: int no (이벤트 게시글 번호)
	 * 반환값: EventBoardDTO eventBoardDto (이벤트 게시글 내용을 가지고 있는 EventBoardDTO 객체)
	 * 설명: 특정 이벤트 게시글의 정보를 조회한다.
	 */
	public EventBoardDTO select(int no) {
		System.out.println("EventBoardDAO -> select 메소드 호출");
		
		EventBoardDTO eventBoardDto = null;
		
		// 이벤트 게시글 내용을 조회하는 쿼리문
		String query = "SELECT * FROM event_board"
						+ " WHERE no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 이벤트 게시글 내용 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, no);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 이벤트 게시글 내용 가져오기
			if(rs.next()) {
				eventBoardDto = new EventBoardDTO();
				
				eventBoardDto.setNo(rs.getInt("no"));
				eventBoardDto.setTitle(rs.getString("title"));
				eventBoardDto.setMemberId("member_id");
				eventBoardDto.setPostDate(rs.getDate("post_date"));
				eventBoardDto.setVisitCount(rs.getInt("visit_count"));
				eventBoardDto.setVisibility(rs.getString("visibility"));
				eventBoardDto.setStartDate(rs.getDate("start_date"));
				eventBoardDto.setEndDate(rs.getDate("end_date"));
			}
			
		} catch(Exception e) {
			System.out.println("이벤트 게시글 조회 중 예외 발생");
			System.out.println("EventBoardDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return eventBoardDto;
	}
	
}
