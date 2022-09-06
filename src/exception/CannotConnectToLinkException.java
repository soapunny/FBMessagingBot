package exception;

public class CannotConnectToLinkException extends Exception {
	private static final long serialVersionUID = 5394749300112729925L;

	public CannotConnectToLinkException(String msg) {
		super(String.format("[%s] : %s", "CannotConnectToLinkException", msg));
	}
}
