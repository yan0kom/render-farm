package rf.back.exception;

public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4262899087932324301L;

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Class<?> clazz, Long id) {
		this(String.format("%s with id=%d not found", clazz.getSimpleName(), id));
	}
}
