package com.digdes.school.controllers;

import com.digdes.school.dto.authenticate.AuthenticationRequest;
import com.digdes.school.dto.authenticate.AuthenticationResponse;
import com.digdes.school.dto.authenticate.RegisterRequest;
import com.digdes.school.security.config.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Контроллер для аутентификации и регистрации")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация",
            description = "Позволяет пользователю зарегестрироваться")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(summary = "Аутентификация",
            description = "Позволяет пользователю пройти аутентификацию")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
