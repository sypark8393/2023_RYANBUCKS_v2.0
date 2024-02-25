package dto;

public class MenuOptionDTO implements DTO {
	private int no;
	private int menuNo;
	private String option1Name;
	private String option1Value;
	private String option2Name;
	private String option2Value;
	private int price;
	private int stock;
	private String visibility;
	
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
	public String getOption1Name() {
		return option1Name;
	}
	public void setOption1Name(String option1Name) {
		this.option1Name = option1Name;
	}
	public String getOption1Value() {
		return option1Value;
	}
	public void setOption1Value(String option1Value) {
		this.option1Value = option1Value;
	}
	public String getOption2Name() {
		return option2Name;
	}
	public void setOption2Name(String option2Name) {
		this.option2Name = option2Name;
	}
	public String getOption2Value() {
		return option2Value;
	}
	public void setOption2Value(String option2Value) {
		this.option2Value = option2Value;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
}
