package dto;

public class EventImageDTO implements DTO {
	private int eventPostNo;
	private String fileName;
	private String type;
	
	// getter & setter
	public int getEventPostNo() {
		return eventPostNo;
	}
	public void setEventPostNo(int eventPostNo) {
		this.eventPostNo = eventPostNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
