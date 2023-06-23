package org.todolist.frontend.web.responses;

import java.time.LocalDateTime;

public record TodoResponse(Long id, String taskName, String description, LocalDateTime deadline, Long userId,
                           boolean isFinished) {
}
