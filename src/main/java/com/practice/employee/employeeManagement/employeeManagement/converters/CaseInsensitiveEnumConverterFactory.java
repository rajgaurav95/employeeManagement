package com.practice.employee.employeeManagement.employeeManagement.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class CaseInsensitiveEnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        // Only apply case-insensitive conversion to annotated enums
        if (targetType.isAnnotationPresent(CaseInsensitiveEnum.class)) {
            return new CaseInsensitiveEnumConverter<>(targetType);
        }
        // Default: Spring’s case-sensitive conversion
        return source -> (T) Enum.valueOf(targetType, source);
    }


    private static class CaseInsensitiveEnumConverter<T extends Enum<T>> implements Converter<String, T> {
        private final Class<T> enumType;

        CaseInsensitiveEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }
            String normalized = source.trim();

            for (T constant : enumType.getEnumConstants()) {
                // match against name (case-insensitive)
                if (constant.name().equalsIgnoreCase(normalized)) {
                    return constant;
                }
            }

            throw new IllegalArgumentException(
                    "Invalid value '" + source + "' for enum " + enumType.getSimpleName()
            );
        }
    }
}
