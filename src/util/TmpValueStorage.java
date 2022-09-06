package util;

public class TmpValueStorage <T>{

	private T value;
	
	public TmpValueStorage(T value) {this.value = value;}
	
	public T getValue() {return value;}
	public void setValue(T value) {this.value = value;}
	
}

