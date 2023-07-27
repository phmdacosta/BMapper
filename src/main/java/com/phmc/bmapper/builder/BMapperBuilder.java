package com.phmc.bmapper.builder;

import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.utils.MappingContext;
import com.phmc.bmapper.utils.MappingType;
import org.springframework.context.ApplicationContext;

public class BMapperBuilder {

    private BMapperBuilderHelper helper;
    private boolean lazyMappings;

    // STATIC METHODS
    public static BMapperBuilder init(final ApplicationContext context) {
        BMapperBuilder builder = new BMapperBuilder();
        builder.helper = builder.initHelper(context);
        return builder;
    }

    public static BMapperBuilder init(final Class<?> mainClass) {
        BMapperBuilder builder = new BMapperBuilder();
        builder.helper = builder.initHelper(mainClass);
        return builder;
    }

    public static BMapperBuilder init(final Package targetPackage) {
        BMapperBuilder builder = new BMapperBuilder();
        builder.helper = builder.initHelper(targetPackage);
        return builder;
    }

    public static BMapperBuilder init(final MappingContext mappingContext) {
        BMapperBuilder builder = new BMapperBuilder();
        builder.helper = builder.initHelper(mappingContext);
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

    private BMapperBuilderHelper initHelper(final ApplicationContext context) {
        MappingContext mappingContext = new MappingContext();
        mappingContext.setContext(context);
        return initHelper(mappingContext);
    }

    private BMapperBuilderHelper initHelper(final Class<?> mainClass) {
        MappingContext mappingContext = new MappingContext();
        mappingContext.setMainClass(mainClass);
        return initHelper(mappingContext);
    }

    private BMapperBuilderHelper initHelper(final Package targetPackage) {
        MappingContext mappingContext = new MappingContext();
        mappingContext.setTargetPackage(targetPackage);
        return initHelper(mappingContext);
    }

    private BMapperBuilderHelper initHelper(final MappingContext mappingContext) {
        BMapperBuilderHelper helper = new BMapperBuilderHelper();
        helper.setMappingDataBuilder(new MappingDataBuilder());
        helper.getMappingDataBuilder().setMappingContext(mappingContext);
        helper.getMappingDataBuilder().addMappingTypes(MappingType.ANNOTATION);
        return helper;
    }
}
