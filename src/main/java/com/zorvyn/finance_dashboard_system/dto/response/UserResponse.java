package com.zorvyn.finance_dashboard_system.dto.response;

import com.zorvyn.finance_dashboard_system.enums.RoleType;
import lombok.Data;

@Data
public class UserResponse {

    private long id;
    private String username;
    private String email;
    private RoleType roleType;
}
