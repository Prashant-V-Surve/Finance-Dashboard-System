package com.zorvyn.finance_dashboard_system.dto.request;

import com.zorvyn.finance_dashboard_system.enums.RecordType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordRequest {

    @Min(value = 1, message = "Please insert a positive amount")
    private double amount;

    private String description;

    @NotNull(message= "Please give the type of record")
    private RecordType recordType;

    @NotNull(message = "Please enter the category of transaction")
    private String category;

    @NotNull(message = "Date is required")
    private LocalDateTime date;

}
