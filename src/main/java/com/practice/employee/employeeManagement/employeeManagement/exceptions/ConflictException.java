package com.practice.employee.employeeManagement.employeeManagement.exceptions;

public class ConflictException extends RuntimeException{
    ConflictException(String message){
        super(message);
    }
}
