package utils;

import java.util.List;

import dto.EventBoardDTO;

public class EventList {
	
	/* 메소드명: getListString
	 * 파라미터: List<EventBoardDTO> eventList (진행중인 이벤트 목록이 저장된 리스트), List<String> eventImageList (진행중인 이벤트 썸네일 목록)
	 * 반환값: String str (진행중인 이벤트 목록을 화면에 표시하기 위한 html)
	 */
	public static String getListString(List<EventBoardDTO> eventList, List<String> eventImageList) {
		String str = "";
		
		for(int i=0; i<eventList.size(); i++) {
			EventBoardDTO eventBoardDto = eventList.get(i);
			
			str += "<li>";
			str += "<dl>";
			str += "<dt>";
			str += "<a href='/event/event_view.do?no=" + eventBoardDto.getNo() + "'>";
			str += "<img src='/img/event/" + eventImageList.get(i) + "'>";
			str += "</a>";
			str += "</dt>";
			str += "<dd>";
			str += "<p class='title'>" + eventBoardDto.getTitle() + "</p>";
			str += "<p class='date'>" + eventBoardDto.getStartDate() + " ~ " + eventBoardDto.getEndDate() + "</p>";
			str += "</dd>";
			str += "</dl>";
			str += "</li>";
			
		}
		
		return str;
	}
	
	/* 메소드명: getEndListString
	 * 파라미터: List<EventBoardDTO> endEventlist (종료된 이벤트 목록이 저장된 리스트), List<String> endEventImageList (종료된 이벤트 썸네일 목록)
	 * 반환값: String str (종료된 이벤트 목록을 화면에 표시하기 위한 html)
	 */
	public static String getEndListString(List<EventBoardDTO> endEventlist, List<String> endEventImageList) {
		String str = "";
		
		for(int i=0; i<endEventlist.size(); i++) {
			EventBoardDTO eventBoardDto = endEventlist.get(i);
			
			str += "<li>";
			str += "<dl>";
			str += "<dt>";
			str += "<a href='/event/event_view.do?no=" + eventBoardDto.getNo() + "'>";
			str += "<span class='end'><i><img src='/img/event/icon_end_event.png' alt='이벤트 종료'></i></span>";
			str += "<img src='/img/event/" + endEventImageList.get(i) + "'>";
			str += "</a>";
			str += "</dt>";
			str += "<dd>";
			str += "<p class='title'>" + eventBoardDto.getTitle() + "</p>";
			str += "<p class='date'>" + eventBoardDto.getStartDate() + " ~ " + eventBoardDto.getEndDate() + "</p>";
			str += "</dd>";
			str += "</dl>";
			str += "</li>";
			
		}
		
		return str;
	}
}
