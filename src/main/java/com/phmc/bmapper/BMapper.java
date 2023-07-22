package com.phmc.bmapper;

import com.phmc.bmapper.builder.MappingDataBuilder;
import com.phmc.bmapper.builder.TypeMapperBuilder;
import com.phmc.bmapper.exceptions.NoMappingException;

import java.util.Collection;

public class BMapper {
    private MappingDataBuilder mappingDataBuilder;

    public MappingDataBuilder getMappingDataBuilder() {
        return mappingDataBuilder;
    }

    public void setMappingDataBuilder(MappingDataBuilder mappingDataBuilder) {
        this.mappingDataBuilder = mappingDataBuilder;
    }

    /**
     * Map a model to a view, and vice versa.
     *
     * @param from      Object to map
     * @param toClass   Class to the target result
     * @param <FROM> Type of the object to map
     * @param <TO>   Type of the result
     */
    @SuppressWarnings("unchecked")
    public <FROM, TO> TO map(FROM from, Class<TO> toClass) {
        if (mappingDataBuilder == null || mappingDataBuilder.getMappedObjects() == null) {
            throw new NoMappingException("No mapping was loaded.");
        }

        TypeMapper<FROM, TO> mapper = (TypeMapper<FROM, TO>) new TypeMapperBuilder().create(from.getClass(), toClass);
        return mapper.doMapping(mappingDataBuilder, from, toClass);
    }

    @SuppressWarnings("unchecked")
    public <ELEM, FROM extends Collection<ELEM>, TO> Collection<TO> map(FROM from, Class<TO> toClass) {
        if (mappingDataBuilder == null || mappingDataBuilder.getMappedObjects() == null) {
            throw new NoMappingException("No mapping was loaded.");
        }

        CollectionMapper<FROM, TO> mapper = (CollectionMapper<FROM, TO>) new TypeMapperBuilder().create(from.getClass(), Collection.class);
        return (Collection<TO>) mapper.doMapping(mappingDataBuilder, from, toClass);
    }
}
