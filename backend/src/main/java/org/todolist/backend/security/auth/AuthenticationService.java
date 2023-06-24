package org.todolist.backend.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.todolist.backend.exceptions.custom.PasswordValidationFailedException;
import org.todolist.backend.exceptions.custom.UserAlreadyRegisteredException;
import org.todolist.backend.security.auth.dto.request.AuthenticationRequest;
import org.todolist.backend.security.auth.dto.request.RegistrationRequest;
import org.todolist.backend.security.auth.dto.response.AuthenticationResponse;
import org.todolist.backend.security.config.JwtService;
import org.todolist.backend.security.mapper.UserMapper;
import org.todolist.backend.security.user.User;
import org.todolist.backend.security.user.UserRepository;

import static org.todolist.backend.util.ValidationUtility.PASSWORD_MATCHES_VALIDATION_FAILED;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserMapper userMapper;
    @Autowired
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Value("${application.security.user-not-found-message}")
    private String USER_NOT_FOUND_MESSAGE;
    @Value("${application.security.user-already-exists-message}")
    private String USER_ALREADY_EXISTS_MESSAGE;

    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        if (!registrationRequest.password().equals(registrationRequest.passwordRepeated()))
            throw new PasswordValidationFailedException(PASSWORD_MATCHES_VALIDATION_FAILED);
        if (userRepository.existsByEmail(registrationRequest.email()))
            throw new UserAlreadyRegisteredException(USER_ALREADY_EXISTS_MESSAGE);
        User user = userMapper.fromRequest(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        user.setIsActive(true); // normally this line would not exist and user would require email confirmation or other activation method
        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(savedUser);
        return new AuthenticationResponse(jwtToken);

    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );
        User user = userRepository.findByEmail(authenticationRequest.email()).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE));
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

}
