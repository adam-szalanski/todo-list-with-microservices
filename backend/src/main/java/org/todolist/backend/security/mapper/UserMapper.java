package org.todolist.backend.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.todolist.backend.security.auth.dto.request.RegistrationRequest;
import org.todolist.backend.security.user.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "isActive", constant = "false")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "todoList", expression = "java(new java.util.ArrayList<>())")
    public abstract User fromRequest(RegistrationRequest registrationRequest);

}
