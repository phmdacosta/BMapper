package com.phmc.bmapper.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MappingClass {
    Class<?> targetClass();
}
