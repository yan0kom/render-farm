package rf.domain.service;

import java.util.stream.Stream;

import rf.domain.entity.RfUser;

public interface RfUserService {
	void signUp(RfUser rfUser);
	RfUser signIn(RfUser rfUser);
	void signOut();

	Stream<RfUser> getAllUsers();

	void deleteUser(RfUser rfUser);
}
