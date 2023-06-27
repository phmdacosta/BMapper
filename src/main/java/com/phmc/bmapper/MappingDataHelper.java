package com.phmc.bmapper;

import com.pedrocosta.springutils.output.Log;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class MappingDataHelper {
    private Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects;

    public void setMappedObjects(Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects) {
        this.mappedObjects = mappedObjects;
    }

    public Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedObjects() {
        return mappedObjects;
    }

    public void initMappingProps(final ApplicationContext context) {
        this.setMappedObjects(new HashMap<>());

        Map<Class<?>, Class<?>> eligibleMappingBeans = MapperUtils.getAllEligibleMapping(context);
        for (Map.Entry<Class<?>, Class<?>> mappingBeans : eligibleMappingBeans.entrySet()) {
            Log.info(BMapper.class,
                    String.format("Initiate mapping: %s -> %s", mappingBeans.getKey().getSimpleName(), mappingBeans.getValue().getSimpleName()));
            this.getMappedObjects().put(
                    MapperUtils.getMappingKeyName(mappingBeans.getKey(), mappingBeans.getValue()),
                    MapperUtils.getMappedProperties(mappingBeans.getKey(), mappingBeans.getValue()));

            Log.info(BMapper.class,
                    String.format("Initiate mapping: %s -> %s", mappingBeans.getValue().getSimpleName(), mappingBeans.getKey().getSimpleName()));
            this.getMappedObjects().put(
                    MapperUtils.getMappingKeyName(mappingBeans.getValue(), mappingBeans.getKey()),
                    MapperUtils.getMappedProperties(mappingBeans.getValue(), mappingBeans.getKey()));
        }
    }
}
