package com.phmc.bmapper;

import com.phmc.bmapper.builder.MappingDataBuilder;

import java.util.Arrays;

public class ArrayMapper<FROM, TO> extends TypeMapper<FROM, TO> {

    @Override
    @SuppressWarnings("unchecked")
    protected TO map(FROM from, Class<TO> resultElementClass) {
        if (!from.getClass().isArray()) {
            throw new IllegalArgumentException(
                    String.format("%s is not a array", from.getClass().getSimpleName()));
        }
        Object[] array = (Object[]) from;
        return new CollectionMapper<FROM, TO>().map((FROM)Arrays.asList(array), resultElementClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected TO map(MappingDataBuilder dataHelper, FROM from, Class<TO> resultElementClass) {
        if (!from.getClass().isArray()) {
            throw new IllegalArgumentException(
                    String.format("%s is not a array", from.getClass().getSimpleName()));
        }
        Object[] array = (Object[]) from;
        return new CollectionMapper<FROM, TO>().map(dataHelper, (FROM)Arrays.asList(array), resultElementClass);
    }
}
