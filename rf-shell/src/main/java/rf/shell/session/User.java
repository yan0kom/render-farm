package rf.shell.session;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {
    private final String username;
    private final String role;
}
