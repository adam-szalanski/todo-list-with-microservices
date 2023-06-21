package org.todolist.backend.todolists.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.todolist.backend.todolists.TodoListEntity;
import org.todolist.backend.todolists.dto.request.TodoRequest;
import org.todolist.backend.todolists.dto.response.TodoResponse;

@Mapper(componentModel = "spring")
public abstract class TodoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taskName", source = "taskName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "finished", source = "isFinished")
    @Mapping(target = "user", ignore = true)
    public abstract TodoListEntity fromRequest(TodoRequest todoRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "taskName", source = "taskName")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "isFinished", source = "finished")
    @Mapping(target = "userId", source = "user.userId")
    public abstract TodoResponse toResponse(TodoListEntity todoListEntity);

}
