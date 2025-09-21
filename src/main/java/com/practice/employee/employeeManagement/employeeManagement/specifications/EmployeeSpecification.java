package com.practice.employee.employeeManagement.employeeManagement.specifications;

import com.practice.employee.employeeManagement.employeeManagement.entities.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> employeeFieldEqualsIgnoreCase(String fieldName, String value) {
        return (root, query, cb) ->
                value == null || value.isBlank()
                        ? null
                        : cb.equal(cb.lower(root.get(fieldName)), value.toLowerCase());
    }
}