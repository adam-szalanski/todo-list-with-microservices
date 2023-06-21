package org.todolist.backend.util;

public class ValidationUtility {
    public static final String TASK_NAME_LENGTH_VALIDATION_FAILED = "Task name must be up to 50 characters long";
    public static final String TASK_DESCRIPTION_LENGTH_VALIDATION_FAILED = "Task name must be up to 255 characters long";
    public static final String TASK_DEADLINE_VALIDATION_FAILED = "Deadline must be in the future";
    public static final String FIRST_NAME_LENGTH_VALIDATION_FAILED = "First name must be between 3 and 50 characters long";
    public static final String LAST_NAME_LENGTH_VALIDATION_FAILED = "Last name must be between 3 and 50 characters long";
    public static final String EMAIL_VALIDATION_FAILED = "Email is not correctly formatted";
    public static final String EMAIL_LENGTH_VALIDATION_FAILED = "Email must be shorter than 100 characters";
    public static final String PASSWORD_LENGTH_VALIDATION_FAILED = "Password must be between 8 and 32 characters long";
    public static final String PASSWORD_REGEX_VALIDATION_FAILED = "Password must contain at least one letter, one number and one special character";
    public static final String PASSWORD_MATCHES_VALIDATION_FAILED = "Passwords does not match";
    public static final String EIGHT_LETTER_NUMBER_SPECIAL_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\w\\W]{8,}$";
    // Minimum: eight characters, one letter, one number, one special character
}
