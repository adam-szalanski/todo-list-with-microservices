package org.todolist.backend.todolists.dto.request;

import java.time.ZonedDateTime;

public record TodoRequest(String taskName, String description, ZonedDateTime deadline) {
}
