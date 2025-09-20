package com.practice.employee.employeeManagement.employeeManagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @Null(message = "Id must not be provided, it is auto-generated")
    private Long id;

    @NotBlank(message = "empName must not be blank")
    private String empName;

    @NotBlank(message = "Designation must not be blank")
    private String designation;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @Min(value = 0, message = "Salary must be positive")
    private int salary;

    @Null(message = "createdAt must not be provided, it is system generated")
    private LocalDateTime createdAt;

    @Null(message = "updatedAt must not be provided, it is system generated")
    private LocalDateTime updatedAt;
}