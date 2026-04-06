package com.zorvyn.finance_dashboard_system.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DashboardResponse {

    private double totalIncome;
    private double totalExpense;
    private double netBalance;

    private Map<String, Double> categoryTotals;
    private List<RecordResponse> recentActivity;
    private Map<Integer, Double> incomeTrends;
    private Map<Integer, Double> expenseTrends;
}
