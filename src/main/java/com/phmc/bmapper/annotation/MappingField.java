package com.phmc.bmapper.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MappingField {
    String name();
    Class<?> targetClass() default MappingField.class;
}
