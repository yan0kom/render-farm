package rf.back.api.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rf.api.dto.ErrorOutDto;
import rf.domain.exception.EntityNotFoundException;
import rf.domain.exception.NonUniqueIdException;
import rf.domain.exception.NonUniqueUsernameException;

import java.util.stream.Collectors;

@RestControllerAdvice("rf.back.api")
class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String msg = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()))
			.collect(Collectors.joining("; "));
		ErrorOutDto validationError = new ErrorOutDto(String.format("Validation error: %s", msg));

		return handleExceptionInternal(ex, validationError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorOutDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		var sb = new StringBuilder();
		sb.append("Username \"");
		sb.append(ex.getMessage());
		sb.append("\" not found");
		return ResponseEntity.ok(new ErrorOutDto(sb.toString()));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorOutDto> handleBadCredentialsException(BadCredentialsException ex) {
		return ResponseEntity.ok(new ErrorOutDto(ex.getMessage()));
	}

	@ExceptionHandler(NonUniqueUsernameException.class)
	public ResponseEntity<ErrorOutDto> handleNonUniqueUsernameException(NonUniqueUsernameException ex) {
		var sb = new StringBuilder();
		sb.append("Non unique username \"");
		sb.append(ex.getMessage());
		sb.append("\"");
		return ResponseEntity.ok(new ErrorOutDto(sb.toString()));
	}

	@ExceptionHandler(NonUniqueIdException.class)
	public ResponseEntity<ErrorOutDto> handleNonUniqueIdException(NonUniqueIdException ex) {
		var sb = new StringBuilder();
		sb.append("Non unique id ");
		sb.append(ex.getMessage());
		return ResponseEntity.ok(new ErrorOutDto(sb.toString()));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorOutDto> handleEntityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.ok(new ErrorOutDto(ex.getMessage()));
	}
}
