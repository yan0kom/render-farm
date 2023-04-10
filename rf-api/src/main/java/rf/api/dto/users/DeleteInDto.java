package rf.api.dto.users;

import javax.validation.constraints.NotNull;

public class DeleteInDto {
	private String username;

	@NotNull
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
