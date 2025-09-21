package com.practice.employee.employeeManagement.employeeManagement.repositories;

import com.practice.employee.employeeManagement.employeeManagement.dto.requests.CreateEmployeeDto;
import com.practice.employee.employeeManagement.employeeManagement.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Employee findByEmpName(String empName);
}
