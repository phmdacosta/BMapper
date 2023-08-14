package com.phmc.bmapper.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MappingCollection {
    String name();
    Class<?> elementType();
}
