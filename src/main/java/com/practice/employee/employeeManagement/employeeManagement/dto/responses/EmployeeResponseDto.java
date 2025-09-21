package com.practice.employee.employeeManagement.employeeManagement.dto.responses;

import com.practice.employee.employeeManagement.employeeManagement.dto.requests.Address;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeResponseDto {

    private Long id;
    private String empName;
    private String designation;

    private String location;
    private String email;

    private int salary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Address address;
}
