
package com.zorvyn.finance_dashboard_system.service;

import com.zorvyn.finance_dashboard_system.dto.response.DashboardResponse;
import com.zorvyn.finance_dashboard_system.dto.response.RecordResponse;
import com.zorvyn.finance_dashboard_system.entity.FinancialRecord;
import com.zorvyn.finance_dashboard_system.enums.RecordType;
import com.zorvyn.finance_dashboard_system.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository financialRecordRepository;

    public DashboardResponse getDashboardSummary(Long dashboardId) {

        // Fetching all the records from user

        List<FinancialRecord> records = financialRecordRepository.findByUserId(dashboardId);

        double totalIncome = 0;
        double totalExpense = 0;

        //Calculate totals
        for(FinancialRecord record : records) {
            if(record.getRecordType().equals(RecordType.INCOME)) {
                totalIncome = totalIncome + record.getAmount();
            }
            else if(record.getRecordType().equals(RecordType.EXPENSE)) {
                totalExpense = totalExpense + record.getAmount();
            }

        }

        double netBalance = totalIncome - totalExpense;

        // Category Totals
        Map<String, Double> categoryTotals = new HashMap<>();
        List<Object[]> categoryResults = financialRecordRepository.getCategoryTotals(dashboardId);

        for(Object[] row : categoryResults) {
            categoryTotals.put((String) row[0], (Double) row[1]);
        }

        //Recent Activity
        List<RecordResponse> recentActivity = financialRecordRepository
                .findTop5ByUserIdOrderByDateDesc(dashboardId)
                .stream()
                .map(record -> {
                    RecordResponse res = new RecordResponse();
                    res.setId(record.getId());
                    res.setAmount(record.getAmount());
                    res.setCategory(record.getCategory());
                    res.setDate(record.getDate());
                    res.setDescription(record.getDescription());
                    res.setRecordType(record.getRecordType());
                    return res;
                })
                .toList();

        // Monthly Trends
        // Income Trends
        Map<Integer, Double> incomeTrends = new HashMap<>();
        List<Object[]> incomeResults = financialRecordRepository.getMonthlyIncomeTrends(dashboardId);

        for(Object[] row : incomeResults) {
            incomeTrends.put((Integer) row[0], (Double) row[1]);
        }


       // Expense Trends
        Map<Integer, Double> expenseTrends = new HashMap<>();
        List<Object[]> expenseResults = financialRecordRepository.getMonthlyExpenseTrends(dashboardId);

        for(Object[] row : expenseResults) {
            expenseTrends.put((Integer) row[0], (Double) row[1]);
        }

        // Prepare Response
        DashboardResponse response = new DashboardResponse();

        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setNetBalance(netBalance);

        response.setCategoryTotals(categoryTotals);
        response.setRecentActivity(recentActivity);
        response.setIncomeTrends(incomeTrends);
        response.setExpenseTrends(expenseTrends);

        return response;

    }


}
