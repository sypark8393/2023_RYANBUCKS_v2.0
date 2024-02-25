package dao;

import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;
import dto.CategoryDTO;

public class CategoryDAO extends JDBConnect {
	// ServeletContext 객체를 매개변수로 받는 생성자
	public CategoryDAO(ServletContext application) {
		super(application);
	}
	
	/* 메소드명: selectList
	 * 파라미터: String type (유형: drink, food)
	 * 반환값: List<CategoryDTO> list (유형별 카테고리 목록)
	 * 설명: 유형(음료/푸드)에 따른 카테고리 목록을 조회한다.
	 */
	public List<CategoryDTO> selectList(String type) {
		System.out.println("CategoryDAO -> selectList 메소드 호출");
		
		List<CategoryDTO> list = new Vector<CategoryDTO>();
		
		// 카테고리 목록을 조회하는 쿼리문
		String query = "SELECT * FROM category"
						+ " WHERE type=? AND NOT visibility='hidden'";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 카테고리 목록 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
		
			// 칼럼 값 채우기
			psmt.setString(1, type);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
			
			// 카테고리 목록 가져오기
			while(rs.next()) {
				CategoryDTO categoryDto = new CategoryDTO();
				
				categoryDto.setId(rs.getString("id"));
				categoryDto.setName(rs.getString("name"));
				categoryDto.setType(rs.getString("type"));
				categoryDto.setVisibility(rs.getString("visibility"));
				
				list.add(categoryDto);
			}
			
		} catch(Exception e) {
			System.out.println("카테고리 목록 조회 중 예외 발생");
			System.out.println("CategoryDAO -> selectList 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	/* 메소드명: select
	 * 파라미터: String id (카테고리 id)
	 * 반환값: CategoryDTO categoryDto (카테고리 정보가 저장된 CategoryDTO 객체)
	 * 설명: 특정 카테고리의 정보를 조회힌다.
	 */
	public CategoryDTO select(String id) {
		System.out.println("CategoryDAO -> select 메소드 호출");
		
		CategoryDTO categoryDto = null;
		
		// 카테고리 정보를 조회하는 쿼리문
		String query = "SELECT * FROM category"
						+ " WHERE NOT visibility='hidden' AND id=?";
		//System.out.println("SQL 쿼리문: " + query);
		
		// 카테고리 정보 조회
		try {
			// 동적 쿼리문 생성
			psmt = con.prepareStatement(query);
		
			// 칼럼 값 채우기
			psmt.setString(1, id);
			
			// 동적 쿼리문 실행
			rs = psmt.executeQuery();
						
			// 카테고리 정보 가져오기
			if(rs.next()) {
				categoryDto = new CategoryDTO();
				
				categoryDto.setId(rs.getString("id"));
				categoryDto.setName(rs.getString("name"));
				categoryDto.setType(rs.getString("type"));
				categoryDto.setVisibility(rs.getString("visibility"));
			}
						
		} catch(Exception e) {
			System.out.println("카테고리 정보 조회 중 예외 발생");
			System.out.println("CategoryDAO -> select 메소드 확인 바람");
			e.printStackTrace();
		}
		
		return categoryDto;
	}
	
}
