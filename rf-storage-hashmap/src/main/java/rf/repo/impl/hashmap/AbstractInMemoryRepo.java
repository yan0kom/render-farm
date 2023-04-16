package rf.repo.impl.hashmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rf.domain.entity.EntityWithId;
import rf.domain.exception.EntityNotFoundException;
import rf.domain.exception.NonUniqueIdException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

class AbstractInMemoryRepo<T extends EntityWithId<T>> {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	private final Map<Long, T> storage = new HashMap<>();
	private Long storageNextId = 0L;
	private final ReadWriteLock storageLock = new ReentrantReadWriteLock(true);

	private Long nextId() {
		return ++storageNextId;
	}

	public T add(T entity) {
		if (entity.getId() > 0) {
			throw new IllegalArgumentException();
		}
		try {
			storageLock.writeLock().lock();

			var id = nextId();
			log.debug("add entity {} using id {}", entity, id);

			storage.computeIfPresent(id,
					(k, v) -> {
						log.debug("Non unique id '{}'", id);
						throw new NonUniqueIdException(entity.toString());
					});
			storage.put(id, entity);
			return entity.withId(id);
		} finally {
			storageLock.writeLock().unlock();
		}
	}

	public T set(T entity) {
		try {
			storageLock.writeLock().lock();
			log.debug("set entity {}", entity);

			Optional.ofNullable(storage.get(entity.getId()))
					.ifPresentOrElse(
							unused -> storage.put(entity.getId(), entity),
							() -> {
								log.debug("Entity with id '{}' not found", entity.getId());
								throw new EntityNotFoundException(entity.getClass(), entity.getId());
							});
		} finally {
			storageLock.writeLock().unlock();
		}
		return entity;
	}

	public void remove(T entity) {
		try {
			storageLock.writeLock().lock();
			log.debug("remove entity {}", entity);

			if (storage.containsKey(entity.getId())) {
				storage.remove(entity.getId());
			} else {
				log.debug("Entity with id '{}' not found", entity.getId());
				throw new EntityNotFoundException(entity.getClass(), entity.getId());
			}
		} finally {
			storageLock.writeLock().unlock();
		}
	}

	public Optional<T> getById(Long id) {
		try {
			storageLock.readLock().lock();
			log.debug("get entity {}", id);

			return Optional.ofNullable(storage.get(id));
		} finally {
			storageLock.readLock().unlock();
		}
	}

	public Stream<T> getAll() {
		try {
			storageLock.readLock().lock();
			log.debug("get all entities");

			return storage.values().stream();
		} finally {
			storageLock.readLock().unlock();
		}
	}
}
