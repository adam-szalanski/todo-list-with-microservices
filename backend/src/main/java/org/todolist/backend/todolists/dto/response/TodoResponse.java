package org.todolist.backend.todolists.dto.response;

import java.time.ZonedDateTime;

public record TodoResponse(Long id, String taskName, String description, ZonedDateTime deadline, Long userId,
                           boolean isFinished) {
}
