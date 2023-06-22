package org.todolist.backend.todolists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TodoListRepository extends JpaRepository<TodoListEntity, Long>, JpaSpecificationExecutor<TodoListEntity> {
}
