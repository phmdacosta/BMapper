package com.phmc.bmapper;

import com.pedrocosta.springutils.ClassUtils;
import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.builder.MappingDataBuilder;
import com.phmc.bmapper.exceptions.NoMappingException;
import com.phmc.bmapper.utils.MapperUtils;

import java.util.Map;

public class ObjectMapper extends TypeMapper<Object, Object> {

    @Override
    protected Object map(Object from, Class<Object> resultClass) {
        Object result = null;
        try {
            result = MapperUtils.getInstance(resultClass);
            Class<?> fromClass = from.getClass();
            doMapping(null, from, fromClass, result, resultClass);
        } catch (Exception e) {
            Log.warn(BMapper.class, e.getMessage());
        }

        return result;
    }

    @Override
    protected Object map(MappingDataBuilder dataHelper, Object from, Class<Object> resultClass) {
        Object result = null;
        try {
            result = MapperUtils.getInstance(resultClass);
            Class<?> fromClass = from.getClass();
            doMapping(dataHelper, from, fromClass, result, resultClass);
        } catch (Exception e) {
            Log.warn(BMapper.class, e.getMessage());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    protected <T,F> void doMapping(MappingDataBuilder dataHelper, F from, Class<?> fromClass, T result, Class<?> resultClass) {
        Map<ChainPropertyDescriptor, ChainPropertyDescriptor> mappedProperties = null;

        if (dataHelper != null) {
            mappedProperties = dataHelper.getMappedObjects().get(MapperUtils.getMappingKeyName(fromClass, resultClass));
        }

        if (mappedProperties == null) {
            throw new NoMappingException("No mapping was loaded.");
        }

        for (Map.Entry<ChainPropertyDescriptor, ChainPropertyDescriptor> entry : mappedProperties.entrySet()) {
            ChainPropertyDescriptor chainSetter = entry.getKey();
            if (chainSetter != null) {
                try {
                    if (chainSetter.size() > 0) {
                        Object instanceSetter = null;
                        for (int i = 1; i < chainSetter.size(); i++) {
                            PropertyDescriptor propertyDescSetter = chainSetter.get(i);
                            if (propertyDescSetter == null) {
                                continue;
                            }

                            instanceSetter = instanceSetter == null ? result : propertyDescSetter.getBeanInstance();

                            Object resultFieldValue = propertyDescSetter.getReadMethod().invoke(instanceSetter);
                            if (resultFieldValue == null) {
                                propertyDescSetter.getWriteMethod().invoke(instanceSetter, MapperUtils.getInstance(propertyDescSetter.getPropertyType()));
                            }
                        }
                        PropertyDescriptor propertyDescSetter = chainSetter.get(chainSetter.size());
                        instanceSetter = instanceSetter == null ? result : propertyDescSetter.getBeanInstance();

                        Object instanceGetter = null;
                        Object fieldValue = null;
                        ChainPropertyDescriptor chainGetter = entry.getValue();
                        for (int i = 1; i <= chainGetter.size(); i++) {
                            PropertyDescriptor propertyDescGetter = chainGetter.get(i);
                            if (propertyDescGetter == null) {
                                continue;
                            }

                            if (instanceGetter == null) {
                                instanceGetter = from;
                            }

                            if (i == chainGetter.size()) {
                                fieldValue = propertyDescGetter.getReadMethod().invoke(instanceGetter);
                            } else {
                                instanceGetter = propertyDescGetter.getReadMethod().invoke(instanceGetter);
                            }
                        }

                        if (fieldValue != null && !ClassUtils.isSimpleProperty(fieldValue.getClass())) {
                            fieldValue = ((TypeMapper<Object, Object>)loadMapper(fromClass, propertyDescSetter.getPropertyType())).doMapping(dataHelper, fieldValue, (Class<Object>) propertyDescSetter.getPropertyType());
                        }
                        propertyDescSetter.getWriteMethod().invoke(instanceSetter, fieldValue);
                    }
                } catch (Exception e) {
                    Log.warn(BMapper.class, e.getMessage());
                }
            }
        }
    }
}
