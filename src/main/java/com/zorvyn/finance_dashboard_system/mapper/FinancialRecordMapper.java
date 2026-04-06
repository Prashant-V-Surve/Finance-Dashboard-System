package com.zorvyn.finance_dashboard_system.mapper;

import com.zorvyn.finance_dashboard_system.dto.request.RecordRequest;
import com.zorvyn.finance_dashboard_system.dto.request.UserRequest;
import com.zorvyn.finance_dashboard_system.dto.response.RecordResponse;
import com.zorvyn.finance_dashboard_system.entity.FinancialRecord;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FinancialRecordMapper {

    //Request -> Entity
    @Mapping(target = "id", ignore = true)
    FinancialRecord toEntity(@Valid RecordRequest request);

    //Entity -> Response
    RecordResponse toResponse(FinancialRecord record);
}
