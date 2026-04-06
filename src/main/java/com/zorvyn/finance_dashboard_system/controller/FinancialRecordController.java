package com.zorvyn.finance_dashboard_system.controller;

import com.zorvyn.finance_dashboard_system.dto.request.RecordRequest;
import com.zorvyn.finance_dashboard_system.dto.response.RecordResponse;
import com.zorvyn.finance_dashboard_system.enums.RecordType;
import com.zorvyn.finance_dashboard_system.repository.FinancialRecordRepository;
import com.zorvyn.finance_dashboard_system.service.FinancialRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordService financialRecordService;

    //Create
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecordResponse> createRecord(@Valid @RequestBody RecordRequest recordRequest, HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.OK).body(financialRecordService.createRecord(recordRequest, userId));
    }

    //Read : giving all the records of given id
    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ANALYST') or hasRole('ADMIN')")
    public ResponseEntity<List<RecordResponse>> getRecordById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(financialRecordService.getRecordsById(id));
    }

    //Update
    @PutMapping("id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecordResponse> updateRecordById(@PathVariable Long id, @Valid @RequestBody RecordRequest recordRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(financialRecordService.updateRecordById(id,recordRequest));
    }

    //Delete
    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecordResponse> deleteRecordById(@PathVariable Long id) {

        RecordResponse recordResponse = financialRecordService.deleteRecord(id);

        if(recordResponse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recordResponse);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }

    @GetMapping("/id/{id}/category/{category}")
    @PreAuthorize("hasRole('ANALYST') or hasRole('ADMIN')")
    public ResponseEntity<List<RecordResponse>> getByCategory(
            @PathVariable Long id,
            @PathVariable String category) {
        return ResponseEntity.ok(financialRecordService.getRecordsByCategory(id, category));
    }

    // Filter by type (INCOME or EXPENSE)
    @GetMapping("/id/{id}/type/{type}")
    @PreAuthorize("hasRole('ANALYST') or hasRole('ADMIN')")
    public ResponseEntity<List<RecordResponse>> getByType(
            @PathVariable Long id,
            @PathVariable RecordType type) {
        return ResponseEntity.ok(financialRecordService.getRecordsByType(id, type));
    }


}
