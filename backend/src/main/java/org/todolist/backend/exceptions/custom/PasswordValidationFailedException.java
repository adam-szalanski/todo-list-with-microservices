package org.todolist.backend.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordValidationFailedException extends RuntimeException {
    public PasswordValidationFailedException(String message) {
        super(message);
    }
}
