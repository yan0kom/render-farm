package rf.back.exception;

public class NonUniqueIdException extends RuntimeException {
	private static final long serialVersionUID = -4262899087932324301L;

	public NonUniqueIdException(String message) {
		super(message);
	}
}
