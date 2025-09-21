package com.practice.employee.employeeManagement.employeeManagement.services.impl;

import com.practice.employee.employeeManagement.employeeManagement.dto.requests.CreateEmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.requests.GetQueryParamsDto;
import com.practice.employee.employeeManagement.employeeManagement.dto.responses.EmployeeResponseDto;
import com.practice.employee.employeeManagement.employeeManagement.entities.Employee;
import com.practice.employee.employeeManagement.employeeManagement.exceptions.ResourceNotFoundException;
import com.practice.employee.employeeManagement.employeeManagement.repositories.EmployeeRepository;
import com.practice.employee.employeeManagement.employeeManagement.services.EmployeeService;
import com.practice.employee.employeeManagement.employeeManagement.specifications.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<EmployeeResponseDto> getAllEmployee(GetQueryParamsDto getQueryParamsDto) {
        Pageable pageable = buildPageable(getQueryParamsDto);

        Specification<Employee> spec = Specification.allOf(
                EmployeeSpecification.employeeFieldEqualsIgnoreCase("location", getQueryParamsDto.getLocation()),
                EmployeeSpecification.employeeFieldEqualsIgnoreCase("designation", getQueryParamsDto.getDesignation()),
                EmployeeSpecification.employeeFieldEqualsIgnoreCase("email", getQueryParamsDto.getEmail())
        );

        Page<Employee> employees = employeeRepository.findAll(spec, pageable);

        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponseDto.class))
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

    @Override
    public EmployeeResponseDto saveEmployee(CreateEmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeResponseDto.class);
    }

    @Override
    public EmployeeResponseDto getByEmpId(long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("employee not present in table"));
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }

}
