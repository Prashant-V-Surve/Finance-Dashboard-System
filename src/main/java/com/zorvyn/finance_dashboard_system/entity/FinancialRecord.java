package com.zorvyn.finance_dashboard_system.entity;

import com.zorvyn.finance_dashboard_system.enums.RecordType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "financial_records")
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private RecordType recordType;

    private String category;
    private LocalDateTime date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
