package com.zorvyn.finance_dashboard_system.mapper;

import com.zorvyn.finance_dashboard_system.dto.request.UserRequest;
import com.zorvyn.finance_dashboard_system.dto.response.UserResponse;
import com.zorvyn.finance_dashboard_system.entity.User;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest userRequest);

    UserResponse toResponse(User userResponse);
}
