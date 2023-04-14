package rf.api.dto.users;

import rf.api.dto.BaseOutDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersOutDto extends BaseOutDto {
	private final List<UsersOutDto.User> users = new ArrayList<>();

	public UsersOutDto() {
		super(true);
	}

	public List<UsersOutDto.User> getUsers() {
		return Collections.unmodifiableList(users);
	}

	public void addUser(UsersOutDto.User user) {
		users.add(user);
	}

	public static class User {
		private String username;
		private String role;

		public User(String username, String role) {
			this.username = username;
			this.role = role;
		}

		public User() {
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
}
