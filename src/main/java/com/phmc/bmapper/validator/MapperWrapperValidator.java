package com.phmc.bmapper.validator;

import com.pedrocosta.springutils.WrapperType;

public class MapperWrapperValidator implements MapperTypeValidator {
    @Override
    public boolean accept(Class<?> clazz) {
        return WrapperType.is(clazz);
    }
}
