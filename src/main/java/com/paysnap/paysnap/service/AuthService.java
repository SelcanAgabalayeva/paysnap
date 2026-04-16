package com.paysnap.paysnap.service;

import com.paysnap.paysnap.dtos.AuthResponse;
import com.paysnap.paysnap.dtos.LoginDto;
import com.paysnap.paysnap.dtos.RegisterDto;

public interface AuthService {
    AuthResponse register(RegisterDto request);
    AuthResponse login(LoginDto request);
    void logout(String token);
}
