package rf.api.dto.users;

import rf.api.dto.BaseOutDto;

public class SignInOutDto extends BaseOutDto {
	private final String token;
	private final String role;

	public SignInOutDto(String token, String role) {
		super(true);
		this.token = token;
		this.role = role;
	}

	public String getToken() {
		return token;
	}
	public String getRole() {
		return role;
	}
}
