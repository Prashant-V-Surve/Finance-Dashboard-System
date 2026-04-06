package com.zorvyn.finance_dashboard_system.dto.response;

import com.zorvyn.finance_dashboard_system.enums.RecordType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordResponse {

    private Long id;
    private double amount;
    private String description;
    private RecordType recordType;
    private String category;
    private LocalDateTime date;
}
