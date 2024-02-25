package utils;

import java.util.List;import javax.servlet.ServletContext;

import dto.ReviewByMenuDTO;

public class ReviewList {

	/* 메소드명: getListString
	 * 파라미터: List<ReviewByMenuDTO> reviewList (리뷰 목록이 저장된 리스트)
	 * 반환값: String str (리뷰 목록을 화면에 표시하기 위한 html)
	 */
	public static String getListString(List<ReviewByMenuDTO> reviewList) {
		String str = "";
		
		for(ReviewByMenuDTO reviewByMenuDto : reviewList) {
			str += "<tr>";
			
			str += "<td class='star'>";
			str += "<span>";
			str += "<span style='width:" + reviewByMenuDto.getRate() * 20 + "%;'></span>";
			str += "</span>";
			str += "</td>";
			
			str += "<td class='left'>";
			str += "<span class='en'>" + maskLastThreeCharacters(reviewByMenuDto.getMemberId()) + "</span> | " + reviewByMenuDto.getPostDate();
			if(reviewByMenuDto.getOptionInfo() != null) {
				str += "<br>" + reviewByMenuDto.getOptionInfo();
			}
			str += "<p>";
			if(reviewByMenuDto.getVisibility().equals("hidden")) { // 숨김 상태인 경우
				str += "<img src='/common/img/icon_alert.png'>";
				str += "<strong>해당 리뷰는 관리자 권한에 의해 숨김 처리 되었습니다.</strong>";
			
			} else {
				str += reviewByMenuDto.getContent();
				
			}
			str += "</p>";
			str += "</td>";
			
			str += "</tr>";
		}
		
		return str;
	}
	
	/* 메소드명: maskLastThreeCharacters
	 * 파라미터: String userId (사용자 id)
	 * 반환값: String maskedString (마지막 3문자가 마스킹 처리된 사용자 id)
	 */
	private static String maskLastThreeCharacters(String userId) {
		String prefix = userId.substring(0, userId.length() - 3);
		String maskedString = prefix + "***";
		
        return maskedString;
	}

	/* 메소드명: getPagingString
	 * 파라미터: ServletContext application (application 내장 객체), int totalCount (리뷰 개수), ing pageNum (페이지 번호)
	 * 반환값: String str (리뷰 목록의 페이지 이동 버튼 및 번호를 화면에 표시하기 위한 html)
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
