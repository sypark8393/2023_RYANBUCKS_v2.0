package dto;

public class MemberDTO implements DTO {
	private String id;
	private String password;
	private String name;
	private java.sql.Date joinDate;
	private String email;
	private String tel;
	private java.sql.Date outDate;
	private String gender;
	private java.sql.Date birth;
	private String marketingInfoAgree;
	
	// getter & setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public java.sql.Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(java.sql.Date joinDate) {
		this.joinDate = joinDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public java.sql.Date getOutDate() {
		return outDate;
	}
	public void setOutDate(java.sql.Date outDate) {
		this.outDate = outDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public java.sql.Date getBirth() {
		return birth;
	}
	public void setBirth(java.sql.Date birth) {
		this.birth = birth;
	}
	public String getMarketingInfoAgree() {
		return marketingInfoAgree;
	}
	public void setMarketingInfoAgree(String marketingInfoAgree) {
		this.marketingInfoAgree = marketingInfoAgree;
	}
}
