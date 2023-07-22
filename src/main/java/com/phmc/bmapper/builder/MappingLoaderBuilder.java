package com.phmc.bmapper.builder;

import com.phmc.bmapper.utils.MappingLoader;
import com.phmc.bmapper.utils.MappingType;

public class MappingLoaderBuilder {
    public MappingLoader build(MappingType mappingType) {
        return mappingType.getLoader();
    }
}
