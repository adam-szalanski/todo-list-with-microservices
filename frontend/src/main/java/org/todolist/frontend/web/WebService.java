package org.todolist.frontend.web;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.todolist.frontend.web.requests.*;
import org.todolist.frontend.web.responses.AuthenticationResponse;
import org.todolist.frontend.web.responses.RequestCountResponse;
import org.todolist.frontend.web.responses.TodoResponse;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WebService {
    public static final String BASE_URL = "http://localhost:8080";
    public static final String REGISTER_URL = "/auth/register";
    public static final String AUTHENTICATE_URL = "/auth/authenticate";
    public static final String LOGOUT_URL = "/auth/logout";
    public static final String COUNT_REQUESTS_URL = "/metrics/requests";
    public static final String TODO_URL = "/todo";
    public static String jwtToken;
    // this token should be stored in the browser
    // otherwise when one user logs in, someone else accessing this page
    // would also be logged in as this user upon entering
    public static String sortBy = "id";
    public static Boolean orderDesc = false;
    public static String filterFinished = "null";
    public static LocalDateTime filterDateBefore = null;
    public static LocalDateTime filterDateAfter = null;
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
        String sortByParameter = "sortBy=" + sortBy;
        String orderDescParameter = "orderDesc=" + orderDesc;
        String url = BASE_URL + TODO_URL + "?" + sortByParameter + "&" + orderDescParameter;
        if (filterFinished!= null && !"null".equals(filterFinished))
            url+="&finished="+filterFinished;
        if (filterDateBefore!=null)
            url+="&dateBefore="+filterDateBefore;
        if (filterDateAfter!=null)
            url+="&dateAfter="+filterDateAfter;
        return List.of(template.getForObject(url, TodoResponse[].class));
    }

    public TodoResponse getOneTodo(Long id) {
        RestTemplate template = getRestTemplateWithToken();
        TodoResponse response = template.getForObject(BASE_URL + TODO_URL + "/" + id, TodoResponse.class);
        return response;
    }

    public TodoResponse createTodo(TodoRequestCreate todoRequestCreate) {
        RestTemplate template = getRestTemplateWithToken();
        TodoResponse response = template.postForObject(BASE_URL + TODO_URL, todoRequestCreate, TodoResponse.class);
        return response;
    }

    public TodoResponse updateTodo(Long id, TodoRequestUpdate todoRequestUpdate) {
        RestTemplate template = getRestTemplateWithToken();
        template.put(BASE_URL + TODO_URL + "/" + id, todoRequestUpdate);
        return null;
    }

    public TodoResponse deleteTodo(Long id) {
        RestTemplate template = getRestTemplateWithToken();
        template.delete(BASE_URL + TODO_URL + "/" + id);
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

    public void logout() {
        jwtToken = null;
        getRestTemplateWithToken().getForObject(BASE_URL + LOGOUT_URL, Void.class);
    }

    public void sortByDescription() {
        if ("description".equals(sortBy))
            orderDesc = !orderDesc;
        else
            sortBy = "description";
    }

    public void sortByName() {
        if ("taskName".equals(sortBy))
            orderDesc = !orderDesc;
        else
            sortBy = "taskName";
    }

    public void sortByDeadline() {
        if ("deadline".equals(sortBy))
            orderDesc = !orderDesc;
        else
            sortBy = "deadline";
    }

    public void sortByIsFinished() {
        if ("isFinished".equals(sortBy))
            orderDesc = !orderDesc;
        else
            sortBy = "isFinished";
    }

    public void filter(FilterRequest filterRequest) {
        filterFinished = filterRequest.isFinished();
        filterDateBefore = filterRequest.deadlineBefore();
        filterDateAfter = filterRequest.deadlineAfter();
    }
}
