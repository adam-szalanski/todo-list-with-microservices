package org.todolist.backend.todolists;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodo(@RequestParam(value = "finished", required = false) Boolean finished,
                                                         @RequestParam(value = "dateBefore", required = false) LocalDateTime before,
                                                         @RequestParam(value = "dateAfter", required = false) LocalDateTime after,
                                                         @RequestParam(value = "sortBy", required = false) String sortField,
                                                         @RequestParam(value = "orderDesc", required = false) boolean orderDesc) {
        return ResponseEntity.ok(todoListService.getAll(sortField, orderDesc, finished, before, after));
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
