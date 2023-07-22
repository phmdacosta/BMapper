package com.phmc.bmapper;

import com.pedrocosta.springutils.WrapperType;
import com.phmc.bmapper.builder.MappingDataBuilder;

public class WrapperMapper<FROM, TO> extends TypeMapper<FROM, TO> {

    @SuppressWarnings("unchecked")
    protected TO map(FROM from, Class<TO> resultClass) {
        if (!WrapperType.is(resultClass)) {
            return ((TypeMapper<FROM, TO>) loadMapper(from.getClass(), resultClass)).doMapping(from, resultClass);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected TO map(MappingDataBuilder dataHelper, FROM from, Class<TO> resultClass) {
        if (!WrapperType.is(resultClass)) {
            return ((TypeMapper<FROM, TO>) loadMapper(from.getClass(), resultClass)).doMapping(dataHelper, from, resultClass);
        }
        return null;
    }
}
