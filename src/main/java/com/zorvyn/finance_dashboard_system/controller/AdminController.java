package com.zorvyn.finance_dashboard_system.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/create")
    public String create() {
        return "Created by admin";
    }

    @DeleteMapping("delete/id/{id}")
    public String deleteById(@PathVariable Integer id) {
        return "Deleted by admin";
    }
}
