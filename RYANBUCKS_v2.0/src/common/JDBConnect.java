package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

public class JDBConnect {
	public Connection con;				// 커넥션 객체
	public Statement stmt;				// 정적 쿼리 객체
	public PreparedStatement psmt;		// 동적 쿼리 객체
	public ResultSet rs;				// 결과 집합
	
	/* 생성자: JDBConnect
	 * 파라미터: ServletContext application (웹 애플리케이션 단위의 정보를 서버에서 저장하고 있기 위한 객체)
	 * 설명: 데이터베이스에 연결한다.
	 */
	public JDBConnect(ServletContext application) {
		try {
			// JDBC 드라이버 로드
			String driver = application.getInitParameter("OracleDriver");
			Class.forName(driver);
			
			// DB 연결
			String url = application.getInitParameter("OracleURL");
			String id = application.getInitParameter("OracleId");
			String pwd = application.getInitParameter("OraclePwd");
			con = DriverManager.getConnection(url, id, pwd);
			
			System.out.println("DB 연결 성공");
			
		} catch (Exception e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();

		}
		
	}
	
	/* 생성자: JDBConnect
	 * 파라미터: Connection con (데이터베이스에 연결된 커넥션 객체)
	 * 설명: 파라미터로 받은 커넥션 객체로 커넥션을 초기화한다.
	 */
	public JDBConnect(Connection con) {
		if(con != null) {
			this.con = con;
			
			System.out.println("DB 연결 성공 (커넥션 객체 전달받음)");
		}
		
	}
	
	/* 메소드명: getConnection
	 * 파라미터: 없음
	 * 반환값 Connection con (DB에 연결된 커넥션 객체)
	 * 설명: 데이터베이스에 연결된 커넥션 객체를 반환한다.
	 */
	public Connection getConnection() {
		return con;
	}
	
	/* 메소드명: close
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 설명: 데이터베이스 연결을 위해 할당된 자원을 해제한다. (데이터베이스 연결 해제)
	 */
	public void close() {
		try {
			if(rs != null)
				rs.close();
			
			if(stmt != null)
				stmt.close();
			
			if(psmt != null)
				psmt.close();
			
			if(con != null)
				con.close();
			
			System.out.println("JDBC 자원 해제");
			
		} catch (Exception e) {
			System.out.println("JDBC 자원 해제 실패");
			e.printStackTrace();
			
		}
		
	}

	/* 메소드명: setAutoCommit
	 * 파라미터: boolean autoCommit (auto-commit 설정 여부 - true: auto-commit, false: not auto-commit)
	 * 반환값: 없음
	 * 설명: AutoCommit 여부를 설정한다.
	 */
	public void setAutoCommit(boolean autoCommit) {
		try {
			con.setAutoCommit(autoCommit);
			
			System.out.println("Auto-commit 설정: " + autoCommit);
			
		} catch (SQLException e) {
			System.out.println("Auto-commit 설정 실패");
			e.printStackTrace();
			
		}
	}

	/* 메소드명: rollback
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 설명: 트랜잭션을 rollback 한다.
	 */
	public void rollback() {
		try {
			con.rollback();
			
			System.out.println("트랜잭션 rollabck");
			
		} catch (SQLException e) {
			System.out.println("트랜잭션 rollabck 실패");
			e.printStackTrace();
		}
		
	}
	
	/* 메소드명: commit
	 * 파라미터: 없음
	 * 반환값: 없음
	 * 설명: 트랜잭션을 commit 한다.
	 */
	public void commit() {
		try {
			con.commit();

			System.out.println("트랜잭션 commit");
			
		} catch (SQLException e) {
			System.out.println("트랜잭션 commit 실패");
			e.printStackTrace();
		}
		
	}
	
}
