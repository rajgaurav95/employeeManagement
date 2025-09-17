package com.practice.employee.employeeManagement.employeeManagement.controllers;

import com.practice.employee.employeeManagement.employeeManagement.dto.EmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.GetQueryParamsDto;
import com.practice.employee.employeeManagement.employeeManagement.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<EmployeeDto>> getAll(@Valid @ModelAttribute GetQueryParamsDto getQueryParamsDto){
        return ResponseEntity.ok(employeeService.getAllEmployee(getQueryParamsDto));
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeDto> getByEmpId(@PathVariable long empId){
        return ResponseEntity.ok(employeeService.getByEmpId(empId));
    }


    @PostMapping()
    public ResponseEntity<EmployeeDto> createNewEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.saveEmployee(employeeDto),HttpStatus.CREATED);
    }

}
