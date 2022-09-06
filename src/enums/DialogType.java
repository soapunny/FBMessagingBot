package enums;

public enum DialogType {
	INFORMATIONAL_MESSAGE(1),
	WARNING_MESSAGE(2),
	TIMER_MESSAGE(3);
	
	private final int msgType;
	
	DialogType(int msgType) {
		this.msgType = msgType;
	}
	
	public int getMsgType(){
		return msgType;
	}
}
