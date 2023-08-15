package com.phmc.bmapper.builder;

import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.utils.MappingContext;
import com.phmc.bmapper.utils.MappingType;
import org.springframework.context.ApplicationContext;

/**
 * Component responsible for creating the BMapper, with which it is possible to configure the mapper.
 */
public class BMapperBuilder {

    private BMapperBuilderHelper helper;
    private boolean lazyMappings;

    // STATIC METHODS

    /**
     * Initiate the builder.
     *
     * @return builder
     */
    public static BMapperBuilder init() {
        BMapperBuilder builder = new BMapperBuilder();
        builder.initHelper();
        return builder;
    }

    public static void loadMappingProps(BMapper mapper) {
        if (mapper.getMappingDataBuilder() != null) {
            mapper.getMappingDataBuilder().initMappingProps();
        }
    }

    // NOT STATIC METHODS

    /**
     * Enable mapping through XML files.
     *
     * @return builder
     */
    public BMapperBuilder enableXmlMapping() {
        this.helper.getMappingDataBuilder().addMappingTypes(MappingType.XML);
        return this;
    }

    /**
     * Enable mapping through Annotations on classes.
     *
     * @param context   Context to assist in searching for the annotated classes
     * @return builder
     */
    public BMapperBuilder enableAnnotationMapping(final ApplicationContext context) {
        MappingContext mappingContext = new MappingContext();
        mappingContext.setContext(context);
        return enableAnnotationMapping(mappingContext);
    }

    /**
     * Enable mapping through Annotations on classes.
     *
     * @param mainClass Project's main class ti extract the context to assist in searching for the annotated classes
     * @return builder
     */
    public BMapperBuilder enableAnnotationMapping(final Class<?> mainClass) {
        MappingContext mappingContext = new MappingContext();
        mappingContext.setMainClass(mainClass);
        return enableAnnotationMapping(mappingContext);
    }

    /**
     * Enable mapping through Annotations on classes.
     *
     * @param targetPackage Annotated classes package to assist in searching
     * @return builder
     */
    public BMapperBuilder enableAnnotationMapping(final Package targetPackage) {
        MappingContext mappingContext = new MappingContext();
        mappingContext.setTargetPackage(targetPackage);
        return enableAnnotationMapping(mappingContext);
    }

    /**
     * BMapper, by default, builds all mappings defined through annotations or XML files (if these are enabled). <br>
     * This method serves to tell BMapper that it needs to load the mappings manually later. <br>
     * To load the mappings manually use method {@linkplain BMapperBuilder#loadMappingProps(BMapper)}
     *
     * @return builder
     */
    public BMapperBuilder lazyMappings() {
        this.lazyMappings = true;
        return this;
    }

    /**
     * Build BMapper object.
     *
     * @return New BMapper object
     */
    public BMapper build() {
        if (!lazyMappings) {
            this.helper.getMappingDataBuilder().initMappingProps();
        }
        BMapper mapper = new BMapper();
        mapper.setMappingDataBuilder(helper.getMappingDataBuilder());
        return mapper;
    }

    // PRIVATE METHODS

    private void initHelper() {
        if (this.helper == null) {
            this.helper = new BMapperBuilderHelper();
        }
        if (this.helper.getMappingDataBuilder() == null) {
            this.helper.setMappingDataBuilder(new MappingDataBuilder());
        }
    }

    private BMapperBuilder enableAnnotationMapping(final MappingContext mappingContext) {
        this.helper.getMappingDataBuilder().setMappingContext(mappingContext);
        helper.getMappingDataBuilder().addMappingTypes(MappingType.ANNOTATION);
        return this;
    }
}
