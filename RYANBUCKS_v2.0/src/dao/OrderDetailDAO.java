package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.OrderDetailDTO;
import dto.OrderTotalDTO;

public class OrderDetailDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public OrderDetailDAO(ServletContext application) {
		super(application);
	}
	
	// Connection 객체를 매개변수로 받는 생성자
	public OrderDetailDAO(Connection con) {
		super(con);
	}
	
	/* 메소드명: selectMenuSummary
	 * 파라미터: List<OrderTotalDTO> orderList (주문 목록 리스트)
	 * 반환값: List<String> (메뉴 요약정보 목록, 메뉴 요약정보: 주문한 메뉴가 1개이면 '메뉴명', N개면 '메뉴명 외 N-1건')
	 * 설명: 주문 내역에서 보여질 메뉴 요약 정보 목록을 조회한다.
	 */
	public List<String> selectMenuSummary(List<OrderTotalDTO> orderList) {
		System.out.println("OrderDetailDAO -> selectMenuSummary 메소드 호출");
		
		List<String> menuSummaryList = new ArrayList<String>();
		
		// 메뉴 요약정보를 조회하는 쿼리문
		String query = "SELECT"
				+ " CASE WHEN COUNT(*)=1 THEN MAX(menu.name_kor)"
				+ " ELSE MIN(menu.name_kor) || ' 외 ' || (COUNT(*)-1) || '건'"
				+ " END AS menu_summary"
				+ " FROM order_detail"
				+ " LEFT JOIN menu ON order_detail.menu_no=menu.no"
				+ " WHERE order_id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 메뉴 요약정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(OrderTotalDTO orderTotalDto : orderList) {
				// 칼럼 값 채우기
				psmt.setString(1, orderTotalDto.getId());
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
				
				// 메뉴 요약정보 가져오기
				if(rs.next()) {
					menuSummaryList.add(rs.getString("menu_summary"));
				}
			}
			
			
		} catch(Exception e) {
			System.out.println("메뉴 요약정보 조회 중 예외 발생");
			System.out.println("OrderDetailDAO -> selectMenuSummary 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return menuSummaryList;
	}
	
	/* 메소드명: selctList
	 * 파라미터: String orderId (주문 번호)
	 * 반환값: List<OrderDetailDTO> list (상세 주문 정보 목록)
	 * 설명: 특정 주문 건의 상세 주문 정보 목록을 조회한다.
	 */
	public List<OrderDetailDTO> selectList(String orderId) {
		System.out.println("OrderDetaillDAO -> selectList(String) 메소드 호출");
		
		List<OrderDetailDTO> list = new Vector<OrderDetailDTO>();
		
		// 상세 주문 정보 목록을 조회하는 쿼리문
		String query = "SELECT * FROM order_detail"
						+ " WHERE order_id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 상세 주문 정보 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, orderId);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 상세 주문 정보 목록 가져오기
			while(rs.next()) {
				OrderDetailDTO orderDetailDto = new OrderDetailDTO();
				
				orderDetailDto.setNo(rs.getInt("no"));
				orderDetailDto.setOrderId(rs.getString("order_id"));
				orderDetailDto.setMenuNo(rs.getInt("menu_no"));
				orderDetailDto.setMenuPrice(rs.getInt("menu_price"));
				orderDetailDto.setOptionNo(rs.getInt("option_no"));
				orderDetailDto.setOptionPrice(rs.getInt("option_price"));
				orderDetailDto.setQuantity(rs.getInt("quantity"));
				
				list.add(orderDetailDto);
			}
			
		} catch(Exception e) {
			System.out.println("상세 주문 정보 목록 조회 중 예외 발생");
			System.out.println("OrderDetaillDAO -> selectList(String) 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	/* 메소드명: selectList
	 * 파라미터: List<Integer> cartNoList (장바구니 관리번호 목록)
	 * 반환값: List<OrderDetailDTO> list (상세 주문 정보 목록)
	 * 설명: 장바구니에 담긴 상품 번호 목록을 이용해 상세 주문 정보 목록을 조회한다.
	 */
	public List<OrderDetailDTO> selectList(List<Integer> cartNoList) {
		System.out.println("OrderDetaillDAO -> selectList(List) 메소드 호출");
		
		List<OrderDetailDTO> list = new Vector<OrderDetailDTO>();
		
		// 상세 주문 정보 목록을 생성하는 쿼리문
		String query = "SELECT c.menu_no, m.sales_price menu_price, c.option_no, mo.price option_price, c.quantity"
						+ " FROM cart c JOIN menu m ON c.menu_no=m.no"
						+ " LEFT JOIN menu_option mo ON c.option_no=mo.no"
						+ " WHERE c.no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 상세 주문 정보 생성
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(int no : cartNoList) {
				// 칼럼 값 채우기
				psmt.setInt(1, no);
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
				
				// 상세 주문 정보 생성하기
				while(rs.next()) {
					OrderDetailDTO orderDetailDto = new OrderDetailDTO();
					
					orderDetailDto.setMenuNo(rs.getInt("menu_no"));
					orderDetailDto.setMenuPrice(rs.getInt("menu_price"));
					orderDetailDto.setOptionNo(rs.getInt("option_no"));
					orderDetailDto.setOptionPrice(rs.getInt("option_price"));
					orderDetailDto.setQuantity(rs.getInt("quantity"));
					
					list.add(orderDetailDto);
				}
			}
			
		} catch(Exception e) {
			System.out.println("상세 주문 정보 목록 생성 중 예외 발생");
			System.out.println("OrderDetaillDAO -> selectList(List) 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	/* 메소드명: select
	 * 파라미터: int menuNo (메뉴 번호), int optionNo (옵션 번호), int quantity (수량)
	 * 반환값: OrderDetailDTO orderDetailDto (상세 주문 정보를 가지는 객체)
	 * 설명: 메뉴 번호, 옵션 번호, 수량으로 상세 주문 정보를 조회한다.
	 */
 	public OrderDetailDTO select(int menuNo, int optionNo, int quantity) {
		System.out.println("OrderDetaillDAO -> select 메소드 호출");
		
		OrderDetailDTO orderDetailDto = null;
		
		// 상세 주문 정보를 생성하는 쿼리문
		String query = "SELECT m.no menu_no, sales_price menu_price,"
						+ " NVL((SELECT price FROM menu_option WHERE no=?), 0) AS option_price"
						+ " FROM menu m"
						+ " WHERE m.no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 상세 주문 정보 생성
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, optionNo);
			psmt.setInt(2, menuNo);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 상세 주문 정보 생성하기
			if(rs.next()) {
				orderDetailDto = new OrderDetailDTO();
				
				orderDetailDto.setMenuNo(rs.getInt("menu_no"));
				orderDetailDto.setMenuPrice(rs.getInt("menu_price"));
				orderDetailDto.setOptionNo(optionNo);
				orderDetailDto.setOptionPrice(rs.getInt("option_price"));
				orderDetailDto.setQuantity(quantity);
			}
			
		} catch(Exception e) {
			System.out.println("상세 주문 정보 생성 중 예외 발생");
			System.out.println("OrderDetaillDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return orderDetailDto;
	}

 	/* 메소드명: insertList
 	 * 파라미터: List<OrderDetailDTO> orderDetailList (상세 주문 정보를 가지고 있는 리스트)
 	 * 반환값: int result (상세 주문 정보 추가 성공 여부 -> 1: 성공, 0: 실패)
 	 * 설명: 상세 주문 정보 목록을 저장한다.
 	 */
 	public int insertList(List<OrderDetailDTO> orderDetailList) {
 		System.out.println("OrderDetailDAO -> insertList 메소드 호출");
 		
 		int result = 0;
 		
 		// 상세 주문 정보 추가하는 쿼리문
 		String query = "INSERT INTO order_detail"
 						+ "(no, order_id, menu_no, menu_price, option_no, option_price, quantity)"
						+ " VALUES(seq_order_detail_no.NEXTVAL, ?, ?, ?, ?, ?, ?)";
 		//System.out.println("SQL 쿼리문: " + query);
 		
 		// 상세 주문 정보 추가
 		try {
 			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
 			
			for(OrderDetailDTO orderDetailDto : orderDetailList) {
				// 칼럼 값 채우기
				psmt.setString(1, orderDetailDto.getOrderId());
				psmt.setInt(2, orderDetailDto.getMenuNo());
				psmt.setInt(3, orderDetailDto.getMenuPrice());
				
				if(orderDetailDto.getOptionNo() != 0) {
					psmt.setInt(4, orderDetailDto.getOptionNo());
				} else {
					psmt.setNull(4, java.sql.Types.INTEGER);
				}
			
				psmt.setInt(5, orderDetailDto.getOptionPrice());
				psmt.setInt(6, orderDetailDto.getQuantity());
				
				// 동적 쿼리문 실행
				result = psmt.executeUpdate();
				
				if(result == 0) {	// 한번이라도 추가 실패 시 중지
					System.out.println("메뉴 상세 정보 업데이트 실패");
					return 0;
				}
			}
 			
 		} catch(Exception e) {
			System.out.println("상세 주문 정보 추가 중 예외 발생");
			System.out.println("OrderDetailDAO -> insertList 메소드 확인 바람");
			e.printStackTrace();
		}
 		
 		return result;
 	}

 	/* 메소드명: updateReviewPostNo
 	 * 파라미터: int no (상세 주문 정보 관리 번호)
 	 * 반환값: int result (리뷰 포스트 번호 업데이트 성공 여부 -> 1: 성공, 0: 실패)
 	 * 설명: 특정 상세 주문 건의 리뷰 게시글 번호를 수정한다.
 	 */
 	public int updateReviewPostNo(int no, int reviewPostNo) {
 		System.out.println("OrderDetailDAO -> updateReviewPostNo 메소드 호출");
 		
 		int result = 0;
 		
 		// 리뷰 포스트 번호 업데이트하는 쿼리문
 		String query = "UPDATE order_detail"
 						+ " SET review_post_no=? WHERE no=?";
 		//System.out.println("SQL 쿼리문: " + query);
 		
 		// 리뷰 포스트 번호 업데이트
 		try {
 			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, reviewPostNo);
			psmt.setInt(2, no);
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
 		} catch(Exception e) {
			System.out.println("리뷰 포스트 번호 업데이트 중 예외 발생");
			System.out.println("OrderDetailDAO -> updateReviewPostNo 메소드 확인 바람");
			e.printStackTrace();
		}
 		
 		return result;
 	}
}
