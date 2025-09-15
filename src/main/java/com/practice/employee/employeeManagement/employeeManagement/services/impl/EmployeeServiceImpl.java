package com.practice.employee.employeeManagement.employeeManagement.services.impl;

import com.practice.employee.employeeManagement.employeeManagement.dto.EmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<EmployeeDto> employeeDtos= List.of(
                new EmployeeDto("gaurav",10),
                new EmployeeDto("mona",22)
        );
        return employeeDtos;
    }
}
