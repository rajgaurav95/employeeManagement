package com.practice.employee.employeeManagement.employeeManagement.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeResponseDto {

    private Long id;
    private String empName;
    private String designation;

    private String location;

    private int salary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
