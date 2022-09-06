package enums;

public enum AnswerReferences {
	AGENT_NAME(0),
	AGENT_NUMBER(1),
	PROPERTY_SUBJECT(2);
	
	private final int intValue;
	
	AnswerReferences(int intValue) {
		this.intValue = intValue;
	}
	
	public int getIntValue() {
		return intValue;
	}
}
