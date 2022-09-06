package exception;

public class ElementNotFoundException extends Exception {
	private static final long serialVersionUID = -488402328467985015L;

	public ElementNotFoundException(String msg) {
		super(String.format("[%s] : %s", "ElementNotFoundException", msg));
	}
}
