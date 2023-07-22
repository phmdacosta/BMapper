package com.phmc.bmapper.builder;

import com.phmc.bmapper.BMapper;

public class BMapperBuilderHelper {
    private BMapper bMapper;
    private MappingDataBuilder mappingDataBuilder;

    public BMapper getBMapper() {
        return bMapper;
    }

    public void setBMapper(BMapper bMapper) {
        this.bMapper = bMapper;
    }

    public MappingDataBuilder getMappingDataBuilder() {
        return mappingDataBuilder;
    }

    public void setMappingDataBuilder(MappingDataBuilder mappingDataBuilder) {
        this.mappingDataBuilder = mappingDataBuilder;
    }
}
