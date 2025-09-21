package com.practice.employee.employeeManagement.employeeManagement.entities;

import com.practice.employee.employeeManagement.employeeManagement.dto.requests.Address;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "employee",
        uniqueConstraints = @UniqueConstraint(columnNames = {"empName", "designation"})
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_name", nullable = false)
    private String empName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String location;

    private int salary;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Embedded
    private Address address;
}