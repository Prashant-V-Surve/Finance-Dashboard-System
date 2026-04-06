package com.zorvyn.finance_dashboard_system.service;

import com.zorvyn.finance_dashboard_system.dto.request.RecordRequest;
import com.zorvyn.finance_dashboard_system.dto.response.RecordResponse;
import com.zorvyn.finance_dashboard_system.entity.FinancialRecord;
import com.zorvyn.finance_dashboard_system.entity.User;
import com.zorvyn.finance_dashboard_system.enums.RecordType;
import com.zorvyn.finance_dashboard_system.mapper.FinancialRecordMapper;
import com.zorvyn.finance_dashboard_system.repository.FinancialRecordRepository;
import com.zorvyn.finance_dashboard_system.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {

    private final FinancialRecordRepository financialRecordRepository;
    private final UserRepository userRepository;
    private final FinancialRecordMapper financialRecordMapper;

    //Create
    public RecordResponse createRecord(@Valid RecordRequest recordRequest, Long userId) {

        // MapStruct mapping
        FinancialRecord record = financialRecordMapper.toEntity(recordRequest);

        //Fetch User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //3.Set User
        record.setUser(user);

        //4.Set Date
        record.setDate(LocalDateTime.now());

        // Save
        FinancialRecord savedRecord = financialRecordRepository.save(record);

        //Return Response
        return financialRecordMapper.toResponse(savedRecord);
    }

    //GetAll
    public List<RecordResponse> getRecordsById(Long userId) {

        return financialRecordRepository.findByUserId(userId)
                .stream()
                .map(financialRecordMapper::toResponse)
                .toList();

    }

    public RecordResponse updateRecordById(Long recordId, RecordRequest request) {

        //Fetch the existing Record to update
        FinancialRecord existingRecord = financialRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        //Updating the Record Manually
        existingRecord.setAmount(request.getAmount());
        existingRecord.setCategory(request.getCategory());
        existingRecord.setDate(request.getDate());
        existingRecord.setDescription(request.getDescription());

        // Save updated record
        FinancialRecord savedRecord = financialRecordRepository.save(existingRecord);

        return financialRecordMapper.toResponse(savedRecord);
    }

    public RecordResponse deleteRecord(Long recordId) {
        FinancialRecord record =  financialRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        financialRecordRepository.delete(record);

        return financialRecordMapper.toResponse(record);
    }

    public List<RecordResponse> getRecordsByCategory(Long userId, String category) {
        return financialRecordRepository.findByUserIdAndCategory(userId, category)
                .stream()
                .map(financialRecordMapper::toResponse)
                .toList();
    }

    public List<RecordResponse> getRecordsByType(Long userId, RecordType type) {
        return financialRecordRepository.findByUserIdAndRecordType(userId, type)
                .stream()
                .map(financialRecordMapper::toResponse)
                .toList();
    }


}
