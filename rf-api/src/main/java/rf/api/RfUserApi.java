package rf.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rf.api.dto.BaseOutDto;
import rf.api.dto.users.*;

import javax.validation.Valid;

public interface RfUserApi {
	@PostMapping("/users/sign-up")
	@PreAuthorize("(isAnonymous() and #dto.role == 'USER') or hasRole('ADMIN')")
	ResponseEntity<SignUpOutDto> signUp(@Valid @RequestBody SignUpInDto dto);

	@PostMapping(value = "/users/sign-in")
	@PreAuthorize("isAnonymous()")
	ResponseEntity<SignInOutDto> signIn(@Valid @RequestBody SignInInDto dto);

	@PostMapping("/users/sign-out")
	ResponseEntity<BaseOutDto> signOut();

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<UsersOutDto> list();

	@DeleteMapping("/users/delete")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<BaseOutDto> delete(@Valid @RequestBody DeleteInDto dto);
}
