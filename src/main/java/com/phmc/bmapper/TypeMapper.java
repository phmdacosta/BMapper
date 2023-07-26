package com.phmc.bmapper;

import com.phmc.bmapper.builder.MappingDataBuilder;
import com.phmc.bmapper.builder.TypeMapperBuilder;

public abstract class TypeMapper<FROM, TO> {

    private TypeMapperBuilder mapperBuilder;

    public TypeMapper<FROM, TO> setMapperBuilder(TypeMapperBuilder mapperBuilder) {
        this.mapperBuilder = mapperBuilder;
        return this;
    }

    public TypeMapperBuilder getMapperBuilder() {
        return mapperBuilder;
    }

    /**
     * Map a model to a view, and vice versa.
     *
     * @param from      Object to map
     * @param toClass   Class to the target result
     */
    public TO doMapping(MappingDataBuilder dataHelper, FROM from, Class<TO> toClass) {
        if (!this.isValidParameters(from, toClass)) {
            return null;
        }
        return this.map(dataHelper, from, toClass);
    }

    protected <T,F> boolean isValidParameters(F from, Class<T> clazz) {
        return from != null && clazz != null;
    }

    protected TypeMapper<?,?> loadMapper(Class<?> fromClass, Class<?> toClass) {
        if (this.getMapperBuilder() == null)
            this.setMapperBuilder(new TypeMapperBuilder());

        return this.getMapperBuilder().create(fromClass, toClass);
    }

    protected abstract TO map(MappingDataBuilder dataHelper, FROM from, Class<TO> resultClass);
}
