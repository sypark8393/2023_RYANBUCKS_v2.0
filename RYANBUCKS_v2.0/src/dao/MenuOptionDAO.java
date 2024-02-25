package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.CartDTO;
import dto.DTO;
import dto.MenuOptionDTO;
import dto.OrderDetailDTO;

public class MenuOptionDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public MenuOptionDAO(ServletContext application) {
		super(application);
	}
	
	// Connection 객체를 매개변수로 받는 생성자
	public MenuOptionDAO(Connection con) {
		super(con);
	}
	
	/* 메소드명: selectInfoList
	 * 파라미터: List<? extends DTO> dtoList (DTO 인터페이스를 구현하는 xxxDTO 객체의 리스트)
	 * 반환값: List<String> infoList (메뉴 옵션 정보: "1번 옵션 | 2번 옵션" 형식)
	 * 설명: 옵션 번호 목록을 이용해 옵션 정보 목록을 조회한다.
	 */
	public List<String> selectInfoList(List<? extends DTO> dtoList) {
		System.out.println("MenuOptionDAO -> selectInfoList 메소드 호출");
		
		List<String> infoList = new ArrayList<String>();
		
		// 옵션 정보를 조회하는 쿼리문
		String query = "SELECT CASE WHEN option2_value IS NOT NULL THEN option1_value || ' | ' || option2_value"
						+ " ELSE option1_value"
						+ " END AS value"
						+ " FROM menu_option"
						+ " WHERE no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 옵션 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(DTO dto : dtoList) {
				// 칼럼 값 채우기
				if(dto instanceof OrderDetailDTO) {	// OrderDetailDTO 객체인 경우
					psmt.setInt(1, ((OrderDetailDTO)dto).getOptionNo());
					
				} else if (dto instanceof CartDTO){	// CartDTO 객체인 경우
					psmt.setInt(1, ((CartDTO)dto).getOptionNo());
					
				}
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
				
				// 메뉴명 가져오기
				if(rs.next()) {
					infoList.add(rs.getString("value"));
					
				} else {
					infoList.add("");
					
				}
			}
			
		}  catch(Exception e) {
			System.out.println("옵션 정보 조회 중 예외 발생");
			System.out.println("MenuOptionDAO -> selectInfoList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return infoList;
	}

	/* 메소드명: selectList
	 * 파라미터: int menuNo (메뉴 번호)
	 * 반환값: List<MenuOptionDTO> list (메뉴 옵션 목록)
	 * 설명: 특정 메뉴의 옵션 목록을 조회한다.
	 */
	public List<MenuOptionDTO> selectList(int menuNo) {
		System.out.println("MenuOptionDAO -> selectList(int) 메소드 호출");
		
		List<MenuOptionDTO> list = new Vector<MenuOptionDTO>();
		
		// 옵션 목록 조회
		String query = "SELECT * FROM menu_option"
						+ " WHERE NOT visibility='hidden' AND menu_no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 옵션 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, menuNo);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 옵션 목록 가져오기
			while(rs.next()) {
				MenuOptionDTO menuOptionDto = new MenuOptionDTO();
				
				menuOptionDto.setNo(rs.getInt("no"));
				menuOptionDto.setMenuNo(rs.getInt("menu_no"));
				menuOptionDto.setOption1Name(rs.getString("option1_name"));
				menuOptionDto.setOption1Value(rs.getString("option1_value"));
				menuOptionDto.setOption2Name(rs.getString("option2_name"));
				menuOptionDto.setOption2Value(rs.getString("option2_value"));
				menuOptionDto.setPrice(rs.getInt("price"));
				menuOptionDto.setStock(rs.getInt("stock"));
				menuOptionDto.setVisibility(rs.getString("visibility"));
				
				list.add(menuOptionDto);
			}
			
		} catch(Exception e) {
			System.out.println("옵션 목록 조회 중 예외 발생");
			System.out.println("MenuOptionDAO -> selectList(int) 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	/* 메소드명: selectList
	 * 파라미터: List<? extends DTO> dtoList (DTO 인터페이스를 구현하는 xxxDTO 객체의 리스트)
	 * 반환값: List<String> list (메뉴 옵션 목록)
	 * 설명: 옵션 번호 목록을 이융해 옵션 목록을 조회한다.
	 */
	public List<MenuOptionDTO> selectList(List<? extends DTO> dtoList) {
		System.out.println("MenuOptionDAO -> selectList(List) 메소드 호출");
		
		List<MenuOptionDTO> list = new Vector<MenuOptionDTO>();
		
		// 옵션 정보를 조회하는 쿼리문
		String query = "SELECT * FROM menu_option"
						+ " WHERE NOT visibility='hidden' AND no=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 옵션 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(DTO dto : dtoList) {
				// 칼럼 값 채우기
				if(dto instanceof CartDTO) {	// CartDTO 객체인 경우
					psmt.setInt(1, ((CartDTO)dto).getOptionNo());
					
				}
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
				
				// 옵션 정보 가져오기
				if(rs.next()) {
					MenuOptionDTO menuOptionDto = new MenuOptionDTO();
					
					menuOptionDto.setNo(rs.getInt("no"));
					menuOptionDto.setMenuNo(rs.getInt("menu_no"));
					menuOptionDto.setOption1Name(rs.getString("option1_name"));
					menuOptionDto.setOption1Value(rs.getString("option1_value"));
					menuOptionDto.setOption2Name(rs.getString("option2_name"));
					menuOptionDto.setOption2Value(rs.getString("option2_value"));
					menuOptionDto.setPrice(rs.getInt("price"));
					menuOptionDto.setStock(rs.getInt("stock"));
					menuOptionDto.setVisibility(rs.getString("visibility"));
					
					list.add(menuOptionDto);
					
				} else {
					list.add(new MenuOptionDTO());
					
				}
				
			}
			
		} catch(Exception e) {
			System.out.println("옵션 목록 조회 중 예외 발생");
			System.out.println("MenuOptionDAO -> selectList(List) 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}

	/* 메소드명: updateStock
	 * 파라미터:  List<OrderDetailDTO> orderDetailList (상세 주문 정보를 가지고 있는 리스트)
	 * 반환값: int result (재고업데이트 성공 여부 -> 1: 성공, 0: 실패)
	 * 설명: 옵션 번호 목록을 이용해 옵션 재고를 수정한다.
	 */
	public int updateStock(List<OrderDetailDTO> orderDetailList) {
		System.out.println("MenuOptionDAO -> updateStock 메소드 호출");
 		
 		int result = 0;
 		
 		// 재고 업데이트하는 쿼리문
 		String query = "UPDATE menu_option SET stock=stock-?"
 						+ " WHERE no=?";
 		//System.out.println("SQL 쿼리문: " + query);
 		
 		// 재고 업데이트
 		try {
 			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
 			
			for(OrderDetailDTO orderDetailDto : orderDetailList) {
				// 옵션이 없으면 넘기기
				if(orderDetailDto.getOptionNo() == 0) {
					continue;
				}
				
				// 칼럼 값 채우기
				psmt.setInt(1, orderDetailDto.getQuantity());
				psmt.setInt(2, orderDetailDto.getOptionNo());
				
				// 동적 쿼리문 실행
				result = psmt.executeUpdate();
				
				if(result == 0) {	// 한번이라도 업데이트 실패 시 중지
					System.out.println("메뉴 옵션 재고 업데이트 실패");
					return 0;
				}
			}
 			
 		} catch(Exception e) {
			System.out.println("재고 업데이트 중 예외 발생");
			System.out.println("MenuOptionDAO -> updateStock 메소드 확인 바람");
			e.printStackTrace();
		}
 		
 		return result;
	}
}
