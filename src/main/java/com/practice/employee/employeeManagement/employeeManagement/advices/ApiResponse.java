package com.practice.employee.employeeManagement.employeeManagement.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private T data;
    private LocalDateTime timestamp;
    private ApiError error;

    ApiResponse (){this.timestamp=LocalDateTime.now();}

    ApiResponse(T data){
        this();
        this.data=data;
    }

    ApiResponse(ApiError error){
        this();
        this.error = error;
    }


}
