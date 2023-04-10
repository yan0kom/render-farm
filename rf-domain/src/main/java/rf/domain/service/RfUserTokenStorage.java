package rf.domain.service;

import java.util.Optional;

import rf.domain.entity.RfUser;

public interface RfUserTokenStorage {
	RfUser createToken(RfUser rfUser);
	void deleteToken(String token);
	void deleteTokenByUsername(String username);
	Optional<RfUser> getRfUserByToken(String token);
	Optional<RfUser> getRfUserByUsername(String username);
}
