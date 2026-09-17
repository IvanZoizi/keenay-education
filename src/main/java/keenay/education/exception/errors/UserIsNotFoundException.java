package keenay.education.exception.errors;

public class UserIsNotFoundException extends RuntimeException {
    public UserIsNotFoundException(String message) {
        super(message);
    }
}
