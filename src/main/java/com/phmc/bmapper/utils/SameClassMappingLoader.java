package com.phmc.bmapper.utils;

import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.PropertyDescriptor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SameClassMappingLoader implements MappingLoader {
    @Override
    public @NotNull Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(MappingContext mappingContext) {
        return new HashMap<>();
    }

    @Override
    @NotNull
    public Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(Class<?> classA, Class<?> classB) {
        Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mapChainPropertyDescriptors = new HashMap<>();

        Class<?> clazz = classA;
        if (classA == null) {
            clazz = classB;
        }

        if (clazz != null) {
            updateMap(mapChainPropertyDescriptors, clazz);
        }

        return mapChainPropertyDescriptors;
    }

    @NotNull
    private Map<ChainPropertyDescriptor, ChainPropertyDescriptor> _getMappedProperties(Class<?> clazz) {
        Map<ChainPropertyDescriptor, ChainPropertyDescriptor> mapChainPropertyDescriptors = new HashMap<>();

        List<ChainPropertyDescriptor> chainPropDescList = getChainPropertyDescriptor(clazz);

        for (ChainPropertyDescriptor chainPropDesc : chainPropDescList) {
            mapChainPropertyDescriptors.put(chainPropDesc, chainPropDesc);
        }

        return mapChainPropertyDescriptors;
    }

    @NotNull
    private List<ChainPropertyDescriptor> getChainPropertyDescriptor(Class<?> lookingClass) {
        List<ChainPropertyDescriptor> chainPropDescList = new ArrayList<>();

        PropertyDescriptor[] propertyDescriptors = MapperUtils.getPropertyDescriptors(lookingClass);
        for (PropertyDescriptor propDesc : propertyDescriptors) {
            ChainPropertyDescriptor chainedProperty = MapperUtils.buildChainPropertyDescriptor(lookingClass, propDesc.getName());
            chainPropDescList.add(chainedProperty);
        }

        return chainPropDescList;
    }

    private void updateMap(Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects, Class<?> clazz) {
        String keyName = MapperUtils.getMappingKeyName(clazz, clazz);
        if (!mappedObjects.containsKey(keyName)) {
            mappedObjects.put(keyName, _getMappedProperties(clazz));
        }
    }
}
