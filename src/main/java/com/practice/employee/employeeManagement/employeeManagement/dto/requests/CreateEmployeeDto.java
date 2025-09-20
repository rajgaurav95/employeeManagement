package com.practice.employee.employeeManagement.employeeManagement.dto.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {

    @NotBlank(message = "empName must not be blank")
    private String empName;

    @NotBlank(message = "Designation must not be blank")
    private String designation;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @Min(value = 0, message = "Salary must be positive")
    private int salary;
}