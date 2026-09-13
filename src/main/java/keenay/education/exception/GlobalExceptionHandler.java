package keenay.education.exception;

import keenay.education.exception.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> createMessage(Throwable ex) {
        Map<String, Object> body = new HashMap<>();

        String message = ex.getMessage();
        if (message == null || message.isEmpty()) {
            message = ex.getClass().getSimpleName() + " occurred";
        }

        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        body.put("exception", ex.getClass().getSimpleName());

        ex.printStackTrace();

        return body;
    }

    @ExceptionHandler({AnimalIsNotSupported.class, PetsNotFoundException.class,
            SkillNotFoundException.class, TicketHasNotBeenCreatedException.class,
            EntityNotFoundException.class})
    public ResponseEntity<Object> handleAnimalIsNotSupportedException(Throwable ex) {
        Map<String, Object> body = createMessage(ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handleAuthorizationExceptionException(AuthorizationException ex) {
        Map<String, Object> body = createMessage(ex);
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Object> handleInternalException(InternalException ex) {
        Map<String, Object> body = createMessage(ex);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoMailFoundException.class)
    public ResponseEntity<Object> handleNoMailFoundException(NoMailFoundException ex) {
        Map<String, Object> body = createMessage(ex);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> body = createMessage(ex);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        Map<String, Object> body = createMessage(ex);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}