package com.zorvyn.finance_dashboard_system.controller;

import com.zorvyn.finance_dashboard_system.dto.request.UserRequest;
import com.zorvyn.finance_dashboard_system.dto.response.UserResponse;
import com.zorvyn.finance_dashboard_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

        return ResponseEntity.status(201).body(userService.createUser(userRequest));

    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId){
        return ResponseEntity.status(200).body(userService.getUserById(userId));
    }


    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@Valid@RequestBody UserRequest request){

    }



}
