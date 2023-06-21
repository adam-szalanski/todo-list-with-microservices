package org.todolist.backend.todolists;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodo() {
        return ResponseEntity.ok(todoListService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getOneTodo(@PathVariable("id") Long id) throws AccessDeniedException {
        return ResponseEntity.ok(todoListService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(todoListService.create(todoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable("id") Long id, @Valid @RequestBody TodoRequest todoRequest) throws AccessDeniedException {
        return ResponseEntity.ok(todoListService.update(id, todoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponse> deleteTodo(@PathVariable("id") Long id) {
        todoListService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
