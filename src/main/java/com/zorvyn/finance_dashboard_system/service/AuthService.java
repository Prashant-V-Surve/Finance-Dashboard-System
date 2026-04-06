package com.zorvyn.finance_dashboard_system.service;

import com.zorvyn.finance_dashboard_system.dto.request.LoginRequest;
import com.zorvyn.finance_dashboard_system.dto.response.LoginResponse;
import com.zorvyn.finance_dashboard_system.entity.User;
import com.zorvyn.finance_dashboard_system.repository.UserRepository;
import com.zorvyn.finance_dashboard_system.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User with email " + loginRequest.getEmail() + " not found!"));

        if(!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);

    }

}
