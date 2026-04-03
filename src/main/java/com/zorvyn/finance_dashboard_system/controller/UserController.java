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

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // C : Create (POST)
    @PostMapping
    public UserResponse createUser(@Valid@RequestBody UserRequest userRequest){

        return userService.createUser(userRequest).getBody();

    }

    public UserResponse getUserById(@RequestParam long id)
    {

    }


}
