package dto;

public class WriteableReviewsDTO implements DTO {
	private int no;
	private int menuNo;
	private String nameKor;
	private String optionInfo;
	private String memberId;
	
	// getter & setter
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(int menuNo) {
		this.menuNo = menuNo;
	}
	public String getNameKor() {
		return nameKor;
	}
	public void setNameKor(String nameKor) {
		this.nameKor = nameKor;
	}
	public String getoptionInfo() {
		return optionInfo;
	}
	public void setoptionInfo(String optionInfo) {
		this.optionInfo = optionInfo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
