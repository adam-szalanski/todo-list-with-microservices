package org.todolist.backend.security.auth.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static org.todolist.backend.util.ValidationUtility.*;

public record RegistrationRequest(
        @Size(min = 3, max = 50, message = FIRST_NAME_LENGTH_VALIDATION_FAILED)
        String firstName,
        @Size(min = 3, max = 50, message = LAST_NAME_LENGTH_VALIDATION_FAILED)
        String lastName,
        @Email(message = EMAIL_VALIDATION_FAILED)
        @Size(max = 100, message = EMAIL_LENGTH_VALIDATION_FAILED)
        String email,
        @Size(min = 8, max = 32, message = PASSWORD_LENGTH_VALIDATION_FAILED)
        @Pattern(regexp = EIGHT_LETTER_NUMBER_SPECIAL_REGEX, message = PASSWORD_REGEX_VALIDATION_FAILED)
        String password,
        @Size(min = 8, max = 32, message = PASSWORD_LENGTH_VALIDATION_FAILED)
        @Pattern(regexp = EIGHT_LETTER_NUMBER_SPECIAL_REGEX, message = PASSWORD_REGEX_VALIDATION_FAILED)
        String passwordRepeated) {
}
