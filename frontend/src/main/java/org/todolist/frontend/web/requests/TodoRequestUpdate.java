package org.todolist.frontend.web.requests;

import java.time.LocalDateTime;

public record TodoRequestUpdate(String taskName, String description, LocalDateTime deadline, boolean isFinished) {
}
