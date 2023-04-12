package rf.domain.repo;

import java.util.Optional;
import java.util.stream.Stream;

public interface EntityRepo<T> {
	T add(T entity);
	T set(T entity);
	void remove(T entity);

	Optional<T> getById(Long id);
	Stream<T> getAll();
}
