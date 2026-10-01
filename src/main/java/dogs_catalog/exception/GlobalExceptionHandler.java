package dogs_catalog.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFound( EntityNotFoundException e) {
        log.error("Handle excpetion", e);

        return ResponseEntity
                .status(404)
                .body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> notValid(MethodArgumentNotValidException e) {
        log.error("Handle exception", e);
        return ResponseEntity
                .status(400)
                .body("Invalid data");
    }
    @ExceptionHandler(OwnerDogLimitExceededException.class)
    public ResponseEntity<String> limit(OwnerDogLimitExceededException e) {
        log.error("Handle exception", e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("This owner has a limit for dogs");
    }
}
