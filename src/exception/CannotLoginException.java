package exception;

public class CannotLoginException extends Exception {
	private static final long serialVersionUID = -7145512304447150363L;

	public CannotLoginException(String msg) {
		super(String.format("[%s] : %s", "CannotLoginException", msg));
	}
}
