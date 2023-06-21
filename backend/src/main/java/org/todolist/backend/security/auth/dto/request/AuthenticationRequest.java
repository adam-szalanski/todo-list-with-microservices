package org.todolist.backend.security.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static org.todolist.backend.util.ValidationUtility.*;

public record AuthenticationRequest(
        @Email(message = EMAIL_VALIDATION_FAILED) @Size(max = 100, message = EMAIL_LENGTH_VALIDATION_FAILED) String email,
        @Pattern(regexp = EIGHT_LETTER_NUMBER_SPECIAL_REGEX, message = PASSWORD_REGEX_VALIDATION_FAILED) @Size(min = 8, max = 32, message = PASSWORD_LENGTH_VALIDATION_FAILED) String password) {
}
