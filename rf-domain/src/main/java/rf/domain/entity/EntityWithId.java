package rf.domain.entity;

public abstract class EntityWithId<T> {
	protected final long id;

	protected EntityWithId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public abstract T withId(long value);
}