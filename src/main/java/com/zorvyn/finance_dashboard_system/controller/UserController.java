package com.zorvyn.finance_dashboard_system.controller;

import com.zorvyn.finance_dashboard_system.dto.request.UserRequest;
import com.zorvyn.finance_dashboard_system.dto.response.UserResponse;
import com.zorvyn.finance_dashboard_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // C : Create (POST)
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid@RequestBody UserRequest userRequest){

        UserResponse clientResponse = userService.createUser(userRequest);
        return ResponseEntity.status(201).body(clientResponse);

    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        List<UserResponse> clientResponseList = userService.getAllUsers();
        return ResponseEntity.status(200).body(clientResponseList);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId){

        UserResponse clientResponse = userService.getUserById(userId);
        return ResponseEntity.status(200).body(clientResponse);
    }

    @PutMapping("/id/{userId}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long userId,@Valid@RequestBody UserRequest userRequest){

        UserResponse clientResponseUp = userService.updateUser(userId,userRequest);

        return ResponseEntity.status(HttpStatus.OK).body(clientResponseUp);
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long userId){

        UserResponse clientResponse = userService.deleteUser(userId);

        if (clientResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }






}
