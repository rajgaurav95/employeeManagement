package com.practice.employee.employeeManagement.employeeManagement.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    ResourceNotFoundException(String message){
        super(message);
    }
}
