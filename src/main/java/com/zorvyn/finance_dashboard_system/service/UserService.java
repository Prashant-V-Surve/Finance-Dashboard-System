package com.zorvyn.finance_dashboard_system.service;

import com.zorvyn.finance_dashboard_system.dto.request.UserRequest;
import com.zorvyn.finance_dashboard_system.dto.response.UserResponse;
import com.zorvyn.finance_dashboard_system.entity.User;
import com.zorvyn.finance_dashboard_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<UserResponse> createUser(UserRequest request) {

        //DTO -> Entity

        User client = new User();
        client.setName(request.getUsername());
        client.setPassword(request.getPassword());
        client.setEmail(request.getEmailId());
        client.setRoleType(request.getRole());

        client.setActive(true);                // recording status of user

        //Save to Database
        User savedUser =  userRepository.save(client);

        //Entity -> DTO
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setUsername(savedUser.getName());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setRoleType(savedUser.getRoleType());

        return ResponseEntity.status(201).body(userResponse);

    }
}
