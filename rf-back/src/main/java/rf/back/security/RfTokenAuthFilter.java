package rf.back.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import rf.api.dto.ErrorOutDto;
import rf.domain.service.RfUserDetailsService;

@Component
class RfTokenAuthFilter extends OncePerRequestFilter {
	private final RfUserDetailsService rfUserDetailsService;

	public RfTokenAuthFilter(RfUserDetailsService rfUserDetailsService) {
		super();
		this.rfUserDetailsService = rfUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
				.filter(v -> v.startsWith("Bearer "))
				.map(v -> v.substring(7))
				.map(token -> rfUserDetailsService.loadUserByToken(token))
				.ifPresent(userDetails -> {
					Authentication authentication =	new UsernamePasswordAuthenticationToken(
							userDetails,
							userDetails.getPassword(),
							userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				});

			filterChain.doFilter(request, response);
		} catch (BadCredentialsException ex) {
			var responseEntity = ResponseEntity.ok(new ErrorOutDto(ex.getMessage()));
			var json = new ObjectMapper().writeValueAsString(responseEntity.getBody());
			response.setStatus(responseEntity.getStatusCodeValue());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(json);
			response.flushBuffer();
		}
	}

}
