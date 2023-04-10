package rf.domain.entity;

public class RfUser {
	private final String username;
	private final String password;
	private final String role;
	private final String token;

	public RfUser(String username, String password, String role, String token) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	public String getToken() {
		return token;
	}

	public RfUser withPassword(String value) {
		return new RfUser(username, value, role, token);
	}
	public RfUser withToken(String value) {
		return new RfUser(username, password, role, value);
	}
}
