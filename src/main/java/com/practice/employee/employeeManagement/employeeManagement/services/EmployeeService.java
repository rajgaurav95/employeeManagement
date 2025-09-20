package com.practice.employee.employeeManagement.employeeManagement.services;

import com.practice.employee.employeeManagement.employeeManagement.dto.requests.CreateEmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.requests.GetQueryParamsDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.responses.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeResponseDto> getAllEmployee(GetQueryParamsDto getQueryParamsDto);

    EmployeeResponseDto saveEmployee(CreateEmployeeDto employeeDto);

    EmployeeResponseDto getByEmpId(long empId);
}
