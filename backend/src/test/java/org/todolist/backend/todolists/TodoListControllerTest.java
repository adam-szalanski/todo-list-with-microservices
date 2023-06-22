package org.todolist.backend.todolists;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoListControllerTest {

    @InjectMocks
    private TodoListController todoListController;
    @Mock
    private TodoListService todoListService;

    @Test
    void createTodo() {
        //given
        TodoResponse expectedReturn = new TodoResponse(1L, "test", "TEST_DESCRIPTION", null, null, true);
        given(todoListService.create(any())).willReturn(expectedReturn);

        //when
        Object outcome = todoListController.createTodo(new TodoRequest(null, null, null, true));

        //then
        assertEquals(ResponseEntity.class, outcome.getClass());
        assertEquals(ResponseEntity.ok(expectedReturn), outcome);
        verify(todoListService, times(1)).create(any());
    }

    @Test
    void updateTodo() throws AccessDeniedException {
        //given
        TodoResponse expectedReturn = new TodoResponse(1L, "test", "TEST_DESCRIPTION", null, null, true);
        given(todoListService.update(any(), any())).willReturn(expectedReturn);

        //when
        Object outcome = todoListController.updateTodo(1L, new TodoRequest(null, null, null, true));

        //then
        assertEquals(ResponseEntity.class, outcome.getClass());
        assertEquals(ResponseEntity.ok(expectedReturn), outcome);
        verify(todoListService, times(1)).update(any(), any());
    }
}