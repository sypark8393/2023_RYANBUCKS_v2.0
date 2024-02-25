package dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.EventBoardDTO;

public class EventImageDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public EventImageDAO(ServletContext application) {
		super(application);
	}
	
	/* 메소드명: selectThumFileNameList
	 * 파라미터: List<EventBoardDTO> eventList (이벤트 목록 리스트)
	 * 반환값: List<String> (썸네일 파일명 목록)
	 * 설명: 이벤트 목록을 이용해 각 이벤트의 썸네일 파일명 목록을 조회한다.
	 */
	public List<String> selectThumFileNameList(List<EventBoardDTO> eventList) {
		System.out.println("EventImageDAO -> selectThumFileNameList 메소드 호출");
		
		List<String> eventImageList = new ArrayList<String>();
		
		// 썸네일 파일명을 조회하는 쿼리문
		String query = "SELECT file_name FROM event_image"
						+ " WHERE event_post_no=? AND type='thumnail'";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 썸네일 파일명 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(EventBoardDTO eventBoardDto : eventList) {
				// 칼럼 값 채우기
				psmt.setInt(1, eventBoardDto.getNo());
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
					
				// 썸네일 파일명 가져오기
				if(rs.next()) {
					eventImageList.add(rs.getString("file_name"));
				}
			}
		
		} catch(Exception e) {
			System.out.println("썸네일 파일명 조회 중 예외 발생");
			System.out.println("EventImageDAO -> selectThumFileNameList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return eventImageList;
	}
	
	/* 메소드명: selectContentFileNameList
	 * 파라미터: int eventPostNo (이벤트 게시글 번호)
	 * 파라미터: List<String> list (내용 이미지 파일명 목록)
	 * 설명: 특정 이벤트 게시글의 본문에 들어갈 이미지의 파일명 목록을 조회한다.
	 */
	public List<String> selectContentFileNameList(int eventPostNo) {
		System.out.println("EventImageDAO -> selectContentFileNameList 메소드 호출");
		
		List<String> list = new ArrayList<String>();
		
		// 내용 파일명을 조회하는 쿼리문
		String query = "SELECT file_name FROM event_image"
						+ " WHERE event_post_no=? AND type='content'"
						+ " ORDER BY file_name";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 내용 파일명 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, eventPostNo);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
				
			// 내용 파일명 가져오기
			while(rs.next()) {
				list.add(rs.getString("file_name"));
			}
			
		} catch(Exception e) {
			System.out.println("내용 파일명 조회 중 예외 발생");
			System.out.println("EventImageDAO -> selectContentFileNameList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
}
