package utils;

import java.util.List;

import javax.servlet.ServletContext;

import dto.NoticeBoardDTO;

public class NoticeList {
	
	/* 메소드명: getListString
	 * 파라미터: List<NoticeBoardDTO> list (공지사항 목록이 저장된 리스트)
	 * 반환값: String str (공지사항 목록을 화면에 표시하기 위한 html)
	 */
	public static String getListString(List<NoticeBoardDTO> list) {
		String str = "";
		
		// 조회된 목록이 없는 경우
		if(list.size() == 0) {
			str += "<tr>";
        	str += "<td></td>";
        	str += "<td class='left'>데이터가 없습니다.</td>";
        	str += "<td></td>";
        	str += "<td></td>";
        	str += "</tr>";
        
        // 조회된 목록이 있는 경우
		} else {
			for(NoticeBoardDTO noticeBoardDto : list) {
	        	str += "<tr>";
	        	str += "<td>" + noticeBoardDto.getNo() + "</td>";
	        	str += "<td class='left'><a href='/notice/notice_view.do?no=" + noticeBoardDto.getNo() + "'>" + noticeBoardDto.getTitle() + "</a></td>";
	        	str += "<td>" + noticeBoardDto.getPostDate() + "</td>";
	        	str += "<td>" + noticeBoardDto.getVisitCount() + "</td>";
	        	str += "</tr>";
	        }
			
		}
        
		return str;
	}
	
	/* 메소드명: getPagingString
	 * 파라미터: ServletContext application (application 내장 객체), int totalCount (공지사항 개수), ing pageNum (페이지 번호)
	 * 반환값: String str (공지사항 목록의 페이지 이동 버튼 및 번호를 화면에 표시하기 위한 html)
	 */
	public static String getPagingString(ServletContext application, int totalCount, int pageNum) {
		String str = "";
		
		int postCountPerPage = Integer.parseInt(application.getInitParameter("PostCountPerPage"));		// 한 페이지에 출력할 게시물의 개수
		int pageCountPerBlock = Integer.parseInt(application.getInitParameter("PageCountPerBlock"));	// 한 블록(화면)에 출력할 게시물의 개수
		
		// 전체 페이지 수 계산
		int totalPage = (int) Math.ceil((double)totalCount / postCountPerPage);
		
		// 이전 블록 버튼
		if(pageNum > pageCountPerBlock) {
			str += "<li class='control'><a href='javascript:void(0)'><img alt='이전' src='/common/img/prev.jpg'></a></li>";
			
		}
		
		// 각 페이지 번호
		int pageTemp = ((pageNum - 1) / pageCountPerBlock) * pageCountPerBlock + 1;	// 시작 페이지 번호
		
		int i = 1;
		while(i <= pageCountPerBlock && pageTemp <= totalPage) {
			if(pageTemp == pageNum) {
				str += "<li class='active'><a href='javascript:void(0)'>" + pageTemp + "</a></li>";
			
			} else {
				str += "<li><a href='javascript:void(0)'>" + pageTemp + "</a></li>";
				
			}
			
			pageTemp++;
			i++;
		}
		
		// 다음 블록 버튼
		if(pageTemp <= totalPage) {
			str += "<li class='control'><a href='javascript:void(0)'><img alt='다음' src='/common/img/next.jpg'></a></li>";
		}
		
		return str;
	}
}
