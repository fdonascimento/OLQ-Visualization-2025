package optimalLocation.parser;

public class ClientParseException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClientParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientParseException(String message) {
		super(message);
	}

	public ClientParseException(Throwable cause) {
		super(cause);
	}
}
