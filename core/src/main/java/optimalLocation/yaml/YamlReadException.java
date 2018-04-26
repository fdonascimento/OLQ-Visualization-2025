package optimalLocation.yaml;

public class YamlReadException extends Exception {

	private static final long serialVersionUID = 1L;

	public YamlReadException(String message, Throwable cause) {
		super(message, cause);
	}

	public YamlReadException(String message) {
		super(message);
	}

	public YamlReadException(Throwable cause) {
		super(cause);
	}
}
