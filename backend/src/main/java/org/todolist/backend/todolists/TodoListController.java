package org.todolist.backend.todolists;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest todoRequest){
        return ResponseEntity.ok(todoListService.create(todoRequest));
    }
}
