package com.phmc.bmapper;

import com.phmc.bmapper.builder.ViewMapperBuilder;

public abstract class TypeMapper<FROM, TO> {

    private ViewMapperBuilder mapperBuilder;

    public TypeMapper<FROM, TO> setMapperBuilder(ViewMapperBuilder mapperBuilder) {
        this.mapperBuilder = mapperBuilder;
        return this;
    }

    public ViewMapperBuilder getMapperBuilder() {
        return mapperBuilder;
    }

    /**
     * <b>Deprecated since 2023-06.</b><br>
     * Use {@link TypeMapper#doMapping(MappingDataHelper, Object, Class)} instead.<br>
     * Map a model to a view, and vice versa.
     *
     * @param from      Object to map
     * @param toClass   Class to the target result
     */
    @Deprecated
    public TO doMapping(FROM from, Class<TO> toClass) {
        if (!this.isValidParameters(from, toClass)) {
            return null;
        }
        return this.map(from, toClass);
    }

    /**
     * Map a model to a view, and vice versa.
     *
     * @param from      Object to map
     * @param toClass   Class to the target result
     */
    public TO doMapping(MappingDataHelper dataHelper, FROM from, Class<TO> toClass) {
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
            this.setMapperBuilder(new ViewMapperBuilder());

        return this.getMapperBuilder().create(fromClass, toClass);
    }

    /**
     * <b>Deprecated since 2023-06.</b><br>
     * Use {@link TypeMapper#map(MappingDataHelper, Object, Class)} instead.
     */
    @Deprecated
    protected abstract TO map(FROM from, Class<TO> resultClass);

    protected abstract TO map(MappingDataHelper dataHelper, FROM from, Class<TO> resultClass);
}
