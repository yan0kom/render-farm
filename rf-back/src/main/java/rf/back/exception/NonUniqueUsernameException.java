package rf.back.exception;

public class NonUniqueUsernameException extends RuntimeException {
	private static final long serialVersionUID = -4262899087932324301L;

	public NonUniqueUsernameException(String message) {
		super(message);
	}
}
