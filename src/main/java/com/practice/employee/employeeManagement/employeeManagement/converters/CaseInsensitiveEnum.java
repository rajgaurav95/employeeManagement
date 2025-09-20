package com.practice.employee.employeeManagement.employeeManagement.converters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)   // Can annotate enum types
@Retention(RetentionPolicy.RUNTIME)
public @interface CaseInsensitiveEnum {
}
