package rf.back.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rf.domain.entity.RfUser;
import rf.domain.service.RfUserTokenStorage;

@Service
class RfUserTokenStorageImpl implements RfUserTokenStorage {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PasswordEncoder passwordEncoder;

	private final Map<String, RfUser> tokensByUsername = new HashMap<>();
	private final Map<String, RfUser> tokens = new HashMap<>();
	private final ReadWriteLock storageLock = new ReentrantReadWriteLock(true);

	public RfUserTokenStorageImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public RfUser createToken(RfUser rfUser) {
		var newToken = passwordEncoder.encode(UUID.randomUUID().toString());
		var updatedUser = rfUser.withToken(newToken);
		try {
			storageLock.writeLock().lock();
			log.debug("create token for '{}'", rfUser.getUsername());

			var oldToken = tokensByUsername.get(rfUser.getUsername());
			if (oldToken != null) {
				tokens.remove(oldToken.getToken());
			}
			tokensByUsername.put(rfUser.getUsername(), updatedUser);
			tokens.put(newToken, updatedUser);
		} finally {
			storageLock.writeLock().unlock();
		}
		return updatedUser;
	}

	@Override
	public void deleteToken(String token) {
		log.debug("delete token '{}'", token);
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteTokenByUsername(String username) {
		try {
			storageLock.writeLock().lock();
			log.debug("delete token by username '{}'", username);

			Optional.ofNullable(tokensByUsername.get(username)).map(RfUser::getToken).ifPresentOrElse(token -> {
				tokens.remove(token);
				tokensByUsername.remove(username);
			}, () -> {
				throw new UsernameNotFoundException(username);
			});
		} finally {
			storageLock.writeLock().unlock();
		}
	}

	@Override
	public Optional<RfUser> getRfUserByToken(String token) {
		try {
			storageLock.readLock().lock();
			log.debug("get user by token '{}'", token);
			return Optional.ofNullable(tokens.get(token));
		} finally {
			storageLock.readLock().unlock();
		}
	}

	@Override
	public Optional<RfUser> getRfUserByUsername(String username) {
		try {
			storageLock.readLock().lock();
			log.debug("get user by username '{}'", username);
			return Optional.ofNullable(tokensByUsername.get(username));
		} finally {
			storageLock.readLock().unlock();
		}
	}
}
