package com.paysnap.paysnap.service.impls;

import com.paysnap.paysnap.dtos.AuthResponse;
import com.paysnap.paysnap.dtos.LoginDto;
import com.paysnap.paysnap.dtos.RegisterDto;
import com.paysnap.paysnap.dtos.UserResponse;
import com.paysnap.paysnap.enums.Role;
import com.paysnap.paysnap.entity.User;
import com.paysnap.paysnap.repositories.UserRepository;
import com.paysnap.paysnap.security.JwtUtil;
import com.paysnap.paysnap.service.AuthService;
import com.paysnap.paysnap.service.JwtBlacklistService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtBlacklistService blacklistService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, JwtBlacklistService blacklistService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.blacklistService = blacklistService;
    }

    @Override
    public AuthResponse register(RegisterDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(mapToResponse(user))
                .build();
    }

    @Override
    public AuthResponse login(LoginDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(mapToResponse(user))
                .build();
    }

    @Override
    public void logout(String token) {
        blacklistService.blacklist(token, 86400000);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

}
