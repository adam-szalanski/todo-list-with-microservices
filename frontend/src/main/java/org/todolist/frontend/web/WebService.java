package org.todolist.frontend.web;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.todolist.frontend.web.requests.AuthenticationRequest;
import org.todolist.frontend.web.requests.RegistrationRequest;
import org.todolist.frontend.web.requests.TodoRequestCreate;
import org.todolist.frontend.web.requests.TodoRequestUpdate;
import org.todolist.frontend.web.responses.AuthenticationResponse;
import org.todolist.frontend.web.responses.RequestCountResponse;
import org.todolist.frontend.web.responses.TodoResponse;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WebService {
    public static final String BASE_URL = "http://localhost:8080";
    public static final String REGISTER_URL = "/auth/register";
    public static final String AUTHENTICATE_URL = "/auth/authenticate";
    public static final String COUNT_REQUESTS_URL = "/metrics/requests";
    public static final String TODO_URL = "/todo";
    public static String jwtToken;
    private final RestTemplateBuilder restTemplateBuilder;

    public void register(RegistrationRequest registrationRequest) {
        RestTemplate template = restTemplateBuilder.build();
        AuthenticationResponse response = template.postForObject(BASE_URL + REGISTER_URL, registrationRequest, AuthenticationResponse.class);
        jwtToken = response.token();
    }

    public void login(AuthenticationRequest authenticationRequest) {
        RestTemplate template = restTemplateBuilder.build();
        AuthenticationResponse response = template.postForObject(BASE_URL + AUTHENTICATE_URL, authenticationRequest, AuthenticationResponse.class);
        jwtToken = response.token();
    }

    public List<TodoResponse> getAllTodo() {
        RestTemplate template = getRestTemplateWithToken();
        List<TodoResponse> response = List.of(template.getForObject(BASE_URL + TODO_URL, TodoResponse[].class));
        return response;
    }

    public TodoResponse getOneTodo(Long id) {
        RestTemplate template = getRestTemplateWithToken();
        System.out.println(BASE_URL + TODO_URL +"/" + id);
        TodoResponse response = template.getForObject(BASE_URL + TODO_URL +"/" + id, TodoResponse.class);
        System.out.println(response);
        return response;
    }

    public TodoResponse createTodo(TodoRequestCreate todoRequestCreate) {
        RestTemplate template = getRestTemplateWithToken();
        TodoResponse response = template.postForObject(BASE_URL + TODO_URL, todoRequestCreate, TodoResponse.class);
        return response;
    }

    public TodoResponse updateTodo(Long id, TodoRequestUpdate todoRequestUpdate) {
        RestTemplate template = getRestTemplateWithToken();
        System.out.println(todoRequestUpdate);
        template.put(BASE_URL + TODO_URL +"/" + id, todoRequestUpdate);
        return null;
    }

    public TodoResponse deleteTodo(Long id) {
        RestTemplate template = getRestTemplateWithToken();
        template.delete(BASE_URL + TODO_URL +"/" + id);
        return null;
    }

    public RequestCountResponse getRequestsCount() {
        RestTemplate template = restTemplateBuilder.build();
        return template.getForObject(BASE_URL + COUNT_REQUESTS_URL, RequestCountResponse.class);
    }

    private RestTemplate getRestTemplateWithToken() {
        RestTemplate template = restTemplateBuilder.defaultHeader("Authorization", "Bearer " + jwtToken).build();
        return template;
    }
}
