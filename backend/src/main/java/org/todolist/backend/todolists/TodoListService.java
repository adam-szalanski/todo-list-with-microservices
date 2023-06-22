package org.todolist.backend.todolists;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.todolist.backend.security.user.User;
import org.todolist.backend.security.util.CurrentUserUtility;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;
import org.todolist.backend.todolists.mapper.TodoMapper;

import java.nio.file.AccessDeniedException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.todolist.backend.todolists.TodoSpecification.*;


@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TodoMapper todoMapper;
    private final CurrentUserUtility currentUserUtility;

    public List<TodoResponse> getAll(String sortField, boolean orderDesc, Boolean finished, ZonedDateTime before, ZonedDateTime after) {
        return todoListRepository.findAll(
                        Specification.allOf(
                                ownedByCurrentUser(currentUserUtility.getCurrentUser()),
                                isFinished(finished),
                                deadlineBefore(before),
                                deadlineAfter(after))
                        , Sort.by(orderDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField)
                )
                .stream()
                .map(todoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TodoResponse getOne(Long id) throws AccessDeniedException {
        TodoListEntity todo = todoListRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!todo.getUser().getUsername().equals(currentUserUtility.getCurrentUser().getUsername()))
            throw new AccessDeniedException(todo.getId().toString());
        return todoMapper.toResponse(todo);
    }

    public TodoResponse create(TodoRequest todoRequest) {
        User user = currentUserUtility.getCurrentUser();
        TodoListEntity todo = todoMapper.fromRequest(todoRequest);
        todo.setUser(user);
        TodoListEntity savedTodo = todoListRepository.save(todo);
        return todoMapper.toResponse(savedTodo);
    }

    public TodoResponse update(Long id, TodoRequest todoRequest) throws AccessDeniedException {
        TodoListEntity todo = todoListRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!todo.getUser().getUsername().equals(currentUserUtility.getCurrentUser().getUsername()))
            throw new AccessDeniedException(todo.getId().toString());
        todo.setTaskName(todoRequest.taskName());
        todo.setDeadline(todoRequest.deadline());
        todo.setDescription(todoRequest.description());
        todo.setFinished(todoRequest.isFinished());
        TodoListEntity savedTodo = todoListRepository.save(todo);
        return todoMapper.toResponse(savedTodo);
    }

    public void delete(Long id) {
        todoListRepository.deleteById(id);
    }

}
