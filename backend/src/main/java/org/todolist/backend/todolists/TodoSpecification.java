package org.todolist.backend.todolists;

import org.springframework.data.jpa.domain.Specification;
import org.todolist.backend.security.user.User;

import java.time.ZonedDateTime;

public class TodoSpecification {
    public static Specification<TodoListEntity> isFinished(Boolean state) {
        return (root, query, criteriaBuilder) -> {
            if (state == null)
                return criteriaBuilder.isTrue(root.isNotNull());
            return criteriaBuilder.equal(root.get("isFinished"), state);
        };
    }

    public static Specification<TodoListEntity> ownedByCurrentUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("user").get("userId"), user.getUserId()
        );
    }

    public static Specification<TodoListEntity> deadlineBefore(ZonedDateTime date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null)
                return criteriaBuilder.isTrue(root.isNotNull());
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get("deadline"), date
            );
        };
    }

    public static Specification<TodoListEntity> deadlineAfter(ZonedDateTime date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null)
                return criteriaBuilder.isTrue(root.isNotNull());
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get("deadline"), date
            );
        };
    }
}
