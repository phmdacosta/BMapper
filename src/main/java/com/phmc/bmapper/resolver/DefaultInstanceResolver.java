package com.phmc.bmapper.resolver;

import com.phmc.bmapper.MapperUtils;

public class DefaultInstanceResolver implements CollectionInstanceResolver<Object> {
    @Override
    public Object newInstance(Class<?> clazz) {
        return MapperUtils.getInstance(clazz);
    }
}
