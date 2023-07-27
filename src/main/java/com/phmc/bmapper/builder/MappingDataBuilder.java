package com.phmc.bmapper.builder;

import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.utils.MapperUtils;
import com.phmc.bmapper.utils.MappingContext;
import com.phmc.bmapper.utils.MappingType;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MappingDataBuilder {
    private final Set<MappingType> mappingTypes;
    private MappingContext mappingContext;
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
        this.mappingContext.setMainClass(mainClass);
    }

    public void setContext(ApplicationContext context) {
        this.mappingContext.setContext(context);
    }

    public void setMappingContext(MappingContext mappingContext) {
        this.mappingContext = mappingContext;
    }

    public void setMappedObjects(Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects) {
        this.mappedObjects = mappedObjects;
    }

    public Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedObjects() {
        return mappedObjects;
    }

    public void initMappingProps() {
        this.setMappedObjects(MapperUtils.getMappedProperties(this.mappingContext, this.mappingTypes));
    }
}
