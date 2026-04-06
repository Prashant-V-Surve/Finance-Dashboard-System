package com.zorvyn.finance_dashboard_system.repository;

import com.zorvyn.finance_dashboard_system.entity.FinancialRecord;
import com.zorvyn.finance_dashboard_system.enums.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByUserId(Long user_Id);

    List<FinancialRecord> findByUserIdAndCategory(Long userId, String category);

    List<FinancialRecord> findByUserIdAndRecordType(Long userId, RecordType type);

    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r WHERE r.user.id = :userId GROUP BY r.category")
    List<Object[]> getCategoryTotals(Long userId);


    List<FinancialRecord> findTop5ByUserIdOrderByDateDesc(Long userId);


    @Query("SELECT FUNCTION('MONTH', r.date), SUM(r.amount) " +
            "FROM FinancialRecord r WHERE r.user.id = :userId AND r.recordType = 'INCOME' " +
            "GROUP BY FUNCTION('MONTH', r.date)")
    List<Object[]> getMonthlyIncomeTrends(Long userId);


    @Query("SELECT FUNCTION('MONTH', r.date), SUM(r.amount) " +
            "FROM FinancialRecord r WHERE r.user.id = :userId AND r.recordType = 'EXPENSE' " +
            "GROUP BY FUNCTION('MONTH', r.date)")
    List<Object[]> getMonthlyExpenseTrends(Long userId);
}
