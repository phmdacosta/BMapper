package com.phmc.bmapper.builder;

import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.utils.MappingType;
import org.springframework.context.ApplicationContext;

public class BMapperBuilder {

    private BMapperBuilderHelper helper;
    private boolean lazyMappings;

    // STATIC METHODS
    public static BMapperBuilder init(ApplicationContext context) {
        BMapperBuilder builder = new BMapperBuilder();
        builder.helper = builder.initHelper(context);
        return builder;
    }

    public static BMapperBuilder init(Class<?> mainClass) {
        BMapperBuilder builder = new BMapperBuilder();
        builder.helper = builder.initHelper(mainClass);
        return builder;
    }

    public static void loadMappingProps(BMapper mapper) {
        if (mapper.getMappingDataBuilder() != null) {
            mapper.getMappingDataBuilder().initMappingProps();
        }
    }

    // NOT STATIC METHODS
    public BMapperBuilder enableXmlMapping() {
        this.helper.getMappingDataBuilder().addMappingTypes(MappingType.XML);
        return this;
    }

    public BMapperBuilder disableAnnotationMapping() {
        this.helper.getMappingDataBuilder().removeMappingTypes(MappingType.ANNOTATION);
        return this;
    }

    public BMapperBuilder lazyMappings() {
        this.lazyMappings = true;
        return this;
    }

    public BMapper build() {
        if (!lazyMappings) {
            this.helper.getMappingDataBuilder().initMappingProps();
        }
        BMapper mapper = new BMapper();
        mapper.setMappingDataBuilder(helper.getMappingDataBuilder());
        return mapper;
    }

    private BMapperBuilderHelper initHelper(ApplicationContext context) {
        BMapperBuilderHelper helper = new BMapperBuilderHelper();
        helper.setMappingDataBuilder(new MappingDataBuilder());
        helper.getMappingDataBuilder().setContext(context);
        helper.getMappingDataBuilder().addMappingTypes(MappingType.ANNOTATION);
        return helper;
    }

    private BMapperBuilderHelper initHelper(Class<?> mainClass) {
        BMapperBuilderHelper helper = new BMapperBuilderHelper();
        helper.setMappingDataBuilder(new MappingDataBuilder());
        helper.getMappingDataBuilder().setMainClass(mainClass);
        helper.getMappingDataBuilder().addMappingTypes(MappingType.ANNOTATION);
        return helper;
    }
}
