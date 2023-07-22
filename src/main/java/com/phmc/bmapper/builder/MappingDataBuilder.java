package com.phmc.bmapper.builder;

import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.utils.MapperUtils;
import com.phmc.bmapper.utils.MappingType;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;

public class MappingDataBuilder {
    private Set<MappingType> mappingTypes;
    private boolean enableXmlMapping;
    private boolean enableAnnotationMapping;
    private ApplicationContext context;
    private Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects;

    public void addMappingTypes(MappingType mappingType) {
        this.mappingTypes.add(mappingType);
    }

    public void removeMappingTypes(MappingType mappingType) {
        this.mappingTypes.remove(mappingType);
    }

    public void setEnableXmlMapping(boolean enableXmlMapping) {
        this.enableXmlMapping = enableXmlMapping;
    }

    public void setEnableAnnotationMapping(boolean enableAnnotationMapping) {
        this.enableAnnotationMapping = enableAnnotationMapping;
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
        this.setMappedObjects(MapperUtils.getMappedProperties(this.context, this.mappingTypes));
    }
}
