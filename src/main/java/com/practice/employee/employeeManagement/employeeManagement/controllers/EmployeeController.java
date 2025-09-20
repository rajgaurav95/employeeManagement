package com.practice.employee.employeeManagement.employeeManagement.controllers;

import com.practice.employee.employeeManagement.employeeManagement.dto.requests.CreateEmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.requests.GetQueryParamsDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.responses.EmployeeResponseDto;
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
    public ResponseEntity<List<EmployeeResponseDto>> getAll(@Valid @ModelAttribute GetQueryParamsDto getQueryParamsDto){
        return ResponseEntity.ok(employeeService.getAllEmployee(getQueryParamsDto));
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeResponseDto> getByEmpId(@PathVariable long empId){
        return ResponseEntity.ok(employeeService.getByEmpId(empId));
    }


    @PostMapping()
    public ResponseEntity<EmployeeResponseDto> createNewEmployee(@Valid @RequestBody CreateEmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.saveEmployee(employeeDto),HttpStatus.CREATED);
    }

}
