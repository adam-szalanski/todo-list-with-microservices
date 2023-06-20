package org.todolist.backend.security.auth.dto.request;

public record RegistrationRequest(String firstName, String lastName, String email, String password) {
}
