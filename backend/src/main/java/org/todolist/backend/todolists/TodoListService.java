package org.todolist.backend.todolists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.todolist.backend.security.user.User;
import org.todolist.backend.security.util.CurrentUserUtility;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;
import org.todolist.backend.todolists.mapper.TodoMapper;


@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoMapper todoMapper;
    private final CurrentUserUtility currentUserUtility;

    public TodoResponse create(TodoRequest todoRequest) {
        User user =  currentUserUtility.getCurrentUser();
        TodoListEntity todo = todoMapper.fromRequest(todoRequest);
        todo.setUser(user);
        TodoListEntity savedTodo = todoListRepository.save(todo);
        return todoMapper.toResponse(savedTodo);
    }
}
