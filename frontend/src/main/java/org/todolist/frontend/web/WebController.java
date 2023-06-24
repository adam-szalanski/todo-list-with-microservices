package org.todolist.frontend.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.todolist.frontend.web.requests.*;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final WebService webService;

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        if (WebService.jwtToken != null) {
            model.addAttribute("loggedIn", "true");
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
        webService.register(registrationRequest);
        return index(model);
    }

    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }

    @RequestMapping("/login")
    String login(@ModelAttribute AuthenticationRequest authenticationRequest, Model model) {
        webService.login(authenticationRequest);
        return index(model);
    }

    @RequestMapping("/logout")
    String logout(Model model) {
        webService.logout();
        return index(model);
    }

    @RequestMapping("/addTodo")
    String addTodo(@ModelAttribute TodoRequestCreate todoRequestCreate, Model model) {
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
        webService.deleteTodo(id);
        return index(model);
    }

    @GetMapping("/sortByName")
    String sortByName(Model model) {
        webService.sortByName();
        return index(model);
    }

    @GetMapping("sortByDescription")
    String sortByDescription(Model model) {
        webService.sortByDescription();
        return index(model);
    }

    @GetMapping("sortByDeadline")
    String sortByDeadline(Model model) {
        webService.sortByDeadline();
        return index(model);
    }

    @GetMapping("sortByIsFinished")
    String sortByIsFinished(Model model) {
        webService.sortByIsFinished();
        return index(model);
    }

    @RequestMapping("filter")
    String filter(@ModelAttribute FilterRequest filterRequest, Model model) {
        webService.filter(filterRequest);
        return index(model);
    }
}
