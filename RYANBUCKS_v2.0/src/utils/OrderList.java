package utils;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;

import dto.OrderTotalDTO;

public class OrderList {

	/* 메소드명: getListString
	 * 파라미터: List<OrderTotalDTO> orderList (주문 목록이 저장된 리스트), List<String> menuSummaryList (주문 건의 메뉴 요약정보 목록)
	 * 반환값: String str (주문 목록을 화면에 표시하기 위한 html)
	 */
	public static String getListString(List<OrderTotalDTO> orderList, List<String> menuSummaryList) {
		String str = "";
		
		// 주문 목록이 없는 경우
		if(orderList.size() == 0) {
			str += "<tr>";
			str += "<td colspan='6'>데이터가 없습니다.</td>";
        	str += "</tr>";
		
        // 주문 목록이 있는 경우
		} else {
			for(int i=0; i<orderList.size(); i++) {
				OrderTotalDTO orderTotalDto = orderList.get(i);
				
				str += "<tr>";
				
				// 주문번호
	        	str += "<td><a href='/my/order_view.do?order_id=" + orderTotalDto.getId() + "'>" + orderTotalDto.getId() +"</a></td>";
	        	
	        	// 주문일자
	        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        	String formattedDate = dateFormat.format(orderTotalDto.getOrderDate());
	        	str += "<td>" + formattedDate + "</td>";
	        	
	        	// 메뉴
	        	str += "<td class='left'><a href='/my/order_view.do?order_id=" + orderTotalDto.getId() + "'>" + menuSummaryList.get(i) + "</a></td>";
	        	
	        	// 유형
	        	str += "<td>";
	        	if(orderTotalDto.getType().equals("pickup")) {
	        		str += "픽업";
	        		
	        	} else if(orderTotalDto.getType().equals("shipping")) {
	        		str += "배송";
	        		
	        	}
	        	str += "</td>";
	        
	        	// 총 거래 금액
	        	str += "<td>" + String.format("%,d", orderTotalDto.getAmount()) + "원</td>";
	        	str += "</tr>";
	        }
		}
		
		return str;
	}
	
	
	/* 메소드명: getPagingString
	 * 파라미터: ServletContext application (application 내장 객체), int totalCount (주문 개수), ing pageNum (페이지 번호)
	 * 반환값: String str (주문 목록의 페이지 이동 버튼 및 번호를 화면에 표시하기 위한 html)
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
