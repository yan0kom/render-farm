package rf.back.security;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
class SecurityFilterChainConfig {
	private final RfTokenAuthFilter rfTokenAuthFilter;

	public SecurityFilterChainConfig(RfTokenAuthFilter rfTokenAuthFilter) {
		this.rfTokenAuthFilter = rfTokenAuthFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		AuthenticationEntryPoint exceptionAep = (request, response, authException) -> response.sendError(
				HttpServletResponse.SC_UNAUTHORIZED,
				authException.getMessage());

		return http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(eh -> eh.authenticationEntryPoint(exceptionAep))
                .authorizeHttpRequests(auth -> auth
                		.antMatchers(HttpMethod.POST, "/api/users/sign-up").permitAll()
                		.antMatchers(HttpMethod.POST, "/api/users/sign-in").permitAll()
                		.anyRequest().authenticated())
                .addFilterBefore(rfTokenAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
	}
}
