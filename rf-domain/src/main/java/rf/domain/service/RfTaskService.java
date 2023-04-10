package rf.domain.service;

import java.util.stream.Stream;

import rf.domain.entity.RfTask;

public interface RfTaskService {
	Stream<RfTask> getAllTasks();
}
