package com.practice.employee.employeeManagement.employeeManagement.dto.enums;

import com.practice.employee.employeeManagement.employeeManagement.converters.CaseInsensitiveEnum;
import lombok.Getter;

@Getter
@CaseInsensitiveEnum
public enum SortBy {
    EMP_NAME("empName"),
    DESIGNATION("designation"),
    LOCATION("location"),
    CREATED_AT("createdAt");

    private final String field;

    SortBy(String field) {
        this.field = field;
    }
//
//    public String getField() {
//        return field;
//    }
}