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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import rf.back.exception.NonUniqueUsernameException;
import rf.domain.entity.RfUser;
import rf.domain.repo.RfUserRepo;
import rf.domain.service.RfUserTokenStorage;

@Repository
class RfUserRepoInMemoryImpl implements RfUserRepo {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final Map<String, RfUser> storage = new HashMap<>();
	private final ReadWriteLock storageLock = new ReentrantReadWriteLock(true);

	private final RfUserTokenStorage rfUserTokenStorage; // used as read cache

	public RfUserRepoInMemoryImpl(RfUserTokenStorage rfUserTokenStorage) {
		this.rfUserTokenStorage = rfUserTokenStorage;
	}

	@PostConstruct
	public void init() {
		add(new RfUser("admin", "$2a$10$TJRmH8GZNCMN5YO0LWX1GO4X1EAce3ntTpXoLYVZEetkHayjMxk5i", "ADMIN", null));
	}

	@Override
	public RfUser add(RfUser rfUser) {
		try {
			storageLock.writeLock().lock();
			log.debug("add user '{}' with role '{}'", rfUser.getUsername(), rfUser.getRole());

			storage.computeIfPresent(rfUser.getUsername(),
					(k, v) -> {
						log.debug("Non unique username '{}'", rfUser.getUsername());
						throw new NonUniqueUsernameException(k);
					});
			storage.put(rfUser.getUsername(), rfUser);
		} finally {
			storageLock.writeLock().unlock();
		}
		return rfUser;
	}

	@Override
	public RfUser update(RfUser rfUser) {
		try {
			storageLock.writeLock().lock();
			log.debug("update user '{}'", rfUser.getUsername());

			Optional.ofNullable(storage.get(rfUser.getUsername()))
					.map(RfUser::getUsername)
					.ifPresentOrElse(username -> storage.put(username, rfUser),
							() -> { throw new UsernameNotFoundException(rfUser.getUsername()); });
		} finally {
			storageLock.writeLock().unlock();
		}
		return rfUser;
	}

	@Override
	public void remove(RfUser rfUser) {
		try {
			storageLock.writeLock().lock();
			log.debug("remove user '{}'", rfUser.getUsername());

			rfUserTokenStorage.getRfUserByUsername(rfUser.getUsername()).ifPresentOrElse(
					(unused) -> {
							rfUserTokenStorage.deleteTokenByUsername(rfUser.getUsername());
							storage.remove(rfUser.getUsername());
						},
					() -> {
						if (storage.containsKey(rfUser.getUsername())) {
							storage.remove(rfUser.getUsername());
						} else {
							throw new UsernameNotFoundException(rfUser.getUsername());
						}
					});
		} finally {
			storageLock.writeLock().unlock();
		}
	}

	@Override
	public Optional<RfUser> getByUsername(String username) {
		try {
			storageLock.readLock().lock();
			log.debug("get user '{}'", username);
			return Optional.ofNullable(
					rfUserTokenStorage.getRfUserByUsername(username).orElseGet(() -> storage.get(username)));
		} finally {
			storageLock.readLock().unlock();
		}
	}

	@Override
	public Stream<RfUser> getAll() {
		try {
			storageLock.readLock().lock();
			log.debug("get all users");
			return storage.values().stream();
		} finally {
			storageLock.readLock().unlock();
		}
	}
}
