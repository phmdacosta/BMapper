package com.phmc.bmapper.utils;

import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.ChainPropertyDescriptor;
import com.phmc.bmapper.PropertyDescriptor;
import com.phmc.bmapper.annotation.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;

import javax.annotation.Nullable;
import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class MapperUtils {

    /**
     * Instantiate a class that have constructor without parameters.
     *
     * @param clazz Class to instantiate
     * @return  A new instance of the class
     * @param <T>   Type to object
     */
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
     * @param mappingContext    Context to find all mapped classes
     * @param mappingTypes      Set of mapping types
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
    public static Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(MappingContext mappingContext, Set<MappingType> mappingTypes) {
        Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects = new HashMap<>();
        for (MappingType mappingType : mappingTypes) {
            MappingLoader loader = mappingType.getLoader();
            mappedObjects.putAll(loader.getMappedProperties(mappingContext));
        }
        return mappedObjects;
    }

    /**
     * Create the mapping of all properties between to objects.
     *
     * @param fromClass     Source class
     * @param toClass       Target class
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
    public static Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> getMappedProperties(Class<?> fromClass, Class<?> toClass, Set<MappingType> mappingTypes) {
        Map<String, Map<ChainPropertyDescriptor, ChainPropertyDescriptor>> mappedObjects = new HashMap<>();
        for (MappingType mappingType : mappingTypes) {
            MappingLoader loader = mappingType.getLoader();
            mappedObjects.putAll(loader.getMappedProperties(fromClass, toClass));
        }
        return mappedObjects;
    }

    /**
     * Get a {@code String} array with all properties from a mapped property name divided by dot.
     * @param chainedPropName   Mapped property name
     * @return  {@code String} array with properties names
     */
    public static String[] getAllPropertiesFromChain(String chainedPropName) {
        String[] chainPropName = { chainedPropName };
        if (chainPropName[0].contains(".")) {
            chainPropName = chainPropName[0].split("\\.");
        }
        return chainPropName;
    }

    /**
     * Checks if the field in annotation of one of the types contained in {@link AnnotationMappingType}.
     *
     * @param field Field
     * @return  True if field has annotation, false otherwise.
     */
    public static boolean hasMappingAnnotation(Field field) {
        for (AnnotationMappingType annotMapping : AnnotationMappingType.values()) {
            if (hasAnnotation(field, annotMapping.getAnnotationClass()))
                return true;
        }
        return false;
    }

    /**
     * Check if field has a specific annotation.
     *
     * @param field             Field to look
     * @param annotationClass   Annotation to search
     * @return  True if the field has the indicated annotation, false otherwise.
     */
    public static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        return field.getAnnotation(annotationClass) != null;
    }

    /**
     * Load property descriptor by the property name.
     *
     * @param clazz         Class to look
     * @param propertyName  Property name
     * @return  {@link PropertyDescriptor} of the property.
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) {
        java.beans.PropertyDescriptor propDesc = BeanUtils.getPropertyDescriptor(clazz, propertyName);
        if (isValidPropertyDescriptor(propDesc)) {
            Annotation annotation = getMappingAnnotation(propDesc, clazz);
            try {
                Class<?> elementType = propDesc.getPropertyType();
                if (isCollection(elementType) || isMap(elementType)) {
                    elementType = getElementType(clazz, propDesc);
                }
                return new PropertyDescriptor(propDesc, clazz, getInstance(clazz), annotation, elementType);
            } catch (IntrospectionException e) {
                Log.warn(BMapper.class, e.getMessage());
            }
        }
        return null;
    }

    /**
     * Load all property descriptors of the class.
     *
     * @param clazz Class to look
     * @return  Array of all {@link PropertyDescriptor} from class.
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
        java.beans.PropertyDescriptor[] propDescArr = BeanUtils.getPropertyDescriptors(clazz);
        List<PropertyDescriptor> result = new ArrayList<>();
        for (java.beans.PropertyDescriptor propDesc : propDescArr) {
            if (isValidPropertyDescriptor(propDesc)) {
                Annotation annotation = getMappingAnnotation(propDesc, clazz);
                try {
                    Class<?> elementType = propDesc.getPropertyType();
                    if (isCollection(elementType) || isMap(elementType)) {
                        elementType = getElementType(clazz, propDesc);
                    }
                    result.add(new PropertyDescriptor(propDesc, clazz, getInstance(clazz), annotation, elementType));
                } catch (IntrospectionException e) {
                    Log.warn(BMapper.class, e.getMessage());
                }
            }
        }
        return result.toArray(new PropertyDescriptor[0]);
    }

    /**
     * Create a chained property descriptors.
     * See: {@link }
     * @param lookingClass
     * @param fieldName
     * @return
     */
    public static ChainPropertyDescriptor buildChainPropertyDescriptor(Class<?> lookingClass, String fieldName) {
        ChainPropertyDescriptor chainedProperty = new ChainPropertyDescriptor();

        String[] chainFieldName = MapperUtils.getAllPropertiesFromChain(fieldName);

        PropertyDescriptor[] classPropDescriptors = MapperUtils.getPropertyDescriptors(lookingClass);
        for (PropertyDescriptor propertyDescriptor : classPropDescriptors) {
            if (!MapperUtils.isValidPropertyDescriptor(propertyDescriptor)
                    || !Arrays.asList(chainFieldName).contains(propertyDescriptor.getName())) {
                continue;
            }

            Class<?> currentClass = lookingClass;
            for (String fName : chainFieldName) {
                PropertyDescriptor propDesc = MapperUtils.getPropertyDescriptor(currentClass, fName);
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

    public static Annotation getMappingAnnotation(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (AnnotationMappingType.has(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    public static Annotation getMappingAnnotation(java.beans.PropertyDescriptor propertyDescriptor, Class<?> clazz) {
        Annotation result = null;
        if (isValidPropertyDescriptor(propertyDescriptor)) {
            // Search in fields first
            try {
                Field field = clazz.getDeclaredField(propertyDescriptor.getName());
                result = hasMappingAnnotation(field) ? getMappingAnnotation(field) : null;
            } catch (NoSuchFieldException ignore) {
                // Search at getter
                if (propertyDescriptor.getReadMethod() != null) {
                    for (Annotation annotation : propertyDescriptor.getReadMethod().getAnnotations()) {
                        if (AnnotationMappingType.has(annotation.annotationType())) {
                            result = annotation;
                        }
                    }
                }
                // Search at setter
                if (propertyDescriptor.getWriteMethod() != null) {
                    for (Annotation annotation : propertyDescriptor.getWriteMethod().getAnnotations()) {
                        if (AnnotationMappingType.has(annotation.annotationType())) {
                            result = annotation;
                        }
                    }
                }
            }
        }
        return result;
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

    /**
     * Get the type of method.
     *
     * @param method    Method
     * @return  If method has parameters, returns the first parameter type.<br>
     *          If the method has return, get its type.
     */
    public static Class<?> getTypeOfMethod(Method method) {
        return method.getParameterTypes().length > 0 ? method.getParameterTypes()[0] : method.getReturnType();
    }

    /**
     * Build key name for mapping.
     *
     * @param fromClass Class A
     * @param toClass   Class B
     * @return  Key name: FromClassName>>ToClassName
     */
    public static String getMappingKeyName(final Class<?> fromClass, final Class<?> toClass) {
        if (fromClass == null || toClass == null) {
            return "";
        }
        return fromClass.getSimpleName() + ">>" + toClass.getSimpleName();
    }

    /**
     * Check if a class is a collection.
     *
     * @param clazz Class to verify
     * @return  True if it is a collection, false otherwise.
     */
    public static boolean isCollection(Class<?> clazz) {
        return CollectionTypes.contains(clazz);
    }

    /**
     * Check if a class is a map.
     *
     * @param clazz Class to verify
     * @return  True if it is a map, false otherwise.
     */
    public static boolean isMap(Class<?> clazz) {
        return MapTypes.contains(clazz);
    }

    /**
     * Get all generic types of a property type.
     *
     * @param beanClass     Bean class of property
     * @param propertyName  Name of the property whose type has generic types
     * @return  Array of generic types.
     */
    @NotNull
    public static Class<?>[] getAllGenericTypes(@NotNull Class<?> beanClass, String propertyName) {
        List<Class<?>> allGenericTypes = new ArrayList<>();
        try {
            Field field = beanClass.getDeclaredField(propertyName);
            if (MapperUtils.isCollection(field.getType()) || isMap(field.getType())) {
                ParameterizedType fieldParameterizedType = (ParameterizedType) field.getGenericType();
                for (Type type : fieldParameterizedType.getActualTypeArguments()) {
                    allGenericTypes.add((Class<?>) type);
                }
            }
        } catch (NoSuchFieldException e) {
            if (beanClass.getSuperclass() != null) {
                allGenericTypes = Arrays.asList(getAllGenericTypes(beanClass.getSuperclass(), propertyName));
            }
        }
        return allGenericTypes.toArray(new Class<?>[0]);
    }

    /**
     * Get all generic types of a property type.
     *
     * @param beanClass             Bean class of property
     * @param propertyDescriptor    Property descriptor whose type has generic types
     * @return  For collections, returns the only generic type. For maps, returns the generic type from value.
     */
    @Nullable
    private static Class<?> getElementType(@NotNull Class<?> beanClass, @NotNull java.beans.PropertyDescriptor propertyDescriptor) {
        Class<?>[] elementTypes = getAllGenericTypes(beanClass, propertyDescriptor.getName());
        if (elementTypes.length > 0) {
            if (isMap(propertyDescriptor.getPropertyType()) && elementTypes.length > 1) {
                return elementTypes[1];
            }
            return elementTypes[0];
        }
        return null;
    }
}
