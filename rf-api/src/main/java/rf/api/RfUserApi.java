package rf.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import rf.api.dto.BaseOutDto;
import rf.api.dto.users.DeleteInDto;
import rf.api.dto.users.SignInInDto;
import rf.api.dto.users.SignInOutDto;
import rf.api.dto.users.SignUpInDto;
import rf.api.dto.users.SignUpOutDto;
import rf.api.dto.users.UsersOutDto;

@RequestMapping("/api/users")
public interface RfUserApi {
	@PostMapping("sign-up")
	@PreAuthorize("(isAnonymous() and #dto.role == 'USER') or hasRole('ADMIN')")
	ResponseEntity<SignUpOutDto> signUp(@Valid @RequestBody SignUpInDto dto);

	@PreAuthorize("isAnonymous()")
	@PostMapping("sign-in")
	ResponseEntity<SignInOutDto> signIn(@Valid @RequestBody SignInInDto dto);

	@PostMapping("sign-out")
	ResponseEntity<BaseOutDto> signOut();

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<UsersOutDto> list();

	@DeleteMapping("delete")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<BaseOutDto> delete(@Valid @RequestBody DeleteInDto dto);
}
