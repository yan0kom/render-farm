package rf.api.dto.users;

import rf.api.dto.BaseOutDto;

public class SignInOutDto extends BaseOutDto {
	private String username;
	private String role;
	private String token;

	public SignInOutDto(String username, String role, String token) {
		super(true);
		this.username = username;
		this.role = role;
		this.token = token;
	}

	public SignInOutDto() {
		this(null, null, null);
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
