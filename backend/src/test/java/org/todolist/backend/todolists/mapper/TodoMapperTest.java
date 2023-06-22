package org.todolist.backend.todolists.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.todolist.backend.security.user.Role;
import org.todolist.backend.security.user.User;
import org.todolist.backend.todolists.TodoListEntity;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TodoMapperTest {
    @Autowired
    private TodoMapper todoMapper;

    @Test
    void fromRequest() {
        //given
        TodoRequest todoRequest = new TodoRequest("test", "testDesc", null, true);
        TodoListEntity expectedMappedEntity = new TodoListEntity(null, "test", "testDesc", null, true, null);

        //when
        TodoListEntity outcome = todoMapper.fromRequest(todoRequest);

        //then
        assertEquals(expectedMappedEntity.getId(),outcome.getId());
        assertEquals(expectedMappedEntity.isFinished(),outcome.isFinished());
        assertEquals(expectedMappedEntity.getUser(),outcome.getUser());
        assertEquals(expectedMappedEntity.getDeadline(),outcome.getDeadline());
        assertEquals(expectedMappedEntity.getDescription(),outcome.getDescription());
        assertEquals(expectedMappedEntity.getTaskName(),outcome.getTaskName());
    }

    @Test
    void toResponse() {
        User testUser = new User(1L, "Test", "Testowsky", "test@test.test", "test123", true, Role.ROLE_USER, new ArrayList<>());
        TodoListEntity expectedMappedEntity = new TodoListEntity(1L, "test", "testDesc", null, true, testUser);
        TodoResponse expectedOutcome = new TodoResponse(1L, "test", "testDesc", null, 1L, true);

        //when
        TodoResponse outcome = todoMapper.toResponse(expectedMappedEntity);

        //then
        assertEquals(expectedOutcome.id(),outcome.id());
        assertEquals(expectedOutcome.isFinished(),outcome.isFinished());
        assertEquals(expectedOutcome.userId(),outcome.userId());
        assertEquals(expectedOutcome.deadline(),outcome.deadline());
        assertEquals(expectedOutcome.description(),outcome.description());
        assertEquals(expectedOutcome.taskName(),outcome.taskName());
    }
}