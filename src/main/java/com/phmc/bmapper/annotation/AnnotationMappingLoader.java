package com.phmc.bmapper.annotation;

import com.pedrocosta.springutils.ClassFinder;
import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.PropertyDescriptor;
import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.utils.MapperUtils;
import com.phmc.bmapper.utils.MappingLoader;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationMappingLoader implements MappingLoader {

    @Override
    public Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(ApplicationContext context) {
        Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects = new HashMap<>();

        Map<Class<?>, Class<?>> eligibleMappingBeans = getAllEligibleMapping(context);
        for (Map.Entry<Class<?>, Class<?>> mappingBeans : eligibleMappingBeans.entrySet()) {
            Log.info(BMapper.class,
                    String.format("Initiate mapping: %s -> %s", mappingBeans.getKey().getSimpleName(), mappingBeans.getValue().getSimpleName()));
            mappedObjects.put(
                    MapperUtils.getMappingKeyName(mappingBeans.getKey(), mappingBeans.getValue()),
                    getMappedProperties(mappingBeans.getKey(), mappingBeans.getValue()));

            Log.info(BMapper.class,
                    String.format("Initiate mapping: %s -> %s", mappingBeans.getValue().getSimpleName(), mappingBeans.getKey().getSimpleName()));
            mappedObjects.put(
                    MapperUtils.getMappingKeyName(mappingBeans.getValue(), mappingBeans.getKey()),
                    getMappedProperties(mappingBeans.getValue(), mappingBeans.getKey()));
        }

        return mappedObjects;
    }

    private Map<Class<?>, Class<?>> getAllEligibleMapping(ApplicationContext context) {
        Map<Class<?>, Class<?>> qualifiedMapping = new HashMap<>();
        Class<MappingClass> annotationClass = MappingClass.class;

        try {
            List<Class<?>> beans = ClassFinder.findAllByAssignable(context, annotationClass);
            for (Class<?> bean : beans) {
                qualifiedMapping.put(bean, bean.getAnnotation(annotationClass).targetClass());
            }
        } catch (ClassNotFoundException e) {
            Log.warn(BMapper.class, String.format("Could not find any class with %s annotation",
                    annotationClass.getSimpleName()));
        }

        return qualifiedMapping;
    }

    private Map<ChainPropertyDescriptor, ChainPropertyDescriptor> getMappedProperties(Class<?> fromClass, Class<?> toClass) {
        Map<ChainPropertyDescriptor, ChainPropertyDescriptor> mappedMethods = new HashMap<>();

        //If any class is null, return empty map
        if (fromClass == null || toClass == null) {
            return mappedMethods;
        }

        boolean isRefClassFromClass = false;
        Class<?> refClass;
        Class<?> oppositeClass;
        if (MapperUtils.hasMappingClassAnnotation(fromClass)) {
            refClass = fromClass;
            oppositeClass = toClass;
            isRefClassFromClass = true;
        } else {
            refClass = toClass;
            oppositeClass = fromClass;
        }

        PropertyDescriptor[] propDescriptorsRef = MapperUtils.getPropertyDescriptors(refClass);
        for (PropertyDescriptor propDescriptorRef : propDescriptorsRef) {
            if (!MapperUtils.isValidPropertyDescriptor(propDescriptorRef)) {
                continue;
            }

            try {
                ChainPropertyDescriptor chainPropRef = new ChainPropertyDescriptor();
                ChainPropertyDescriptor chainPropOpposite = new ChainPropertyDescriptor();

                chainPropRef.add(propDescriptorRef);
                if (propDescriptorRef.getPropertyAnnotation() == null) {
                    chainPropOpposite.add(MapperUtils.getPropertyDescriptor(oppositeClass, propDescriptorRef.getName()));
                } else {
                    chainPropOpposite = MapperUtils.getChainPropertyFromAnnotation(propDescriptorRef.getPropertyAnnotation(), oppositeClass);
                }

                if (isRefClassFromClass) {
                    mappedMethods.put(chainPropOpposite, chainPropRef);
                } else {
                    mappedMethods.put(chainPropRef, chainPropOpposite);
                }
            } catch (NullPointerException ignored) {}
        }

        return mappedMethods;
    }
}
