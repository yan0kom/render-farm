package rf.back.repo.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import rf.back.exception.EntityNotFoundException;
import rf.back.exception.NonUniqueIdException;
import rf.domain.entity.RfTask;
import rf.domain.repo.RfTaskRepo;

@Repository
class RfTaskRepoInMemoryImpl implements RfTaskRepo {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final Map<Long, RfTask> storage = new HashMap<>();
	private final ReadWriteLock storageLock = new ReentrantReadWriteLock(true);

	public RfTaskRepoInMemoryImpl() {
	}

	@PostConstruct
	public void init() {
		add(new RfTask(101L, "sleep", "sleep ${duration}",
				Map.of("duration", "for example: 39s, 2m")));
	}

	@Override
	public RfTask add(RfTask rfTask) {
		try {
			storageLock.writeLock().lock();
			log.debug("add task {}", rfTask);

			storage.computeIfPresent(rfTask.getId(),
					(k, v) -> {
						log.debug("Non unique id '{}'", rfTask.getId());
						throw new NonUniqueIdException(rfTask.toString());
					});
			storage.put(rfTask.getId(), rfTask);
		} finally {
			storageLock.writeLock().unlock();
		}
		return rfTask;
	}

	@Override
	public RfTask update(RfTask rfTask) {
		try {
			storageLock.writeLock().lock();
			log.debug("update task {}", rfTask);

			Optional.ofNullable(storage.get(rfTask.getId()))
					.ifPresentOrElse(
							unused -> storage.put(rfTask.getId(), rfTask),
							() -> { throw new EntityNotFoundException(rfTask.getClass(), rfTask.getId()); });
		} finally {
			storageLock.writeLock().unlock();
		}
		return rfTask;
	}

	@Override
	public void remove(RfTask rfTask) {
		try {
			storageLock.writeLock().lock();
			log.debug("remove task {}", rfTask);

			if (storage.containsKey(rfTask.getId())) {
				storage.remove(rfTask.getId());
			} else {
				throw new EntityNotFoundException(rfTask.getClass(), rfTask.getId());
			}
		} finally {
			storageLock.writeLock().unlock();
		}
	}

	@Override
	public Stream<RfTask> getAll() {
		try {
			storageLock.readLock().lock();
			log.debug("get all tasks");
			return storage.values().stream();
		} finally {
			storageLock.readLock().unlock();
		}
	}
}
