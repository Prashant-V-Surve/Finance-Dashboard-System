package com.zorvyn.finance_dashboard_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insights")
public class InsightController {

    @GetMapping
    public String getInsight() {
        return "Insights data (ANALYST + ADMIN)";
    }
}
