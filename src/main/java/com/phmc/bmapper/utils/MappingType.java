package com.phmc.bmapper.utils;

import com.phmc.bmapper.annotation.AnnotationMappingLoader;
import com.phmc.bmapper.xml.XmlMappingLoader;

public enum MappingType {
    ANNOTATION(new AnnotationMappingLoader()),
    XML(new XmlMappingLoader());

    private MappingLoader loader;

    MappingType(MappingLoader loader) {
        this.loader = loader;
    }

    public MappingLoader getLoader() {
        return this.loader;
    }
}
