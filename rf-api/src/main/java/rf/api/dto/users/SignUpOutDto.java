package rf.api.dto.users;

import rf.api.dto.BaseOutDto;

public class SignUpOutDto extends BaseOutDto {
	private final String username;
	private final String role;

	public SignUpOutDto(String username, String role) {
		super(true);
		this.username = username;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}
	public String getRole() {
		return role;
	}
}
