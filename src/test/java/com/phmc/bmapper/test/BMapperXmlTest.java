package com.phmc.bmapper.test;

import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.builder.BMapperBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BMapperXmlTest extends BMapperTest {
    @BeforeEach
    public void setUp() {
        this.bMapper = BMapperBuilder
                .init()
                .enableXmlMapping()
                .build();
    }

    @Override
    @Test
    public void test_viewMapping_commonFields_success() {
        super.test_viewMapping_commonFields_success();
    }

    @Override
    @Test
    public void test_modelMapping_commonFields_success() {
        super.test_modelMapping_commonFields_success();
    }

    @Override
    @Test
    public void test_bothMapping_collections_success() {
        super.test_bothMapping_collections_success();
    }

    @Override
    @Test
    public void test_viewMapping_annotation_success() {
        super.test_viewMapping_annotation_success();
    }

    @Override
    @Test
    public void test_modelMapping_annotation_success() {
        super.test_modelMapping_annotation_success();
    }

    @Override
    @Test
    public void test_bothMapping_annotation_collections_success() {
        super.test_bothMapping_annotation_collections_success();
    }

    @Override
    @Test
    public void test_bothMapping_sameClasses_success() {
        super.test_bothMapping_sameClasses_success();
    }

    @Override
    @Test
    public void test_viewCollectionMapping_fromList_commonFields_success() {
        super.test_viewCollectionMapping_fromList_commonFields_success();
    }

    @Override
    @Test
    public void test_viewCollectionMapping_fromSet_commonFields_success() {
        super.test_viewCollectionMapping_fromSet_commonFields_success();
    }

    @Override
    @Test
    public void test_modelCollectionMapping_fromList_commonFields_success() {
        super.test_modelCollectionMapping_fromList_commonFields_success();
    }

    @Override
    @Test
    public void test_modelCollectionMapping_fromSet_commonFields_success() {
        super.test_modelCollectionMapping_fromSet_commonFields_success();
    }

    @Override
    @Test
    public void test_viewMappingWithSuper_commonFields_success() {
        super.test_viewMappingWithSuper_commonFields_success();
    }

    @Override
    @Test
    public void test_viewMappingWithSuper_annotation_success() {
        super.test_viewMappingWithSuper_annotation_success();
    }
}
