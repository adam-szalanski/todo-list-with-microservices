package org.todolist.frontend.web.requests;


public record RegistrationRequest(String firstName, String lastName, String email, String password,
                                  String passwordRepeated) {
}
