package com.phmc.bmapper.annotation;

import com.pedrocosta.springutils.ClassFinder;
import com.pedrocosta.springutils.ClassUtils;
import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.PropertyDescriptor;
import com.phmc.bmapper.utils.MapperUtils;
import com.phmc.bmapper.utils.MappingContext;
import com.phmc.bmapper.utils.MappingLoader;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationMappingLoader implements MappingLoader {
    @Override
    public @NotNull Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final MappingContext mappingContext) {
        Map<Class<?>, Class<?>> eligibleMappingBeans = getAllEligibleMapping(mappingContext.getContext());
        if (eligibleMappingBeans.isEmpty()) {
            eligibleMappingBeans = getAllEligibleMapping(mappingContext.getTargetPackage());
        }
        return _getMappedProperties(eligibleMappingBeans);
    }

    @Override
    public @NotNull Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final Class<?> classA, final Class<?> classB) {
        Map<Class<?>, Class<?>> eligibleMappingBeans = getEligibleMapping(classA, classB);
        return _getMappedProperties(eligibleMappingBeans);
    }

    @NotNull
    private Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> _getMappedProperties(Map<Class<?>, Class<?>> eligibleMappingBeans) {
        Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects = new HashMap<>();

        if (eligibleMappingBeans.isEmpty()) {
            Log.warn(BMapper.class, String.format("Could not find any class with annotation %s", getAnnotClassForMappingClass().getSimpleName()));
        }

        for (Map.Entry<Class<?>, Class<?>> mappingBeans : eligibleMappingBeans.entrySet()) {
            // Mapping same class A
            updateMap(mappedObjects, mappingBeans.getKey(), mappingBeans.getKey());

            // Mapping same class B
            updateMap(mappedObjects, mappingBeans.getValue(), mappingBeans.getValue());

            // Mapping from class A to class B
            updateMap(mappedObjects, mappingBeans.getKey(), mappingBeans.getValue());

            // Mapping from class B to class A
            updateMap(mappedObjects, mappingBeans.getValue(), mappingBeans.getKey());
        }

        return mappedObjects;
    }

    private void updateMap(Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects, Class<?> fromClass, Class<?> toClass) {
        Log.info(BMapper.class,
                String.format("Initiate mapping: %s -> %s", fromClass.getSimpleName(), toClass.getSimpleName()));

        String keyName = MapperUtils.getMappingKeyName(fromClass, toClass);
        if (!mappedObjects.containsKey(keyName)) {
            mappedObjects.put(keyName, _getMappedProperties(fromClass, toClass));
        }
    }

    private Map<Class<?>, Class<?>> getAllEligibleMapping(ApplicationContext context) {
        Map<Class<?>, Class<?>> qualifiedMapping = new HashMap<>();

        if (context == null) {
            return qualifiedMapping;
        }

        try {
            List<Class<?>> beans = ClassFinder.findAllByAnnotation(context, getAnnotClassForMappingClass());
            for (Class<?> bean : beans) {
                qualifiedMapping.put(bean, bean.getAnnotation(getAnnotClassForMappingClass()).targetClass());
            }
        } catch (ClassNotFoundException e) {
            Log.warn(BMapper.class, String.format("Could not find any class with %s annotation",
                    getAnnotClassForMappingClass().getSimpleName()));
        }

        return qualifiedMapping;
    }

    private Map<Class<?>, Class<?>> getAllEligibleMapping(Package targetPackage) {
        Map<Class<?>, Class<?>> qualifiedMapping = new HashMap<>();

        if (targetPackage == null) {
            return qualifiedMapping;
        }

        try {
            List<Class<?>> beans = ClassFinder.findAllByAnnotation(getAnnotClassForMappingClass(), targetPackage);
            for (Class<?> bean : beans) {
                qualifiedMapping.put(bean, bean.getAnnotation(getAnnotClassForMappingClass()).targetClass());
            }
        } catch (ClassNotFoundException e) {
            Log.warn(BMapper.class, String.format("Could not find any class with %s annotation",
                    getAnnotClassForMappingClass().getSimpleName()));
        }

        return qualifiedMapping;
    }

    private Map<Class<?>, Class<?>> getEligibleMapping(Class<?> classA, Class<?> classB) {
        Map<Class<?>, Class<?>> qualifiedMapping = new HashMap<>();

        Class<?> mappedByAnnotClass = classA;
        Annotation annotMappingClass = classA.getAnnotation(getAnnotClassForMappingClass());
        if (annotMappingClass == null) {
            mappedByAnnotClass = classB;
            annotMappingClass = classB.getAnnotation(getAnnotClassForMappingClass());
        }

        if (annotMappingClass != null) {
            qualifiedMapping.put(mappedByAnnotClass, mappedByAnnotClass.getAnnotation(getAnnotClassForMappingClass()).targetClass());
        }

        return qualifiedMapping;
    }

    private Map<ChainPropertyDescriptor, ChainPropertyDescriptor> _getMappedProperties(Class<?> fromClass, Class<?> toClass) {
        Map<ChainPropertyDescriptor, ChainPropertyDescriptor> mappedMethods = new HashMap<>();

        //If any class is null, return empty map
        if (fromClass == null || toClass == null) {
            return mappedMethods;
        }

        boolean isRefClassFromClass = false;
        Class<?> refClass;
        Class<?> oppositeClass;
        if (hasMappingClassAnnotation(fromClass)) {
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
                    chainPropOpposite = getChainPropertyFromAnnotation(propDescriptorRef.getPropertyAnnotation(), oppositeClass);
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

    private ChainPropertyDescriptor getChainPropertyFromAnnotation(Annotation annotation, Class<?> lookingClass) {
        if (annotation == null || lookingClass == null) {
            return new ChainPropertyDescriptor();
        }
        String fieldName = getFieldNameFromMappingAnnotation(annotation);
        return MapperUtils.buildChainPropertyDescriptor(lookingClass, fieldName);
    }

    private String getFieldNameFromMappingAnnotation(Annotation annotation) {
        if(annotation == null || !AnnotationMappingType.has(annotation.annotationType())) {
            return null;
        }
        String annotFieldName = null;
        try {
            annotFieldName = (String) annotation.annotationType()
                    .getDeclaredMethod("name", new Class[0]).invoke(annotation);
        } catch (IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException | NullPointerException ignored) {}
        return annotFieldName;
    }

    private Class<MappingClass> getAnnotClassForMappingClass() {
        return MappingClass.class;
    }

    private boolean hasMappingClassAnnotation(Class<?> clazz) {
        return ClassUtils.hasAnnotation(clazz, getAnnotClassForMappingClass());
    }
}
