package com.practice.employee.employeeManagement.employeeManagement.specifications;

import com.practice.employee.employeeManagement.employeeManagement.entities.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {


    public static Specification<Employee> hasLocation(String location) {
        return (root, query, cb) ->
                location == null || location.isBlank()
                        ? null
                        : cb.equal(cb.lower(root.get("location")), location.toLowerCase());
    }


    public static Specification<Employee> hasDesignation(String designation) {
        return (root,query,cb)->
                designation == null || designation.isBlank()
                    ? null
                        : cb.equal(cb.lower(root.get("designation")), designation.toLowerCase());
    }
}