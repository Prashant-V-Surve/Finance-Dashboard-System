package com.zorvyn.finance_dashboard_system.dto.request;

import com.zorvyn.finance_dashboard_system.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Please Insert Your Username !")
    private String username;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Please Insert Your Email-ID !")
    private String emailId;

    @NotBlank(message = "Password is required for Authentification !")
    private String password;

    @NotNull(message = "Please Enter Your Role !")
    private RoleType role;
}
