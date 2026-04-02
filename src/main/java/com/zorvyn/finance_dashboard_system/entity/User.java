package com.zorvyn.finance_dashboard_system.entity;

import com.zorvyn.finance_dashboard_system.enums.RoleType;
import com.zorvyn.finance_dashboard_system.enums.RoleType;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Table(name = "user_details")
public class User {

    @Id                                                            //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)            //Auto incrementing ID
    public long id;

    public String name;

    @Column(unique = true)
    public String email;

    public String password;

    @Enumerated(EnumType.STRING)
    public RoleType RoleType;

    public boolean active = true;

}