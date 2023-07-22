package com.phmc.bmapper.utils;

import com.pedrocosta.springutils.ClassUtils;
import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.PropertyDescriptor;
import com.phmc.bmapper.TypeMapper;
import com.phmc.bmapper.annotation.*;
import com.phmc.bmapper.builder.MappingDataBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class MapperUtils {

    public static <T> T getInstance(final Class<T> clazz) {
        T result = null;
        try {
            result = clazz.getConstructor(new Class[0]).newInstance();
        } catch (NoSuchMethodException e) {
            Log.warn(BMapper.class,
                    String.format("Could not instantiate %s there is no default constructor",
                            clazz.getSimpleName()));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            Log.error(BMapper.class, e);
        }
        return result;
    }

    /**
     * Create the mapping of all properties between to objects.
     *
     * @param context       Context of application
     * @param mappingTypes  Set of mapping types
     * @return
     * <br>
     * A map with {@link ChainPropertyDescriptor} key and {@link ChainPropertyDescriptor} value,
     * where the key has properties of the target object and the value has properties of the source object.
     *
     * <pre>
     * <code>
     * Example: <br>
     *      Source -> Square{ height, width } <br>
     *      Target -> SquareDTO{ height } <br>
     *      Map -> [ <br>
     *              key: ChainPropertyDescriptor{ SquareDTO::height }, <br>
     *              value: ChainPropertyDescriptor{ Square::height } <br>
     *             ]
     * </code>
     * <pre/>
     */
    public static Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(final ApplicationContext context, Set<MappingType> mappingTypes) {
        Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects = new HashMap<>();
        for (MappingType mappingType : mappingTypes) {
            MappingLoader loader = mappingType.getLoader();
            mappedObjects.putAll(loader.getMappedProperties(context));
        }
        return mappedObjects;
    }

    /**
     * <b>Deprecated since 2023-07.</b><br>
     * Use {@link MapperUtils#getMappedProperties(ApplicationContext, Set)} instead.<br>
     *
     * Create the mapping of all properties between to objects.
     *
     * @param fromClass Class of the source object to be mapped.
     * @param toClass   Class of the target object that will be the result of the mapping.
     * @return
     * <br>
     * A map with {@link ChainPropertyDescriptor} key and {@link ChainPropertyDescriptor} value,
     * where the key has properties of the target object and the value has properties of the source object.
     *
     * <pre>
     * <code>
     * Example: <br>
     *      Source -> Square{ height, width } <br>
     *      Target -> SquareDTO{ height } <br>
     *      Map -> [ <br>
     *              key: ChainPropertyDescriptor{ SquareDTO::height }, <br>
     *              value: ChainPropertyDescriptor{ Square::height } <br>
     *             ]
     * </code>
     * <pre/>
     */
    @Deprecated
    public static Map<ChainPropertyDescriptor, ChainPropertyDescriptor> getMappedProperties(Class<?> fromClass, Class<?> toClass) {
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

        PropertyDescriptor[] propDescriptorsRef = getPropertyDescriptors(refClass);
        for (PropertyDescriptor propDescriptorRef : propDescriptorsRef) {
            if (!isValidPropertyDescriptor(propDescriptorRef)) {
                continue;
            }

            try {
                ChainPropertyDescriptor chainPropRef = new ChainPropertyDescriptor();
                ChainPropertyDescriptor chainPropOpposite = new ChainPropertyDescriptor();

                chainPropRef.add(propDescriptorRef);
                if (propDescriptorRef.getPropertyAnnotation() == null) {
                    chainPropOpposite.add(getPropertyDescriptor(oppositeClass, propDescriptorRef.getName()));
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

//    /**
//     * Create the mapping of all properties between to objects.
//     *
//     * @param fromClass Class of the source object to be mapped.
//     * @param toClass   Class of the target object that will be the result of the mapping.
//     * @return
//     * <br>
//     * A map with {@link ChainPropertyDescriptor} key and {@link ChainPropertyDescriptor} value,
//     * where the key has properties of the target object and the value has properties of the source object.
//     *
//     * <pre>
//     * <code>
//     * Example: <br>
//     *      Source -> Square{ height, width } <br>
//     *      Target -> SquareDTO{ height } <br>
//     *      Map -> [ <br>
//     *              key: ChainPropertyDescriptor{ SquareDTO::height }, <br>
//     *              value: ChainPropertyDescriptor{ Square::height } <br>
//     *             ]
//     * </code>
//     * <pre/>
//     */
//    public static Map<ChainPropertyDescriptor, ChainPropertyDescriptor> getMappedProperties(Class<?> fromClass, Class<?> toClass, Set<MappingType> mappingTypes) {
//        Map<ChainPropertyDescriptor, ChainPropertyDescriptor> mappedProperties = new HashMap<>();
//        for (MappingType mappingType : mappingTypes) {
//            mappedProperties.putAll(mappingType.getLoader().getMappedProperties(fromClass, toClass));
//        }
//        return mappedProperties;
//    }

    public static ChainPropertyDescriptor getChainPropertyFromAnnotation(Annotation annotation, Class<?> lookingClass) {
        ChainPropertyDescriptor chainedProperty = new ChainPropertyDescriptor();

        if (annotation == null || lookingClass == null) {
            return chainedProperty;
        }

        String fieldName = getFieldNameFromMappingAnnotation(annotation);
        String[] chainFieldName = { fieldName };
        if (chainFieldName[0].contains(".")) {
            chainFieldName = chainFieldName[0].split("\\.");
        }

        PropertyDescriptor[] classPropDescriptors = getPropertyDescriptors(lookingClass);
        for (PropertyDescriptor propertyDescriptor : classPropDescriptors) {
            if (!isValidPropertyDescriptor(propertyDescriptor)
                || !Arrays.asList(chainFieldName).contains(propertyDescriptor.getName())) {
                continue;
            }

            Class<?> currentClass = lookingClass;
            for (String fName : chainFieldName) {
                PropertyDescriptor propDesc = getPropertyDescriptor(currentClass, fName);
                if (propDesc == null) {
                    break;
                }
                chainedProperty.add(propDesc);
                currentClass = propDesc.getPropertyType();
            }
            break;
        }

        return chainedProperty;
    }

    public static boolean hasMappingClassAnnotation(Class<?> clazz) {
        return ClassUtils.hasAnnotation(clazz, MappingClass.class);
    }

    public static boolean hasMappingAnnotation(Field field) {
        for (AnnotationMappingType annotMapping : AnnotationMappingType.values()) {
            if (hasAnnotation(field, annotMapping.getAnnotationClass()))
                return true;
        }
        return false;
    }

    public static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        return field.getAnnotation(annotationClass) != null;
    }

    public static boolean hasAnnotation(Method method, Class<? extends Annotation> annotationClass) {
        return method.getAnnotation(annotationClass) != null;
    }

    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) {
        java.beans.PropertyDescriptor propDesc = BeanUtils.getPropertyDescriptor(clazz, propertyName);
        if (propDesc != null) {
            Annotation annotation = getMappingAnnotation_3(propDesc);
            try {
                return new PropertyDescriptor(propDesc, clazz, getInstance(clazz), annotation);
            } catch (IntrospectionException e) {
                Log.error(BMapper.class, e);
            }
        }
        return null;
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
        java.beans.PropertyDescriptor[] propDescArr = BeanUtils.getPropertyDescriptors(clazz);
        PropertyDescriptor[] result = new PropertyDescriptor[propDescArr.length];
        for (int i = 0; i < propDescArr.length; i++) {
            java.beans.PropertyDescriptor propDesc = propDescArr[i];
            if (isValidPropertyDescriptor(propDesc)) {
                Annotation annotation = getMappingAnnotation_3(propDesc);
                try {
                    result[i] = new PropertyDescriptor(propDesc, clazz, getInstance(clazz), annotation);
                } catch (IntrospectionException e) {
                    Log.error(BMapper.class, e);
                }
            }
        }
        return result;
    }

    public static Annotation getMappingAnnotation(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (AnnotationMappingType.has(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    public static Annotation getMappingAnnotation(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (AnnotationMappingType.has(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    public static Annotation getMappingAnnotation_3(java.beans.PropertyDescriptor propertyDescriptor) {
        if (isValidPropertyDescriptor(propertyDescriptor)) {
            Class<?> clazz = propertyDescriptor.getReadMethod().getDeclaringClass();
            // Search in fields first
            try {
                Field field = clazz.getDeclaredField(propertyDescriptor.getName());
                return hasMappingAnnotation(field) ? getMappingAnnotation(field) : null;
            } catch (NoSuchFieldException ignore) {}
            // Search at getter
            for (Annotation annotation : propertyDescriptor.getReadMethod().getAnnotations()) {
                if (AnnotationMappingType.has(annotation.annotationType())) {
                    return annotation;
                }
            }
            // Search at setter
            for (Annotation annotation : propertyDescriptor.getWriteMethod().getAnnotations()) {
                if (AnnotationMappingType.has(annotation.annotationType())) {
                    return annotation;
                }
            }
        }
        return null;
    }

    public static String getFieldNameFromMappingAnnotation(Annotation annotation) {
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

    /**
     * Checks if property descriptor is not null, and it's name is different from "class".
     *
     * @param propertyDescriptor {@linkplain PropertyDescriptor PropertyDescriptor} object
     * @return  <b>{@code True}</b> if the param is not null and different from "class", <b>{@code false}</b> otherwise.
     */
    public static boolean isValidPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor != null && !"class".equals(propertyDescriptor.getName());
    }

    /**
     * Checks if property descriptor is not null, and it's name is different from "class".
     *
     * @param propertyDescriptor {@linkplain java.beans.PropertyDescriptor PropertyDescriptor} object
     * @return  <b>{@code True}</b> if the param is not null and different from "class", <b>{@code false}</b> otherwise.
     */
    public static boolean isValidPropertyDescriptor(java.beans.PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor != null && !"class".equals(propertyDescriptor.getName());
    }

    public static Class<?> getTypeOfMethod(Method method) {
        return method.getParameterTypes().length > 0 ? method.getParameterTypes()[0] : method.getReturnType();
    }

    public static String getMappingKeyName(final Class<?> fromClass, final Class<?> toClass) {
        if (fromClass == null || toClass == null) {
            return "";
        }
        return fromClass.getSimpleName() + ">>" + toClass.getSimpleName();
    }
}
