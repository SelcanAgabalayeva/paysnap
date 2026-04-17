package com.paysnap.paysnap.controllers;

import com.paysnap.paysnap.dtos.AuthResponse;
import com.paysnap.paysnap.dtos.LoginDto;
import com.paysnap.paysnap.dtos.RegisterDto;
import com.paysnap.paysnap.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterDto request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginDto request) {
        return authService.login(request);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token not found");
        }

        String token = authHeader.substring(7);

        authService.logout(token);
    }
}