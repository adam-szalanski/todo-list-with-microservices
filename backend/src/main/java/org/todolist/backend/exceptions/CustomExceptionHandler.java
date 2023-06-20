package org.todolist.backend.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.todolist.backend.exceptions.custom.UserNotLoggedInException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${application.security.user-already-exists-message}")
    private String USER_ALREADY_EXISTS;
    @Value("${application.security.user-not-found-message}")
    private String USER_NOT_FOUND;
    @Value("${application.security.user-not-logged-in-message}")
    private String USER_NOT_LOGGED_IN;
    @Value("${application.security.entity-not-found-message}")
    private String ENTITY_NOT_FOUND;

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorDetails> handleUserAlreadyExists() {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), USER_ALREADY_EXISTS);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorDetails);
    }
    @ExceptionHandler(UserNotLoggedInException.class)
    protected ResponseEntity<ErrorDetails> handleUserNotLoggedIn() {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), USER_NOT_LOGGED_IN);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorDetails);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleUserNotFound() {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorDetails);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleEntityNotFound() {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorDetails);
    }
}
