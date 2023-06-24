package org.todolist.frontend.web.requests;

import java.time.LocalDateTime;

public record FilterRequest(LocalDateTime deadlineBefore, LocalDateTime deadlineAfter, String isFinished) {
}
