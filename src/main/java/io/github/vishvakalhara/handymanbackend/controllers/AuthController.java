package io.github.vishvakalhara.handymanbackend.controllers;

import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.AuthResponse;
import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.LoginRequest;
import io.github.vishvakalhara.handymanbackend.domains.dtos.auth.RegisterRequest;
import io.github.vishvakalhara.handymanbackend.domains.entities.User;
import io.github.vishvakalhara.handymanbackend.mappers.UserMapper;
import io.github.vishvakalhara.handymanbackend.services.AuthService;
import io.github.vishvakalhara.handymanbackend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest requestBody){

        // Map the Request body to User
        User user = userMapper.registerRequestToEntity(requestBody);

        // Create new User
        authService.createUser(user);

        // Generate JWT
        UserDetails userDetails = authService.authenticate(requestBody.getEmail(), requestBody.getPassword());
        String token = authService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest requestBody){

        UserDetails userDetails = authService.authenticate(requestBody.getEmail(), requestBody.getPassword());
        String token = authService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}