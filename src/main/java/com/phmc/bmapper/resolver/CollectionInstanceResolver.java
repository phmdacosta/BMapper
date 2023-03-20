package com.phmc.bmapper.resolver;

public interface CollectionInstanceResolver<T> {
    T newInstance(Class<?> clazz);
}
