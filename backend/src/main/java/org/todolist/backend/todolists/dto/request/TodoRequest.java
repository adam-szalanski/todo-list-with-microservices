package org.todolist.backend.todolists.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.todolist.backend.util.ValidationUtility.*;

public record TodoRequest(
        @Size(max = 50, message = TASK_NAME_LENGTH_VALIDATION_FAILED)
        String taskName,
        @Size(max = 255, message = TASK_DESCRIPTION_LENGTH_VALIDATION_FAILED)
        String description,
        @Future(message = TASK_DEADLINE_VALIDATION_FAILED)
        LocalDateTime deadline,
        boolean isFinished) {
    // no need for 3 parameter constructor since boolean defaults to false and that's what is intended anyway
}
