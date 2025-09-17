package com.practice.employee.employeeManagement.employeeManagement.dto.enums;

import lombok.Getter;

@Getter
public enum SortBy {
    EMP_NAME("empName"),
    DESIGNATION("designation"),
    LOCATION("location"),
    CREATED_AT("createdAt");

    private final String field;

    SortBy(String field) {
        this.field = field;
    }
}