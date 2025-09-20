package com.practice.employee.employeeManagement.employeeManagement.dto;

import com.practice.employee.employeeManagement.employeeManagement.dto.enums.SortBy;
import com.practice.employee.employeeManagement.employeeManagement.dto.enums.SortDir;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GetQueryParamsDto {

    @Min(value = 0, message = "Page index must be greater than or equal to 0")
    private int page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot be greater than 100")
    private int pageSize = 10;

    private String location;
    private String designation;

    private SortBy sortBy;   // ✅ nullable
    private SortDir sortDir; // ✅ nullable

//    // ✅ Case-insensitive setters
//    public void setSortBy(String sortBy) {
//        if (sortBy != null && !sortBy.isBlank()) {
//            this.sortBy = SortBy.valueOf(sortBy.toUpperCase());
//        }
//    }

//    public void setSortDir(String sortDir) {
//        if (sortDir != null && !sortDir.isBlank()) {
//            this.sortDir = SortDir.valueOf(sortDir.toUpperCase());
//        }
//    }
}
