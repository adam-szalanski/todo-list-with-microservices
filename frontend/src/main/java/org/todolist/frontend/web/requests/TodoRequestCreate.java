package org.todolist.frontend.web.requests;

import java.time.LocalDateTime;

public record TodoRequestCreate(String taskName, String description, LocalDateTime deadline) {
}
