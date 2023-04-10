package rf.back.service.impl;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import rf.domain.entity.RfTask;
import rf.domain.repo.RfTaskRepo;
import rf.domain.service.RfTaskService;

@Service
class RfTaskServiceImpl implements RfTaskService {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RfTaskRepo rfTaskRepo;

	public RfTaskServiceImpl(RfTaskRepo rfTaskRepo) {
		this.rfTaskRepo = rfTaskRepo;
	}

	@Override
	public Stream<RfTask> getAllTasks() {
		log.debug("get all tasks");
		return rfTaskRepo.getAll();
	}
}
