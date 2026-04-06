package com.zorvyn.finance_dashboard_system.controller;

import com.zorvyn.finance_dashboard_system.dto.response.DashboardResponse;
import com.zorvyn.finance_dashboard_system.repository.FinancialRecordRepository;
import com.zorvyn.finance_dashboard_system.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/id/{id}")
    public ResponseEntity<DashboardResponse> getDashboard (@PathVariable Long id){

        return ResponseEntity.ok(dashboardService.getDashboardSummary(id));
    }
}
