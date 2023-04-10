package rf.back.service.impl;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rf.domain.entity.RfUser;
import rf.domain.repo.RfUserRepo;
import rf.domain.service.RfUserDetailsService;
import rf.domain.service.RfUserTokenStorage;

@Service
class RfUserDetailsServiceImpl implements RfUserDetailsService {
	private final RfUserRepo rfUserRepo;
	private final RfUserTokenStorage rfUserTokenStorage;

	public RfUserDetailsServiceImpl(RfUserRepo rfUserRepo, RfUserTokenStorage rfUserTokenStorage) {
		this.rfUserRepo = rfUserRepo;
		this.rfUserTokenStorage = rfUserTokenStorage;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return rfUserRepo.getByUsername(username)
			.map(RfUserDetailsServiceImpl::rfUserToUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	@Override
	public UserDetails loadUserByToken(String token) {
		return rfUserTokenStorage.getRfUserByToken(token)
			.map(RfUserDetailsServiceImpl::rfUserToUserDetails)
			.orElseThrow(() -> new BadCredentialsException("Invalid token"));
	}

	private static UserDetails rfUserToUserDetails(RfUser rfUser) {
		if (rfUser.getRole() == null || rfUser.getRole().isEmpty()) {
			throw new UsernameNotFoundException(rfUser.getUsername());
		}
		return new User(
				rfUser.getUsername(),
				rfUser.getPassword(),
				List.of(new SimpleGrantedAuthority("ROLE_"+rfUser.getRole())));
	}

}
