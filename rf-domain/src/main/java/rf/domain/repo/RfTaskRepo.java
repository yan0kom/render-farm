package rf.domain.repo;

import java.util.Optional;
import java.util.stream.Stream;

import rf.domain.entity.RfTask;

public interface RfTaskRepo {
	RfTask add(RfTask rfTask);
	RfTask set(RfTask rfTask);
	void remove(RfTask rfTask);

	Optional<RfTask> getById(Long id);
	Stream<RfTask> getAll();
}
