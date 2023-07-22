package com.phmc.bmapper.resolver;

import com.phmc.bmapper.utils.MapperUtils;

public class DefaultInstanceResolver implements CollectionInstanceResolver<Object> {
    @Override
    public Object newInstance(Class<?> clazz) {
        return MapperUtils.getInstance(clazz);
    }
}
