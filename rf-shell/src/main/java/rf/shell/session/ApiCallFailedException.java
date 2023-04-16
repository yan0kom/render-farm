package rf.shell.session;

public class ApiCallFailedException extends RuntimeException {
    public ApiCallFailedException(String endPoint, String message) {
        super(String.format("Call to %s failed: %s", endPoint, message));
    }
}
