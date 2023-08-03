package com.phmc.bmapper;

import org.jetbrains.annotations.NotNull;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class PropertyDescriptor extends java.beans.PropertyDescriptor {
    private Annotation propertyAnnotation;
    private Object beanInstance;

    public PropertyDescriptor(@NotNull java.beans.PropertyDescriptor propertyDescriptor, Object beanInstance, Annotation annotation) throws IntrospectionException {
        this(propertyDescriptor,
                beanInstance != null ? beanInstance.getClass() : null,
                beanInstance,
                annotation);
    }

    public PropertyDescriptor(@NotNull java.beans.PropertyDescriptor propertyDescriptor, Class<?> beanClass, Object beanInstance, Annotation annotation) throws IntrospectionException {
        this(propertyDescriptor.getName(),
                beanClass,
                propertyDescriptor.getReadMethod() != null ? propertyDescriptor.getReadMethod().getName() : null,
                propertyDescriptor.getWriteMethod() != null ? propertyDescriptor.getWriteMethod().getName() : null,
                annotation);
        this.beanInstance = beanInstance;
    }

    public PropertyDescriptor(String propertyName, Class<?> beanClass) throws IntrospectionException {
        super(propertyName, beanClass);
    }

    public PropertyDescriptor(String propertyName, Class<?> beanClass, String readMethodName, String writeMethodName) throws IntrospectionException {
        super(propertyName, beanClass, readMethodName, writeMethodName);
    }

    public PropertyDescriptor(String propertyName, Class<?> beanClass, String readMethodName, String writeMethodName, Annotation propertyAnnotation) throws IntrospectionException {
        super(propertyName, beanClass, readMethodName, writeMethodName);
        this.propertyAnnotation = propertyAnnotation;
    }

    public PropertyDescriptor(String propertyName, Method readMethod, Method writeMethod) throws IntrospectionException {
        super(propertyName, readMethod, writeMethod);
    }

    public PropertyDescriptor(String propertyName, Method readMethod, Method writeMethod, Annotation propertyAnnotation) throws IntrospectionException {
        super(propertyName, readMethod, writeMethod);
        this.propertyAnnotation = propertyAnnotation;
    }

    public Annotation getPropertyAnnotation() {
        return propertyAnnotation;
    }

    public Object getBeanInstance() {
        return beanInstance;
    }

    @Override
    public String toString() {
        return "{ "
                 + getName() +
                " }";
    }
}
