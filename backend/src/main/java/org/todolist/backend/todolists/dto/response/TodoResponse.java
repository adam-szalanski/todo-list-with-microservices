package org.todolist.backend.todolists.dto.response;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record TodoResponse(Long id, String taskName, String description, LocalDateTime deadline, Long userId,
                           boolean isFinished) {
}
