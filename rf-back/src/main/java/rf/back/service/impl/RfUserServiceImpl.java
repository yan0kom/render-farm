package rf.back.service.impl;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rf.domain.entity.RfUser;
import rf.domain.repo.RfUserRepo;
import rf.domain.service.RfUserService;
import rf.domain.service.RfUserTokenStorage;

@Service
class RfUserServiceImpl implements RfUserService {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RfUserRepo rfUserRepo;
	private final RfUserTokenStorage rfUserTokenStorage;
	private final PasswordEncoder passwordEncoder;

	public RfUserServiceImpl(RfUserRepo rfUserRepo, RfUserTokenStorage rfUserTokenStorage,
			PasswordEncoder passwordEncoder) {
		this.rfUserRepo = rfUserRepo;
		this.rfUserTokenStorage = rfUserTokenStorage;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void signUp(RfUser rfUser) {
		log.debug("sign-up user '{}' with role '{}'", rfUser.getUsername(), rfUser.getRole());

		rfUserRepo.add(rfUser.withPassword(passwordEncoder.encode(rfUser.getPassword())));
	}

	@Override
	public RfUser signIn(RfUser rfUser) {
		log.debug("sign-in user '{}'", rfUser.getUsername());

		var storedUser =  rfUserRepo.getByUsername(rfUser.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException(rfUser.getUsername()));

		if (!passwordEncoder.matches(rfUser.getPassword(), storedUser.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}

		return rfUserTokenStorage.createToken(storedUser);
	}

	@Override
	public void signOut() {
		var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.debug("sign-out user '{}'", userDetails.getUsername());
		rfUserTokenStorage.deleteTokenByUsername(userDetails.getUsername());
	}

	@Override
	public Stream<RfUser> getAllUsers() {
		log.debug("get all users");
		return rfUserRepo.getAll();
	}

	@Override
	public void deleteUser(RfUser rfUser) {
		log.debug("delete user '{}'", rfUser.getUsername());

		rfUserRepo.remove(rfUser);
	}
}
