package rf.api.dto.users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SignUpInDto {
	private String username;
	private String password;
	private String role;

	@NotNull
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull
	@Pattern(regexp = "USER|ADMIN")
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
