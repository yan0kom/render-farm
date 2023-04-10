package rf.api.dto.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rf.api.dto.BaseOutDto;

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
		private final String username;
		private final String role;

		public User(String username, String role) {
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
}
