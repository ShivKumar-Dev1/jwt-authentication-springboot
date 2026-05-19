package com.shiv.JwtDemo.service;

import com.shiv.JwtDemo.dto.*;
import com.shiv.JwtDemo.entity.User;
import com.shiv.JwtDemo.repository.UserRepository;
import com.shiv.JwtDemo.JwtUtil.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String signup(SignupRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Encrypt Password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        boolean isPasswordCorrect =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!isPasswordCorrect) {
            throw new RuntimeException("Invalid Password");
        }

        String token =
                jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}