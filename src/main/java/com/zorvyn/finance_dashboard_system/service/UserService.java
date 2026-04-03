package com.zorvyn.finance_dashboard_system.service;

import com.zorvyn.finance_dashboard_system.dto.request.UserRequest;
import com.zorvyn.finance_dashboard_system.dto.response.UserResponse;
import com.zorvyn.finance_dashboard_system.entity.User;
import com.zorvyn.finance_dashboard_system.mapper.UserMapper;
import com.zorvyn.finance_dashboard_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public ResponseEntity <UserResponse> createUser(UserRequest request) {

        //DTO -> Entity                     requested data -> database entity
        User client = userMapper.toEntity(request);

        //status of user
        client.setActive(true);

        //Saving the data to repository
        User savedUser = userRepository.save(client);

        //Entity -> DTO
        return  ResponseEntity.status(201).body( userMapper.toResponse(savedUser));
    }

    public ResponseEntity <UserResponse> getAllUsers() {

        
    }
}
