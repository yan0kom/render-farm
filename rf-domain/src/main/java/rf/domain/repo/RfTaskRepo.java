package rf.domain.repo;

import java.util.stream.Stream;

import rf.domain.entity.RfTask;

public interface RfTaskRepo {
	RfTask add(RfTask rfTask);
	RfTask update(RfTask rfTask);
	void remove(RfTask rfTask);

	Stream<RfTask> getAll();
}
