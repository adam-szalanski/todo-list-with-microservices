package org.todolist.frontend.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.todolist.frontend.web.requests.AuthenticationRequest;
import org.todolist.frontend.web.requests.RegistrationRequest;
import org.todolist.frontend.web.requests.TodoRequestCreate;
import org.todolist.frontend.web.requests.TodoRequestUpdate;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final WebService webService;

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        if (WebService.jwtToken != null) {
            model.addAttribute("todoList", webService.getAllTodo());
        }
        model.addAttribute("requestCount", webService.getRequestsCount().count() + 1);
        return "index";
    }

    @GetMapping("/register")
    String register(Model model) {
        return "register";
    }

    @RequestMapping("/register")
    String register(@ModelAttribute RegistrationRequest registrationRequest, Model model) {
        model.addAttribute("loggedIn", "true");
        webService.register(registrationRequest);
        return index(model);
    }

    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }

    @RequestMapping("/login")
    String login(@ModelAttribute AuthenticationRequest authenticationRequest, Model model) {
        model.addAttribute("loggedIn", "true");
        webService.login(authenticationRequest);
        return index(model);
    }

    @RequestMapping("/addTodo")
    String addTodo(@ModelAttribute TodoRequestCreate todoRequestCreate, Model model) {
        model.addAttribute("loggedIn", "true");
        webService.createTodo(todoRequestCreate);
        return index(model);
    }

    @GetMapping("/edit/{id}")
    String editTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editedTask", webService.getOneTodo(id));
        return "edit";
    }

    @RequestMapping("/edit/{id}")
    String editTask(@ModelAttribute TodoRequestUpdate todoRequestUpdate, @PathVariable("id") Long id, Model model) {
        model.addAttribute("loggedIn", "true");
        webService.updateTodo(id, todoRequestUpdate);
        return index(model);
    }
    @GetMapping("/delete/{id}")
    String deleteTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("deletedTask", webService.getOneTodo(id));
        return "delete";
    }

    @RequestMapping("/delete/{id}")
    String confirmDeleteTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("loggedIn", "true");
        webService.deleteTodo(id);
        return index(model);
    }
}
