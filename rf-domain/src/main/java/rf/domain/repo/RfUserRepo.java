package rf.domain.repo;

import java.util.Optional;
import java.util.stream.Stream;

import rf.domain.entity.RfUser;

public interface RfUserRepo {
	RfUser add(RfUser rfUser);
	RfUser update(RfUser rfUser);
	void remove(RfUser rfUser);

	Optional<RfUser> getByUsername(String username);
	Stream<RfUser> getAll();
}
