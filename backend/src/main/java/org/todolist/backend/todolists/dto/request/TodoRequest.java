package org.todolist.backend.todolists.dto.request;

import java.time.ZonedDateTime;

public record TodoRequest(String taskName, String description, ZonedDateTime deadline, boolean isFinished) {
    // no need for 3 parameter constructor since boolean defaults to false
}
