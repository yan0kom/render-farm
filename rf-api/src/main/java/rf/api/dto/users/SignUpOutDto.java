package rf.api.dto.users;

import rf.api.dto.BaseOutDto;

public class SignUpOutDto extends BaseOutDto {
	private String username;
	private String role;

	public SignUpOutDto(String username, String role) {
		super(true);
		this.username = username;
		this.role = role;
	}

	public SignUpOutDto() {
		this(null, null);
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
}
