package com.practice.employee.employeeManagement.employeeManagement.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee {
    @Id
    public String id;

    String empName;
    Integer empAge;
}
