package org.todolist.backend.todolists;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.todolist.backend.security.user.Role;
import org.todolist.backend.security.user.User;
import org.todolist.backend.security.util.CurrentUserUtility;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;
import org.todolist.backend.todolists.mapper.TodoMapper;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoListServiceTest {
    @InjectMocks
    private TodoListService todoListService;
    @Mock
    private TodoListRepository todoListRepository;
    @Mock
    private TodoMapper todoMapper;
    @Mock
    private CurrentUserUtility currentUserUtility;

    @Test
    void create() {
        //given
        TodoRequest todoRequest = new TodoRequest("test", "testDesc", null, true);
        TodoListEntity expectedMappedEntity = new TodoListEntity(1L, "test", "testDesc", null, true, new User());
        TodoResponse expectedOutcome = new TodoResponse(1L, "test", "testDesc", null, 1L, true);
        given(todoMapper.fromRequest(todoRequest)).willReturn(expectedMappedEntity);
        given(todoMapper.toResponse(expectedMappedEntity)).willReturn(expectedOutcome);
        given(todoListRepository.save(expectedMappedEntity)).willReturn(expectedMappedEntity);

        //when
        Object outcome = todoListService.create(todoRequest);

        //then
        assertEquals(expectedOutcome, outcome);
        verify(todoMapper, times(1)).fromRequest(any());
        verify(todoMapper, times(1)).toResponse(any());
        verify(todoListRepository, times(1)).save(any());
    }

    @Test
    void update_worksCorrectly() throws AccessDeniedException {
        //given
        User testUser = new User(1L, "Test", "Testowsky", "test@test.test", "test123", true, Role.ROLE_USER, new ArrayList<>());
        TodoRequest todoRequest = new TodoRequest("test", "testDesc", null, true);
        TodoListEntity entityFromRepository = new TodoListEntity(1L, "test123", "Tested description", null, false, testUser);
        TodoListEntity expectedEntity = new TodoListEntity(1L, "test", "testDesc", null, true, testUser);
        TodoResponse expectedOutcome = new TodoResponse(1L, "test", "testDesc", null, testUser.getUserId(), true);
        given(todoListRepository.findById(any())).willReturn(Optional.of(entityFromRepository));
        given(currentUserUtility.getCurrentUser()).willReturn(testUser);
        given(todoListRepository.save(entityFromRepository)).willReturn(expectedEntity);
        given(todoMapper.toResponse(expectedEntity)).willReturn(expectedOutcome);

        //when
        TodoResponse outcome = todoListService.update(1L, todoRequest);

        //then
        assertEquals(expectedOutcome.taskName(), outcome.taskName());
        assertEquals(expectedOutcome.description(), outcome.description());
        assertEquals(expectedOutcome.deadline(), outcome.deadline());
        assertEquals(expectedOutcome.isFinished(), outcome.isFinished());
        verify(todoMapper, times(1)).toResponse(any());
        verify(todoListRepository, times(1)).save(any());
    }

    @Test
    void update_throwsAccessDenied() {
        //given
        User testUser = new User(1L, "Test", "Testowsky", "test@test.test", "test123", true, Role.ROLE_USER, new ArrayList<>());
        TodoRequest todoRequest = new TodoRequest("test", "testDesc", null, true);
        TodoListEntity entityFromRepository = new TodoListEntity(1L, "test123", "Tested description", null, false, new User(1L, "Test123", "Testowsky123", "test111@test111.test", "test123", true, Role.ROLE_USER, new ArrayList<>()));
        given(todoListRepository.findById(any())).willReturn(Optional.of(entityFromRepository));
        given(currentUserUtility.getCurrentUser()).willReturn(testUser);

        //throws
        assertThrows(AccessDeniedException.class, () -> todoListService.update(1L, todoRequest));
    }

    @Test
    void update_throwsEntityNotFound() {
        //given
        given(todoListRepository.findById(any())).willReturn(Optional.empty());

        //throws
        assertThrows(EntityNotFoundException.class, () -> todoListService.update(1L, null));
    }
}