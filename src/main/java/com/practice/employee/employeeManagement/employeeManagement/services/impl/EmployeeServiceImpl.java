package com.practice.employee.employeeManagement.employeeManagement.services.impl;

import com.practice.employee.employeeManagement.employeeManagement.dto.EmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.GetQueryParamsDto;
import com.practice.employee.employeeManagement.employeeManagement.entities.Employee;
import com.practice.employee.employeeManagement.employeeManagement.exceptions.ResourceNotFoundException;
import com.practice.employee.employeeManagement.employeeManagement.repositories.EmployeeRepository;
import com.practice.employee.employeeManagement.employeeManagement.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;


    @Override
    public List<EmployeeDto> getAllEmployee(GetQueryParamsDto getQueryParamsDto) {
        Pageable pageable = buildPageable(getQueryParamsDto);

        Page<Employee> employees = employeeRepository.findAll(pageable);

        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Builds Pageable object based on query params.
     * - If no sortBy → return pageable without sorting
     * - If sortBy given but no sortDir → default ASC
     */
    private Pageable buildPageable(GetQueryParamsDto getQueryParamsDto) {
        if (getQueryParamsDto.getSortBy() != null) {
            Sort.Direction direction = (getQueryParamsDto.getSortDir() == null)
                    ? Sort.Direction.ASC
                    : Sort.Direction.valueOf(getQueryParamsDto.getSortDir().name());

            return PageRequest.of(
                    getQueryParamsDto.getPage(),
                    getQueryParamsDto.getPageSize(),
                    Sort.by(direction, getQueryParamsDto.getSortBy().getField())
            );
        }

        return PageRequest.of(
                getQueryParamsDto.getPage(),
                getQueryParamsDto.getPageSize()
        );
    }


//    @Override
//    public List<EmployeeDto> getAllEmployee(GetQueryParamsDto getQueryParamsDto) {
//        Sort.Direction direction = Sort.Direction.valueOf(getQueryParamsDto.getSortDir().name());
//        String sortByField = getQueryParamsDto.getSortBy().getField();
//
//        Pageable pageable = PageRequest.of(
//                getQueryParamsDto.getPage(),
//                getQueryParamsDto.getPageSize(),
//                Sort.by(direction, sortByField)
//        );
//
//        Page<Employee> employees = employeeRepository.findAll(pageable);
//
//        return employees.stream()
//                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
//                .collect(Collectors.toList());
//    }


    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        System.out.println("empl saved");
        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }

    @Override
    public EmployeeDto getByEmpId(long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() ->  new ResourceNotFoundException("employee not present in table"));
        return modelMapper.map(employee,EmployeeDto.class);
    }
}
