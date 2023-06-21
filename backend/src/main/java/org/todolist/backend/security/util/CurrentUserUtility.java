package org.todolist.backend.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.todolist.backend.exceptions.custom.UserNotLoggedInException;
import org.todolist.backend.security.user.User;
import org.todolist.backend.security.user.UserRepository;

@Component
@RequiredArgsConstructor
public class CurrentUserUtility {
    private final UserRepository userRepository;
    @Value("${application.security.user-not-logged-in-message}")
    private String NOT_LOGGED_IN_MESSAGE;
    @Value("${application.security.user-not-found-message}")
    private String USER_NOT_FOUND_MESSAGE;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            throw new UserNotLoggedInException(NOT_LOGGED_IN_MESSAGE);
        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE));
    }
}
