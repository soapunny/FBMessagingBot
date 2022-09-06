package exception;

public class WebDriverNotFoundException extends Exception {
	private static final long serialVersionUID = -5653639902549860659L;

	public WebDriverNotFoundException(String msg) {
		super(String.format("[%s] : %s", "WebDriverNotFoundException", msg));
	}
}
