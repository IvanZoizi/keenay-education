package keenay.education.exception.errors;

public class PetsNotFoundException extends RuntimeException {
    public PetsNotFoundException(String message) {
        super(message);
    }
}
