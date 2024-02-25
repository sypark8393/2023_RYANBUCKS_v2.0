package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.CartDTO;
import dto.DTO;
import dto.MenuDTO;
import dto.OrderDetailDTO;

public class MenuDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public MenuDAO(ServletContext application) {
		super(application);
	}
	
	// Connection 객체를 매개변수로 받는 생성자
	public MenuDAO(Connection con) {
		super(con);
	}
	
	/* 메소드명: selectNameList
	 * 파라미터: List<? extends DTO> dtoList (DTO 인터페이스를 구현하는 xxxDTO 객체의 리스트)
	 * 반환값: List<String> nameList (메뉴명 목록)
	 * 설명: 메뉴 번호 목록을 이용해 각 메뉴의 메뉴명(한글) 목록을 조회한다.
	 */
	public List<String> selectNameList(List<? extends DTO> dtoList) {
		System.out.println("MenuDAO -> selectNameList 메소드 호출");
		
		List<String> nameList = new ArrayList<String>();
		
		// 메뉴명을 조회하는 쿼리문
		String query = "SELECT name_kor FROM menu"
						+ " WHERE no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 메뉴명 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(DTO dto : dtoList) {
				// 칼럼 값 채우기
				if(dto instanceof OrderDetailDTO) {	// OrderDetailDTO 객체인 경우
					psmt.setInt(1, ((OrderDetailDTO)dto).getMenuNo());
					
				}
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
				
				// 메뉴명 가져오기
				if(rs.next()) {
					nameList.add(rs.getString("name_kor"));
				}
			}
			
		} catch(Exception e) {
			System.out.println("메뉴명 조회 중 예외 발생");
			System.out.println("MenuDAO -> selectNameList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return nameList;
	}
	
	/* 메소드명: selectList
	 * 파라미터: String type (유형: drink, food)
	 * 반환값: Map<String, List<MenuDTO>> map (<카테고리 id, 카테고리별 메뉴 목록> 형태로 정보를 가지는 맵)
	 * 설명: 유형(음료/푸드)에 따른 메뉴 메뉴 목록을 카테고리별 목록으로 조회한다.
	 */
	public Map<String, List<MenuDTO>> selectList(String type) {
		System.out.println("MenuDAO -> selectList 메소드 호출");
		
		Map<String, List<MenuDTO>> map = new HashMap<String, List<MenuDTO>>();
		
		// 카테고리별 메뉴 목록을 조회하는 쿼리문
		String query = "SELECT m.*"
						+ " FROM menu m JOIN category ct ON m.category_id=ct.id"
						+ " WHERE NOT ct.visibility='hidden' AND ct.type=? AND NOT m.visibility='hidden'"
						+ " ORDER BY m.category_id, no";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 카테고리별 메뉴 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, type);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 카테고리별 메뉴 목록 가져오기
			String categoryId = "";
			List<MenuDTO> list = new Vector<MenuDTO>();
			
			while(rs.next()) {
				// 처음 읽어오는 케이스인 경우에만
				if(categoryId.equals("")) {
					categoryId = rs.getString("category_id");
				}
				
				MenuDTO menuDto = new MenuDTO();
				
				menuDto.setNo(rs.getInt("no"));
				menuDto.setNameKor(rs.getString("name_kor"));
				menuDto.setNameEng(rs.getString("name_eng"));
				menuDto.setDescription(rs.getString("description"));
				menuDto.setSalesPrice(rs.getInt("sales_price"));
				menuDto.setStock(rs.getInt("stock"));
				menuDto.setVisibility(rs.getString("visibility"));
				menuDto.setCategoryId(rs.getString("category_id"));
				menuDto.setRegistDate(rs.getDate("regist_date"));
				
				// 새로운 카테고리가 아니라면 리스트에 추가
				if(categoryId.equals(menuDto.getCategoryId())) {
					list.add(menuDto);
				
				// 새로운 카테고리인 경우 이전 카테고리의 메뉴 목록을 맵에 저장
				} else {
					map.put(categoryId, list);		// map에 메뉴 목록 저장
					list = new Vector<MenuDTO>();	// 새로운 리스트 생성
					
					categoryId = menuDto.getCategoryId();	// 새로운 카테고리명 저장
					list.add(menuDto);
				}
			}
			
			map.put(categoryId, list);	// 마지막 카테고리의 메뉴 목록을 map에 저장
			
		} catch(Exception e) {
			System.out.println("카테고리별 메뉴 목록 조회 중 예외 발생");
			System.out.println("MenuDAO -> selectList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		
		return map;
	}
	
	/* 메소드명: selectNewList
	 * 파라미터: String type (유형)
	 * 반환값: List<MenuDTO> list (메뉴 정보를 가진 MenuDTO 객체 리스트)
	 * 설명: 유형(음료/푸드)에 따른 신메뉴 목록을 조회한다.
	 */
	public List<MenuDTO> selectNewList(String type) {
		System.out.println("MenuDAO -> selectNewList 메소드 호출");
		
		List<MenuDTO> list = new Vector<MenuDTO>();
		
		// 메뉴 정보를 조회하는 쿼리문
		String query = "SELECT m.*"
						+ " FROM menu m JOIN category ct ON m.category_id=ct.id"
						+ " WHERE NOT ct.visibility='hidden' AND ct.type=? AND NOT m.visibility='hidden' AND m.regist_date>='23/09/01'";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 메뉴 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, type);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
						
			// 메뉴 정보 조회
			while(rs.next()) {
				MenuDTO menuDto = new MenuDTO();
				
				menuDto.setNo(rs.getInt("no"));
				menuDto.setNameKor(rs.getString("name_kor"));
				menuDto.setNameEng(rs.getString("name_eng"));
				menuDto.setDescription(rs.getString("description"));
				menuDto.setSalesPrice(rs.getInt("sales_price"));
				menuDto.setStock(rs.getInt("stock"));
				menuDto.setVisibility(rs.getString("visibility"));
				menuDto.setCategoryId(rs.getString("category_id"));
				menuDto.setRegistDate(rs.getDate("regist_date"));
				
				list.add(menuDto);
				
			}
			
		} catch(Exception e) {
			System.out.println("메뉴 정보 조회 중 예외 발생");
			System.out.println("MenuDAO -> selectNewList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	/* 메소드명: selectList
	 * 파라미터: List<? extends DTO> dtoList (DTO 인터페이스를 구현하는 xxxDTO 객체의 리스트)
	 * 반환값: List<MenuDTO> list (메뉴 정보를 가진 MenuDTO 객체 리스트)
	 * 설명: 메뉴 번호 목록을 이용해 메뉴 정보 목록을 조회한다.
	 */
	public List<MenuDTO> selectList(List<? extends DTO> dtoList) {
		System.out.println("MenuDAO -> selectList(List) 메소드 호출");
		
		List<MenuDTO> list = new Vector<MenuDTO>();
		
		// 메뉴 정보를 조회하는 쿼리문
		String query = "SELECT * FROM menu"
				+ " WHERE NOT visibility='hidden' AND no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 메뉴 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
		
			for(DTO dto : dtoList) {
				// 칼럼 값 채우기
				if(dto instanceof CartDTO) {		// CartDTO 객체인 경우
					psmt.setInt(1, ((CartDTO)dto).getMenuNo());
					
				}
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
				
				// 메뉴명 가져오기
				if(rs.next()) {
					MenuDTO menuDto = new MenuDTO();
					
					menuDto.setNo(rs.getInt("no"));
					menuDto.setNameKor(rs.getString("name_kor"));
					menuDto.setNameEng(rs.getString("name_eng"));
					menuDto.setDescription(rs.getString("description"));
					menuDto.setSalesPrice(rs.getInt("sales_price"));
					menuDto.setStock(rs.getInt("stock"));
					menuDto.setVisibility(rs.getString("visibility"));
					menuDto.setCategoryId(rs.getString("category_id"));
					menuDto.setRegistDate(rs.getDate("regist_date"));
					
					list.add(menuDto);
				}
			}
			
		} catch(Exception e) {
			System.out.println("메뉴 정보 조회 중 예외 발생");
			System.out.println("MenuDAO -> selectList(List) 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}

	/* 메소드명: select
	 * 파라미터: int no (메뉴 번호)
	 * 반환값: MenuDTO menuDto (메뉴 정보가 저장된 MenuDTO 객체)
	 * 설명: 특정 메뉴의 정보를 조회한다.
	 */
	public MenuDTO select(int no) {
		System.out.println("MenuDAO -> select 메소드 호출");
		
		MenuDTO menuDto = null;
		
		// 메뉴 정보를 조회하는 쿼리문
		String query = "SELECT * FROM menu"
						+ " WHERE NOT visibility='hidden' AND no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 메뉴 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
		
			// 칼럼 값 채우기
			psmt.setInt(1, no);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 메뉴 정보 가져오기
			if(rs.next()) {
				menuDto = new MenuDTO();
				
				menuDto.setNo(rs.getInt("no"));
				menuDto.setNameKor(rs.getString("name_kor"));
				menuDto.setNameEng(rs.getString("name_eng"));
				menuDto.setDescription(rs.getString("description"));
				menuDto.setSalesPrice(rs.getInt("sales_price"));
				menuDto.setStock(rs.getInt("stock"));
				menuDto.setVisibility(rs.getString("visibility"));
				menuDto.setCategoryId(rs.getString("category_id"));
				menuDto.setRegistDate(rs.getDate("regist_date"));
			}
		
		} catch(Exception e) {
			System.out.println("메뉴 정보 조회 중 예외 발생");
			System.out.println("MenuDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return menuDto;
	}

	/* 메소드명: updateStock
	 * 파라미터:  List<OrderDetailDTO> orderDetailList (상세 주문 정보를 가지고 있는 리스트)
	 * 반환값: int result (재고업데이트 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 메뉴 재고를 수정한다.
	 */
	public int updateStock(List<OrderDetailDTO> orderDetailList) {
		System.out.println("MenuDAO -> updateStock 메소드 호출");
 		
 		int result = 0;
 		
 		// 재고 업데이트하는 쿼리문
 		String query = "UPDATE menu SET stock=stock-?"
 						+ " WHERE no=?";
 		//System.out.println("SQL 쿼리문: " + query);
 		
 		// 재고 업데이트
 		try {
 			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
 			
			for(OrderDetailDTO orderDetailDto : orderDetailList) {
				// 칼럼 값 채우기
				psmt.setInt(1, orderDetailDto.getQuantity());
				psmt.setInt(2, orderDetailDto.getMenuNo());
				
				// 동적 쿼리문 실행
				result = psmt.executeUpdate();
				
				if(result == 0) {	// 한번이라도 업데이트 실패 시 중지
					System.out.println("메뉴 재고 업데이트 실패");
					return 0;
				}
			}
 			
 		} catch(Exception e) {
			System.out.println("재고 업데이트 중 예외 발생");
			System.out.println("MenuDAO -> updateStock 메소드 확인 바람");
			e.printStackTrace();
		}
 		
 		return result;
	}
	
}
