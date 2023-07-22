package com.phmc.bmapper.builder;

import com.phmc.bmapper.*;
import com.phmc.bmapper.utils.MapperUtils;
import com.phmc.bmapper.validator.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TypeMapperBuilder {

    private final Map<MapperKey<?,?>, TypeMapper<?,?>> mappers;

    public TypeMapperBuilder() {
        mappers = new HashMap<>();
    }

    public TypeMapperBuilder addMapper(TypeMapper<?,?> mapper, Class<?> fromClass, Class<?> toClass) {
        MapperKey<?,?> key = new MapperKey<>(fromClass, toClass);
        this.mappers.put(key, mapper);
        return this;
    }

    public TypeMapper<?,?> create(Class<?> fromClass, Class<?> toClass) {
        TypeMapper<?,?> typeMapper = findMapper(fromClass, toClass);
        return typeMapper != null ? typeMapper : create(toClass);
    }

    public TypeMapper<?,?> create(Class<?> clazz) {
        return MapperType.get(clazz).getMapper().setMapperBuilder(this);
    }

    private TypeMapper<?,?> findMapper(Class<?> fromClass, Class<?> toClass) {
        Map.Entry<MapperKey<?,?>, TypeMapper<?,?>> entry = this.mappers.entrySet().stream().filter(
                e -> new MapperKey<>(fromClass, toClass).equals(e.getKey())
        ).findAny().orElse(null);
        if (entry != null) {
            return entry.getValue();
        }
        return null;
    }

    private enum MapperType {
        DEFAULT(new MapperObjectValidator(), new ObjectMapper()),
        COLLECTION(new MapperCollectionValidator(), new CollectionMapper<>()),
        MAP(new MapperMapValidator(), new MapMapper<>()),
        ARRAY(new MapperArrayValidator(), new ArrayMapper<>()),
        @SuppressWarnings("rawtypes")
        WRAPPER(new MapperWrapperValidator(), new WrapperMapper());

        final MapperTypeValidator validator;
        final TypeMapper<?,?> mapper;

        MapperType(MapperTypeValidator validator, TypeMapper<?,?> mapper) {
            this.validator = validator;
            this.mapper = mapper;
        }

        public static MapperType get(Class<?> clazz) {
            return Arrays.stream(MapperType.values())
                    .filter(type -> type.validator.accept(clazz))
                    .findAny().orElse(DEFAULT);
        }

        public static MapperType get(Field field) {
            return MapperType.get(field.getType());
        }

        public static MapperType get(Method method) {
            return MapperType.get(MapperUtils.getTypeOfMethod(method));
        }

        public TypeMapper<?,?> getMapper() {
            return mapper;
        }
    }
}
