package com.zorvyn.finance_dashboard_system.service;

import com.zorvyn.finance_dashboard_system.dto.request.UserRequest;
import com.zorvyn.finance_dashboard_system.dto.response.UserResponse;
import com.zorvyn.finance_dashboard_system.entity.User;
import com.zorvyn.finance_dashboard_system.exception.ResourceNotFoundException;
import com.zorvyn.finance_dashboard_system.mapper.UserMapper;
import com.zorvyn.finance_dashboard_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest request) {

        //DTO -> Entity                     requested data -> database entity
        User client = userMapper.toEntity(request);

        //status of user
        client.setActive(true);

        //Saving the data to repository
        User savedUser = userRepository.save(client);

        //Entity -> DTO
        return userMapper.toResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!")));
    }

    public UserResponse updateUser( Long id ,UserRequest request){

        User existingClient = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));

        //UPDATING THE FIELDS MANUALLY
        existingClient.setName(request.getName());
        existingClient.setEmail(request.getEmail());
        existingClient.setPassword(request.getPassword());
        existingClient.setRoleType(request.getRoleType());

        // SAVE UPDATED DATA

        User savedUser = userRepository.save(existingClient);


        return userMapper.toResponse(savedUser);
    }

    public UserResponse deleteUser(Long id){

        User exsistingClient = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));

        userRepository.delete(exsistingClient);

        return userMapper.toResponse(exsistingClient);
    }
}
