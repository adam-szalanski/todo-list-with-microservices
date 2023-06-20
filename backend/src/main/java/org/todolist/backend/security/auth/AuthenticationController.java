package org.todolist.backend.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.todolist.backend.security.auth.dto.request.AuthenticationRequest;
import org.todolist.backend.security.auth.dto.request.RegistrationRequest;
import org.todolist.backend.security.auth.dto.response.AuthenticationResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public Mono<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return Mono.just(authenticationService.register(registrationRequest));
    }

    @PostMapping("/authenticate")
    public Mono<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return Mono.just(authenticationService.authenticate(authenticationRequest));
    }
}
