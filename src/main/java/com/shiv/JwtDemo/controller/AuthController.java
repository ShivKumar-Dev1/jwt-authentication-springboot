package com.shiv.JwtDemo.controller;

import com.shiv.JwtDemo.dto.*;
import com.shiv.JwtDemo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(
            @RequestBody SignupRequest request) {

        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}
