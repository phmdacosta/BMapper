package com.phmc.bmapper.validator;

public class MapperObjectValidator implements MapperTypeValidator {
    @Override
    public boolean accept(Class<?> clazz) {
        return false;
    }
}
