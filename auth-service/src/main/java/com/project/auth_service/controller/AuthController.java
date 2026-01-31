package com.project.auth_service.controller;

import com.project.auth_service.dto.AuthResponse;
import com.project.auth_service.dto.LoginRequest;
import com.project.auth_service.dto.SignupRequest;
import com.project.auth_service.security.JwtUtil;
import com.project.auth_service.service.AuthService;
import com.project.auth_service.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {

        authService.signup(request);
        return ResponseEntity.ok(new AuthResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        return ResponseEntity.ok(jwtUtil.generateToken(email, "ROLE_USER"));
    }


}
