package rf.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RfUserDetailsService extends UserDetailsService {
	UserDetails loadUserByToken(String token);
}