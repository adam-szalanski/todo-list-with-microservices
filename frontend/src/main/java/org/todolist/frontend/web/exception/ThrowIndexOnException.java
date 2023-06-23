package org.todolist.frontend.web.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.todolist.frontend.web.WebController;

@ControllerAdvice
@RequiredArgsConstructor
public class ThrowIndexOnException {
    private final WebController webController;
    @ExceptionHandler(Exception.class)
    protected String redirectToIndex(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return webController.index(model);
    }
}
