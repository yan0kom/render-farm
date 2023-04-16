package rf.shell.session;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import rf.api.dto.users.SignInInDto;
import rf.api.dto.users.SignUpInDto;
import rf.shell.client.RfUserApiClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RfSessionManager {
    private final RfSession session;
    private final RfUserApiClient rfUserApiClient;

    public RfSessionManager(RfSession session, RfUserApiClient rfUserApiClient) {
        this.session = session;
        this.rfUserApiClient = rfUserApiClient;
    }

    public String signIn(String username, String password) {
        ensureNotSignedIn();
        var inDto = new SignInInDto();
        inDto.setUsername(username);
        inDto.setPassword(password);
        var response = rfUserApiClient.signIn(inDto);
        if (response.getStatusCode() == HttpStatus.OK) {
            var outDto = response.getBody();
            if (outDto.getSuccess()) {
                session.signIn(new User(outDto.getUsername(), outDto.getRole()), outDto.getToken());
                return String.format("Signed in as %s", outDto.getUsername());
            }
            return outDto.getError();
        }
        return response.getStatusCode().toString();
    }

    public String signUp(String username, String password, String role) {
        ensureNotSignedInOrAdminSignedIn();
        var inDto = new SignUpInDto();
        inDto.setUsername(username);
        inDto.setPassword(password);
        inDto.setRole(role);
        var response = rfUserApiClient.signUp(inDto);
        if (response.getStatusCode() == HttpStatus.OK) {
            var outDto = response.getBody();
            if (outDto.getSuccess()) {
                return String.format("User %s with role %s registered", outDto.getUsername(), outDto.getRole());
            }
            return outDto.getError();
        }
        return response.getStatusCode().toString();
    }

    public String signOut() {
        ensureSignedIn();
        var response = rfUserApiClient.signOut();
        if (response.getStatusCode() == HttpStatus.OK) {
            var outDto = response.getBody();
            if (outDto.getSuccess()) {
                session.signOut();
                return String.format("Signed out");
            }
            return outDto.getError();
        }
        return response.getStatusCode().toString();
    }

    public List<User> getUsers() {
        ensureAdminSignedIn();
        var response = rfUserApiClient.list();
        if (response.getStatusCode() == HttpStatus.OK) {
            var outDto = response.getBody();
            if (outDto.getSuccess()) {
                return outDto.getUsers().stream()
                        .map(dtoUser -> new User(dtoUser.getUsername(), dtoUser.getRole()))
                        .collect(Collectors.toList());
            }
            throw new ApiCallFailedException("GET /users", outDto.getError());
        }
        throw new ApiCallFailedException("GET /users", response.getStatusCode().name());
    }

    private void ensureSignedIn() {
        if (!session.isSignedIn()) {
            throw new IllegalStateException("You are not signed-in");
        }
    }

    private void ensureAdminSignedIn() {
        if (!session.isSignedIn() || !session.isAdmin()) {
            throw new IllegalStateException("You are not signed-in as administrator");
        }
    }

    private void ensureNotSignedIn() {
        if (session.isSignedIn()) {
            throw new IllegalStateException("You are already signed-in");
        }
    }

    private void ensureNotSignedInOrAdminSignedIn() {
        if (session.isSignedIn() && !session.isAdmin()) {
            throw new IllegalStateException("You are not signed-in as an administrator");
        }
    }
}
