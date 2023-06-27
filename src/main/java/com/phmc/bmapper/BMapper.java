package com.phmc.bmapper;

import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.builder.ViewMapperBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BMapper implements ApplicationContextAware {

    private ViewMapperBuilder builder;
    private ApplicationContext context;
    private MappingDataHelper mappingDataHelper;

    public BMapper() {
        this.builder = new ViewMapperBuilder();
    }

    public ViewMapperBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(ViewMapperBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        if (mappingDataHelper == null) {
            initMappingProps(context);
        }
    }

    public MappingDataHelper getMappingDataHelper() {
        return mappingDataHelper;
    }

    public void setMappingDataHelper(MappingDataHelper mappingDataHelper) {
        this.mappingDataHelper = mappingDataHelper;
    }

    /**
     * Map a model to a view, and vice versa.
     *
     * @param from      Object to map
     * @param toClass   Class to the target result
     * @param <FROM> Type of the object to map
     * @param <TO>   Type of the result
     */
    @SuppressWarnings("unchecked")
    public <FROM, TO> TO map(FROM from, Class<TO> toClass) {
        if (mappingDataHelper == null) {
            initMappingProps(context);
        }

        TypeMapper<FROM, TO> mapper = (TypeMapper<FROM, TO>) new ViewMapperBuilder().create(from.getClass(), toClass);
        return mapper.doMapping(mappingDataHelper, from, toClass);
    }

    @SuppressWarnings("unchecked")
    public <ELEM, FROM extends Collection<ELEM>, TO> Collection<TO> map(FROM from, Class<TO> toClass) {
        if (mappingDataHelper == null) {
            initMappingProps(context);
        }

        CollectionMapper<FROM, TO> mapper = (CollectionMapper<FROM, TO>) new ViewMapperBuilder().create(from.getClass(), Collection.class);
        return (Collection<TO>) mapper.doMapping(mappingDataHelper, from, toClass);
    }

    private void initMappingProps(final ApplicationContext context) {
        this.mappingDataHelper = new MappingDataHelper();
        this.mappingDataHelper.initMappingProps(context);
    }
}
