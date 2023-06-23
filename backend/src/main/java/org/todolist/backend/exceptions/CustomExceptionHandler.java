package org.todolist.backend.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.todolist.backend.exceptions.custom.PasswordValidationFailedException;
import org.todolist.backend.exceptions.custom.UserAlreadyRegisteredException;
import org.todolist.backend.exceptions.custom.UserNotLoggedInException;
import org.todolist.backend.exceptions.errorresponses.ErrorDetails;
import org.todolist.backend.exceptions.errorresponses.MultipleErrorDetails;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.Set;

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
    @Value("${application.security.access-denied-message}")
    private String ACCESS_DENIED;
    @Value("${application.security.validation-error-message}")
    private String DEFAULT_VALIDATION_ERROR;

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    protected ResponseEntity<ErrorDetails> handleUserAlreadyExists() {
        return createErrorResponse(HttpStatus.BAD_REQUEST, USER_ALREADY_EXISTS);
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    protected ResponseEntity<ErrorDetails> handleUserNotLoggedIn() {
        return createErrorResponse(HttpStatus.BAD_REQUEST, USER_NOT_LOGGED_IN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleUserNotFound() {
        return createErrorResponse(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleEntityNotFound() {
        return createErrorResponse(HttpStatus.NOT_FOUND, ENTITY_NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorDetails> handleAccessDenied() {
        return createErrorResponse(HttpStatus.UNAUTHORIZED, ACCESS_DENIED);
    }

    @ExceptionHandler(PasswordValidationFailedException.class)
    protected ResponseEntity<ErrorDetails> handlePasswordValidation(PasswordValidationFailedException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ErrorDetails> handleValidation(ValidationException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorDetails> handleIllegalArgument(IllegalArgumentException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<MultipleErrorDetails> handleConstraintValidation(ConstraintViolationException e) {
        Set<String> errorMessages = new HashSet<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations())
            errorMessages.add(constraintViolation.getMessage());
        MultipleErrorDetails errorDetails = new MultipleErrorDetails(HttpStatus.BAD_REQUEST.value(), errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorDetails);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Set<String> errorMessages = new HashSet<>();
        for (ObjectError error : ex.getAllErrors())
            errorMessages.add(error.getDefaultMessage());
        MultipleErrorDetails errorDetails = new MultipleErrorDetails(HttpStatus.BAD_REQUEST.value(), errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).contentType(MediaType.APPLICATION_JSON).body(errorDetails);
    }

    private ResponseEntity<ErrorDetails> createErrorResponse(HttpStatus status, Exception e) {
        ErrorDetails errorDetails = new ErrorDetails(status.value(), e.getMessage());
        return ResponseEntity.status(status.value()).body(errorDetails);
    }

    private ResponseEntity<ErrorDetails> createErrorResponse(HttpStatus status, String message) {
        ErrorDetails errorDetails = new ErrorDetails(status.value(), message);
        return ResponseEntity.status(status.value()).body(errorDetails);
    }
}
