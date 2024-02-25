package dao;

import java.sql.Connection;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.CartDTO;

public class CartDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public CartDAO(ServletContext application) {
		super(application);
	}
	
	// Connection 객체를 매개변수로 받는 생성자
	public CartDAO(Connection con) {
		super(con);
	}
	
	/* 메소드명: insert
	 * 파라미터: CartDTO cartDto (장바구니에 담을 메뉴 정보를 가지고 있는 CartDTO 객체)
	 * 반환값: int result (장바구니에 메뉴 담기 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 메뉴, 옵션, 수량 정보를 이용해 회원의 장바구니에 상품 정보를 저장한다.
	 */
	public int insert(CartDTO cartDto) {
		System.out.println("CartDAO -> insert 메소드 호출");
		
		int result = 0;
		
		// 장바구니에 메뉴 담는 쿼리문
		String query = "INSERT INTO cart"
						+ " VALUES(seq_cart_no.NEXTVAL, ?, ?, ?, ?)";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 장바구니에 메뉴 담기
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, cartDto.getMemberId());
			psmt.setInt(2, cartDto.getMenuNo());
			
			// 옵션이 있는 메뉴면 옵션 번호로 세팅하고, 옵션이 없는 메뉴면 null로 설정
			if(cartDto.getOptionNo() != 0) {
				psmt.setInt(3, cartDto.getOptionNo());
				
			} else {
				psmt.setNull(3, java.sql.Types.INTEGER);
			}
			
			psmt.setInt(4, cartDto.getQuantity());
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("장바구니에 메뉴 담는 중 예외 발생");
			System.out.println("CartDAO -> insert 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* 메소드명: select
	 * 파라미터: String memberId (회원 id), int no (장바구니 관리 번호) 
	 * 반환값: CartDTO cartDto (장바구니에 담긴 메뉴의 정보를 가지고 있는 CartDTO 객체)
	 * 설명: 회원의 장바구니에 저장된 상품 중 특정 상품의 정보를 조회한다.
	 */
	public CartDTO select(String memberId, int no) {
		System.out.println("CartDAO -> select 메소드 호출");
		
		CartDTO cartDto = null;
		
		// 장바구니에 담긴 메뉴 정보를 가져오는 쿼리문
		String query = "SELECT * FROM cart"
						+ " WHERE member_id=? AND no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 장바구니에 담긴 메뉴 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, memberId);
			psmt.setInt(2, no);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
						
			// 장바구니에 담긴 메뉴 정보 가져오기
			if(rs.next()) {
				cartDto = new CartDTO();
				
				cartDto.setNo(rs.getInt("no"));
				cartDto.setMemberId(rs.getString("member_id"));
				cartDto.setMenuNo(rs.getInt("menu_no"));
				cartDto.setOptionNo(rs.getInt("option_no"));
				cartDto.setQuantity(rs.getInt("quantity"));
			}
			
		} catch(Exception e) {
			System.out.println("장바구니에 담긴 메뉴 정보 조회 중 예외 발생");
			System.out.println("CartDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return cartDto;
	}
	
	/* 메소드명: selectList
	 * 파라미터: String memberId (회원 id)
	 * 반환값: List<CartDTO> list (장바구니에 담긴 메뉴 목록)
	 * 설명: 회원의 장바구니에 담긴 상품 목록을 조회한다.
	 */
	public List<CartDTO> selectList(String memberId) {
		System.out.println("CartDAO -> selectList(String) 메소드 호출");
		
		List<CartDTO> list = new Vector<CartDTO>();
		
		// 장바구니에 담긴 메뉴 목록 가져오는 쿼리문
		// 활성화 상태의 카테고리, 활성화 상태의 메뉴, 활성화 상태의 옵션이거나 옵션이 없는 케이스
		String query = "SELECT c.*"
						+ " FROM cart c"
						+ " JOIN menu m ON c.menu_no=m.no"
						+ " JOIN category ct ON m.category_id=ct.id"
						+ " LEFT JOIN menu_option mo ON c.option_no=mo.no"
						+ " WHERE member_id=?"
						+ " AND NOT m.visibility='hidden'"
						+ " AND NOT ct.visibility='hidden'"
						+ " AND (NOT mo.visibility='hidden' OR mo.visibility IS NULL)"
						+ " ORDER BY c.no";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 장바구니에 담긴 메뉴 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, memberId);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 장바구니에 담긴 메뉴 목록 가져오기
			while(rs.next()) {
				CartDTO cartDto = new CartDTO();
				
				cartDto.setNo(rs.getInt("no"));
				cartDto.setMemberId(rs.getString("member_id"));
				cartDto.setMenuNo(rs.getInt("menu_no"));
				cartDto.setOptionNo(rs.getInt("option_no"));
				cartDto.setQuantity(rs.getInt("quantity"));
				
				list.add(cartDto);
			}
			
		} catch(Exception e) {
			System.out.println("장바구니에 담긴 메뉴 목록 조회 중 예외 발생");
			System.out.println("CartDAO -> selectList(String) 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	/* 메소드명: deleteList
	 * 파라미터: List<Integer> noList (장바구니에서 삭제할 아이템 번호 목록), String memberId (회원 id)
	 * 반환값: int result (장바구니에서 메뉴 삭제 성공 여부 -> 1: 성공, 0: 실패
	 * 설명: 회원의 장바구니에 담긴 상품 중 특정 상품들을 삭제한다.
	 */
	public int deleteList(List<Integer> noList, String memberId) {
		System.out.println("CartDAO -> deleteList 메소드 호출");
		
		int result = 0;
		
		// 장바구니에서 메뉴 삭제하는 쿼리문
		String query = "DELETE FROM cart"
						+ " WHERE member_id=? AND no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 장바구니에서 메뉴 삭제
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(int no : noList) {
				// 칼럼 값 채우기
				psmt.setString(1, memberId);
				psmt.setInt(2, no);
				
				// 동적 쿼리문 실행
				result = psmt.executeUpdate();
				
				if(result == 0) {	// 한번이라도 삭제 실패 시 중지
					System.out.println("장바구니 업데이트 실패");
					return 0;
				}
			}
			
		} catch(Exception e) {
			System.out.println("장바구니에서 메뉴 삭제 중 예외 발생");
			System.out.println("CartDAO -> deleteList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* 메소드명: update
	 * 파라미터: String memberId (회원 id), int no (장바구니 관리 번호), int optionNo (옵션 번호), int quantity (수량)
	 * 반환값: int result (장바구니 업데이트 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 회원의 장바구니에 담긴 상품 중 특정 상품의 옵션과 수량을 수정한다.
	 */
	public int update(String memberId, int no, int optionNo, int quantity) {
		System.out.println("CartDAO -> update 메소드 호출");
		
		int result = 0;
		
		// 장바구니 업데이트하는 쿼리문
		String query = "UPDATE cart"
						+ " SET option_no=?, quantity=?"
						+ " WHERE member_id=? AND no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 장바구니 업데이트
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, optionNo);
			psmt.setInt(2, quantity);
			psmt.setString(3, memberId);
			psmt.setInt(4, no);
			
			// 동적 쿼리문 실행
			result = psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("장바구니 업데이트 중 예외 발생");
			System.out.println("CartDAO -> update 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return result;
	}
}
