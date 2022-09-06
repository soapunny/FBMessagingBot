package exception;

public class CannotSleepInThreadException extends Exception {
	private static final long serialVersionUID = 6677587740003346287L;

	public CannotSleepInThreadException(String msg) {
		super(String.format("[%s] : %s", "CannotSleepInThreadException", msg));
	}
}
