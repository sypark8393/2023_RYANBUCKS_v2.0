package dto;

public class OrderTotalDTO implements DTO {
	public static String[] cartNameList = {"비씨", "국민", "하나", "삼성", "신한", "현대", "롯데", "씨티", "농협", "우리"}; // 카드사 목록
	
	private String id;
	private java.sql.Timestamp orderDate;
	private String memberId;
	private String recipientName;
	private String recipientTel;
	private String recipientEmail;
	private String type;
	private String pickupBranch;
	private java.sql.Timestamp pickupTime;
	private String postCode;
	private String roadAddress;
	private String detailAddress;
	private int amount;
	private String payMethod;
	private String cardName;
	private String cardNo;
	private String cardQuota;
	private String authCode;
	
	// getter & setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.sql.Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(java.sql.Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getRecipientTel() {
		return recipientTel;
	}
	public void setRecipientTel(String recipientTel) {
		this.recipientTel = recipientTel;
	}
	public String getRecipientEmail() {
		return recipientEmail;
	}
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPickupBranch() {
		return pickupBranch;
	}
	public void setPickupBranch(String pickupBranch) {
		this.pickupBranch = pickupBranch;
	}
	public java.sql.Timestamp getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(java.sql.Timestamp pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getRoadAddress() {
		return roadAddress;
	}
	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardQuota() {
		return cardQuota;
	}
	public void setCardQuota(String cardQuota) {
		this.cardQuota = cardQuota;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
}
