package com.phmc.bmapper.builder;

import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.utils.MapperUtils;
import com.phmc.bmapper.utils.MappingType;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MappingDataBuilder {
    private final Set<MappingType> mappingTypes;
    private Class<?> mainClass;
    private ApplicationContext context;
    private Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects;

    public MappingDataBuilder() {
        this.mappingTypes = new HashSet<>();
    }
    public void addMappingTypes(MappingType mappingType) {
        this.mappingTypes.add(mappingType);
    }

    public void removeMappingTypes(MappingType mappingType) {
        this.mappingTypes.remove(mappingType);
    }

    public Set<MappingType> getMappingTypes() {
        return mappingTypes;
    }

    public void setMainClass(Class<?> mainClass) {
        this.mainClass = mainClass;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public void setMappedObjects(Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects) {
        this.mappedObjects = mappedObjects;
    }

    public Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedObjects() {
        return mappedObjects;
    }

    public void initMappingProps() {
        this.setMappedObjects(MapperUtils.getMappedProperties(this.mainClass, this.mappingTypes));
    }
}
