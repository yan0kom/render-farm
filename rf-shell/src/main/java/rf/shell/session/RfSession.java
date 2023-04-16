package rf.shell.session;

import org.springframework.stereotype.Component;

@Component
public class RfSession {
    private boolean isSignedIn;
    private User user;
    private String token;

    public void signIn(User user, String token) {
        this.user = user;
        this.token = token;
        this.isSignedIn = true;
    }

    public void signOut() {
        isSignedIn = false;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }
    public User getUser() {
        return user;
    }
    public String getToken() {
        return token;
    }

    public boolean isAdmin() {
        return "ADMIN".equals(user.getRole());
    }
}
