package com.zorvyn.finance_dashboard_system.repository;

import com.zorvyn.finance_dashboard_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
