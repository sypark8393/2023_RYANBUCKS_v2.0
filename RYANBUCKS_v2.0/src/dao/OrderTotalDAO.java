package dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.OrderTotalDTO;

public class OrderTotalDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public OrderTotalDAO(ServletContext application) {
		super(application);
	}
	
	// Connection 객체를 매개변수로 받는 생성자
	public OrderTotalDAO(Connection con) {
		super(con);
	}
	
	/* 메소드명: selectCount
	 * 파라미터: Map<String, Object> condition (검색 조건이 저장된 맵)
	 * 반환값: int totalCount (검색 조건에 맞는 주문 개수)
	 * 설명: 기간, 주문유형(전체, 픽업, 배송), 결제수단(전체, 카드)이 일치하는 주문 내역 목록의 개수를 조회한다.
	 */
	public int selectCount(Map<String, Object> condition) {
		System.out.println("OrderTotalDAO -> selectCount 메소드 호출");
		
		int totalCount = 0;
		
		// 검색 조건에 맞는 주문 개수를 조회하는 쿼리문
		String query = "SELECT COUNT(*) FROM order_total"
						+ " WHERE member_id=?";
		
		// 기간
		if(!condition.get("period").equals("before 6")) { //
			query += " AND TRUNC(order_date)>TRUNC(SYSDATE) - INTERVAL '" + condition.get("period") + "' MONTH";
		
		} else { // 6개월 이전
			query += " AND TRUNC(order_date)<=TRUNC(SYSDATE) - INTERVAL '6' MONTH";
			
		}
		
		// 주문유형 조건이 있는 경우
		if(!condition.get("type").equals("whole")) {
			query += " AND type='" + condition.get("type") + "'";
		}
		
		// 결제수단 조건이 있는 경우
		if(!condition.get("pay_method").equals("whole")) {
			query += " AND pay_method='" + condition.get("pay_method") + "'";
		}
		
		//System.out.println("SQL 쿼리문: " + query);
		
		// 검색 조건에 맞는 주문 개수 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, condition.get("member_id").toString());
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 검색 조건에 맞는 주문 개수 가져오기
			if(rs.next()) {
				totalCount = rs.getInt("COUNT(*)");
			}
			
		} catch(Exception e) {
			System.out.println("검색 조건에 맞는 주문 개수 조회 중 예외 발생");
			System.out.println("OrderTotalDAO -> selectCount 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	/* 메소드명: selectList
	 * 파라미터: Map<String, Object> condition (검색 조건이 저장된 맵)
	 * 반환값: List<OrderTotalDTO> list (주문 목록)
	 * 설명: 기간, 주문유형(전체, 픽업, 배송), 결제수단(전체, 카드)이 일치하고 특정 행에 해당되는 주문 내역 목록을 조회한다.
	 */
	public List<OrderTotalDTO> selectList(Map<String, Object> condition) {
		System.out.println("OrderTotalDAO -> selectList 메소드 호출");
		
		List<OrderTotalDTO> list = new Vector<OrderTotalDTO>();
	
		// 주문 목록을 조회하는 쿼리문
		String query = "SELECT * FROM"
						+ " (SELECT id, order_date, type, amount, ROWNUM r_num FROM"
						+ " (SELECT id, order_date, type, amount"
						+ " FROM order_total"
						+ " WHERE member_id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 기간
		if(!condition.get("period").equals("before 6")) { //
			query += " AND TRUNC(order_date)>=TRUNC(SYSDATE) - INTERVAL '" + condition.get("period") + "' MONTH";
		
		} else { // 6개월 이전
			query += " AND TRUNC(order_date)<TRUNC(SYSDATE) - INTERVAL '6' MONTH";
			
		}
		
		// 주문유형 조건이 있는 경우
		if(!condition.get("type").equals("whole")) {
			query += " AND type='" + condition.get("type") + "'";
		}
		
		// 결제수단 조건이 있는 경우
		if(!condition.get("pay_method").equals("whole")) {
			query += " AND pay_method='" + condition.get("pay_method") + "'";
		}
		
		query += " ORDER BY order_date DESC)"
				+ " )"
				+ " WHERE r_num BETWEEN ? AND ?";
		
		//System.out.println("SQL 쿼리문: " + query);
		
		// 주문 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, condition.get("member_id").toString());
			psmt.setString(2, condition.get("start").toString());
			psmt.setString(3, condition.get("end").toString());
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 주문 목록 가져오기
			while(rs.next()) {
				OrderTotalDTO orderTotalDto = new OrderTotalDTO();
				
				orderTotalDto.setId(rs.getString("id"));
				orderTotalDto.setOrderDate(rs.getTimestamp("order_date"));
				//orderTotalDto.setMemberId(rs.getString("member_id"));
				//orderTotalDto.setRecipientName(rs.getString("recipient_name"));
				//orderTotalDto.setRecipientTel(rs.getString("recipient_tel"));
				//orderTotalDto.setRecipientEmail(rs.getString("recipient_email"));
				orderTotalDto.setType(rs.getString("type"));
				//orderTotalDto.setPostCode(rs.getString("post_code"));
				//orderTotalDto.setRoadAddress(rs.getString("road_address"));
				//orderTotalDto.setDetailAddress(rs.getString("detail_address"));
				//orderTotalDto.setPickupBranch(rs.getString("pickup_branch"));
				//orderTotalDto.setPickupTime(rs.getTimestamp("pickup_time"));
				orderTotalDto.setAmount(rs.getInt("amount"));
				//orderTotalDto.setPayMethod(rs.getString("pay_method"));
				//orderTotalDto.setCardName(rs.getString("card_name"));
				//orderTotalDto.setCardNo(rs.getString("card_no"));
				//orderTotalDto.setCardQuota(rs.getString("card_quota"));
				//orderTotalDto.setAuthCode(rs.getString("auth_code"));
				
				list.add(orderTotalDto);
			}
			
		} catch(Exception e) {
			System.out.println("주문 목록 조회 중 예외 발생");
			System.out.println("OrderTotalDAO -> selectList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	/* 메소드명: select
	 * 파라미터: String id (주문 번호), String memberId (회원 id)
	 * 반환값: OrderTotalDTO orderTotalDto (통합 주문 정보를 가지고 있는 OrderTotalDTO 객체)
	 * 설명: 특정 주문 건의 통합 주문 정보를 조회한다.
	 */
	public OrderTotalDTO select(String id, String memberId) {
		System.out.println("OrderTotalDAO -> select 메소드 호출");
		
		OrderTotalDTO orderTotalDto = null;
		
		// 통합 주문 정보를 조회하는 쿼리문
		String query = "SELECT * FROM order_total"
						+ " WHERE id=? AND member_id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 통합 주문 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
		
			// 칼럼 값 채우기
			psmt.setString(1, id);
			psmt.setString(2, memberId);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 통합 주문 정보 가져오기
			if(rs.next()) {
				orderTotalDto = new OrderTotalDTO();
				
				orderTotalDto.setId(rs.getString("id"));
				orderTotalDto.setOrderDate(rs.getTimestamp("order_date"));
				orderTotalDto.setMemberId(rs.getString("member_id"));
				orderTotalDto.setRecipientName(rs.getString("recipient_name"));
				orderTotalDto.setRecipientTel(rs.getString("recipient_tel"));
				orderTotalDto.setRecipientEmail(rs.getString("recipient_email"));
				orderTotalDto.setType(rs.getString("type"));
				orderTotalDto.setPostCode(rs.getString("post_code"));
				orderTotalDto.setRoadAddress(rs.getString("road_address"));
				orderTotalDto.setDetailAddress(rs.getString("detail_address"));
				orderTotalDto.setPickupBranch(rs.getString("pickup_branch"));
				orderTotalDto.setPickupTime(rs.getTimestamp("pickup_time"));
				orderTotalDto.setAmount(rs.getInt("amount"));
				orderTotalDto.setPayMethod(rs.getString("pay_method"));
				orderTotalDto.setCardName(rs.getString("card_name"));
				orderTotalDto.setCardNo(rs.getString("card_no"));
				orderTotalDto.setCardQuota(rs.getString("card_quota"));
				orderTotalDto.setAuthCode(rs.getString("auth_code"));
			}
			
		} catch(Exception e) {
			System.out.println("통합 주문 정보 조회 중 예외 발생");
			System.out.println("OrderTotalDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return orderTotalDto;
	}

	/* 메소드명: insert
	 * 파라미터: OrderTotalDTO orderTotalDto (통합 주문 정보를 가지고 있는 객체)
	 * 반환값: int result (통합 주문 정보 추가 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 통합 주문 정보를 저장한다.
	 */
	public int insert(OrderTotalDTO orderTotalDto) {
		System.out.println("OrderTotalDAO -> insert 메소드 호출");
		
		int result = 0;
		
		// 통합 주문 정보 추가하는 쿼리문
		String query = "INSERT INTO order_total"
						+ "(id, member_id, recipient_name, recipient_tel, recipient_email, type, post_code, road_address, detail_address, pickup_branch, pickup_time, amount, pay_method, card_name, card_no, card_quota, auth_code)"
						+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 통합 주문 정보 추가
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, orderTotalDto.getId());
			psmt.setString(2, orderTotalDto.getMemberId());
			psmt.setString(3, orderTotalDto.getRecipientName());
			psmt.setString(4, orderTotalDto.getRecipientTel());
			psmt.setString(5, orderTotalDto.getRecipientEmail());
			psmt.setString(6, orderTotalDto.getType());
			psmt.setString(7, orderTotalDto.getPostCode());
			psmt.setString(8, orderTotalDto.getRoadAddress());
			psmt.setString(9, orderTotalDto.getDetailAddress());
			psmt.setString(10, orderTotalDto.getPickupBranch());
			psmt.setTimestamp(11, orderTotalDto.getPickupTime());
			psmt.setInt(12, orderTotalDto.getAmount());
			psmt.setString(13, orderTotalDto.getPayMethod());
			psmt.setString(14, orderTotalDto.getCardName());
			psmt.setString(15, orderTotalDto.getCardNo());
			psmt.setString(16, orderTotalDto.getCardQuota());
			psmt.setString(17, orderTotalDto.getAuthCode());
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("통합 주문 정보 추가 중 예외 발생");
			System.out.println("OrderTotalDAO -> insert 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}


}
