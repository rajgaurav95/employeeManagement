package com.practice.employee.employeeManagement.employeeManagement.dto.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {

    @NotBlank(message = "empName must not be blank")
    private String empName;

    @NotBlank(message = "Email must not be blank")
    private String email;

    private String designation;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @Min(value = 0, message = "Salary must be positive")
    private Integer salary;
}