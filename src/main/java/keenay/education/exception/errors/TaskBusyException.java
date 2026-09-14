package keenay.education.exception.errors;

public class TaskBusyException extends RuntimeException {
    public TaskBusyException(String message) {
        super(message);
    }
}
