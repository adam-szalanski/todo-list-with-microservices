package org.todolist.backend.exceptions.errorresponses;

import java.util.Set;

public record MultipleErrorDetails(Integer status, Set<String> Messages) {
}
