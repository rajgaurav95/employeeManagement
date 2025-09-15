package com.practice.employee.employeeManagement.employeeManagement.controllers;

import com.practice.employee.employeeManagement.employeeManagement.dto.EmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.services.EmployeeService;
import com.practice.employee.employeeManagement.employeeManagement.services.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public ResponseEntity<List<EmployeeDto>> getAll(){
        System.out.println("get all Employees");
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @GetMapping("/{empId}")
    public String getByEmpId(@PathVariable long empId){
        return "getByempid"+empId;
    }


    @PostMapping()
    public String createNewEmployee(@RequestBody String body){
        return "post mapping called";
    }

}
