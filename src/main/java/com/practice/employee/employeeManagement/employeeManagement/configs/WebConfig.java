package com.practice.employee.employeeManagement.employeeManagement.configs;

import com.practice.employee.employeeManagement.employeeManagement.converters.CaseInsensitiveEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CaseInsensitiveEnumConverterFactory converterFactory;

    public WebConfig(CaseInsensitiveEnumConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(converterFactory);
    }
}
