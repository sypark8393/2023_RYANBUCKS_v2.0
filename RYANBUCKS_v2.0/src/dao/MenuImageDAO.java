package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.CartDTO;
import dto.DTO;
import dto.OrderDetailDTO;
import dto.WriteableReviewsDTO;

public class MenuImageDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public MenuImageDAO(ServletContext application) {
		super(application);
	}
	
	/* 메소드명: selectThumFileNameList
	 * 파라미터: List<? extends DTO> dtoList (DTO 인터페이스를 구현하는 xxxDTO 객체의 리스트)
	 * 반환값: List<String> thumFileNameList (썸네일 파일명 목록)
	 * 설명: 메뉴 번호 목록을 이용해 메뉴 썸네일 파일명 목록을 조회한다.
	 */
	public List<String> selectThumFileNameList(List<? extends DTO> dtoList) {
		System.out.println("MenuImageDAO -> selectThumFileNameList(List) 메소드 호출");
		
		List<String> thumFileNameList = new ArrayList<String>();
		
		// 썸네일 파일명을 조회하는 쿼리문
		String query = "SELECT file_name FROM menu_image"
						+ " WHERE menu_no=? AND type='representative'";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 썸네일 파일명 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			for(DTO dto : dtoList) {
				// 칼럼 값 채우기
				if(dto instanceof OrderDetailDTO) {	// OrderDetailDTO 객체인 경우
					psmt.setInt(1, ((OrderDetailDTO)dto).getMenuNo());
					
				} else if (dto instanceof CartDTO) {	// CartDTO 객체인 경우
					psmt.setInt(1, ((CartDTO)dto).getMenuNo());
					
				} else if (dto instanceof WriteableReviewsDTO) {	// WriteableReviewsDTO 객체인 경우
					psmt.setInt(1, ((WriteableReviewsDTO)dto).getMenuNo());
				}
				
				// 동적 쿼리문 실행
				rs = psmt.executeQuery();
					
				// 썸네일 파일명 가져오기
				if(rs.next()) {
					thumFileNameList.add(rs.getString("file_name"));
				}
			}
			
		} catch(Exception e) {
			System.out.println("썸네일 파일명 조회 중 예외 발생");
			System.out.println("MenuImageDAO -> selectThumFileNameList(List) 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return thumFileNameList;
	}

	/* 메소드명: selectThumFileNameList
	 * 파라미터: String type (유형: drink, food)
	 * 반환값: Map<Integer, String> map (<메뉴 번호, 썸네일 파일명> 형태로 정보를 가지는 맵)
	 * 설명: 유형(음료/푸드)에 따른 메뉴의 썸네일 파일명 목록을 조회한다.
	 */
	public Map<Integer, String> selectThumFileNameList(String type) {
		System.out.println("MenuImageDAO -> selectThumFileNameList(String) 메소드 호출");
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		// 이미지 썸네일 목록을 조회하는 쿼리문
		String query = "SELECT m.no, mi.file_name"
						+ " FROM menu m"
						+ " JOIN category ct ON m.category_id=ct.id"
						+ " JOIN menu_image mi ON m.no=mi.menu_no"
						+ " WHERE NOT ct.visibility='hidden' AND ct.type=? AND NOT m.visibility='hidden' AND mi.type='representative'";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 이미지 썸네일 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setString(1, type);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 이미지 썸네일 목록 가져오기
			while(rs.next()) {
				map.put(rs.getInt("no"), rs.getString("file_name"));
				
			}
			
		} catch(Exception e) {
			System.out.println("이미지 썸네일 목록 조회 중 예외 발생");
			System.out.println("MenuImageDAO -> selectThumFileNameList(String) 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return map;
	}
	
	/* 메소드명: selectFileNameList
	 * 파라미터: int no (메뉴 번호)
	 * 반환값: List<String> list (이미지 파일명 목록)
	 * 설명: 특정 메뉴의 이미지 목록을 조회한다.
	 */
	public List<String> selectFileNameList(int no) {
		System.out.println("MenuImageDAO -> selectFileNameList 메소드 호출");
		
		List<String> list = new ArrayList<String>();
		
		// 이미지 파일명 목록을 조회하는 쿼리문
		String query = "SELECT * FROM menu_image"
						+ " WHERE menu_no=?"
						+ " ORDER BY type";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 이미지 파일명 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
			
			// 칼럼 값 채우기
			psmt.setInt(1, no);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 이미지 파일명 목록 가져오기
			while(rs.next()) {
				list.add(rs.getString("file_name"));
			}
			
		} catch(Exception e) {
			System.out.println("이미지 파일명 목록 조회 중 예외 발생");
			System.out.println("MenuImageDAO -> selectFileNameList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
}
