package enums;

public enum MessageType {
	MESSAGE_WITH_CLIENT_NUMBER(1),
	MESSAGE_WITH_CLIENT_EMAIL(2),
	MESSAGE_WITH_A_QUISTION_MARK(3);
	
	private final int colNumber;
	
	MessageType(int colNumber) {
		this.colNumber = colNumber;
	}
	
	public int getColNumber(){
		return colNumber;
	}
}
