package com.practice.employee.employeeManagement.employeeManagement.services;

import com.practice.employee.employeeManagement.employeeManagement.dto.EmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.GetQueryParamsDto;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDto> getAllEmployee(GetQueryParamsDto getQueryParamsDto);

    EmployeeDto saveEmployee( EmployeeDto employeeDto);

    EmployeeDto getByEmpId(long empId);
}
