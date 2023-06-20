package org.todolist.backend.security.auth.dto.request;

public record AuthenticationRequest(String email, String password) {
}
