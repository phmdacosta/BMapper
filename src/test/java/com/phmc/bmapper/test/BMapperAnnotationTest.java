package com.phmc.bmapper.test;

import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.builder.BMapperBuilder;
import com.phmc.bmapper.test.obj.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class BMapperAnnotationTest {
    protected BMapper bMapper;

    @BeforeEach
    public void setUp() {
        this.bMapper = BMapperBuilder
                .init()
                .enableAnnotationMapping(BMapper.class)
                .build();
    }

    @Test
    public void test_DTOMapping_commonFields_success() {
        long userId = 275L;

        TestAnnotModel_B testAnnotModel_B = new TestAnnotModel_B();
        testAnnotModel_B.setId(684L);
        testAnnotModel_B.setName("TestAnnotModel_B_1");
        testAnnotModel_B.setValue("Value_TestAnnotModel_B");

        TestAnnotModel_A testModelA = new TestAnnotModel_A();
        testModelA.setId(userId);
        testModelA.setModelAName("Test Model A");
        testModelA.getTestAnnotModelBList().add(testAnnotModel_B);

        TestAnnotModel_E model = new TestAnnotModel_E();
        model.setId(userId);
        model.setTestAnnotModelA(testModelA);
        model.setValue(8856D);

        TestAnnotModel_E_DtoRequest testModelDtoDRequest = bMapper.map(model, TestAnnotModel_E_DtoRequest.class);
        assertEquals(model.getTestAnnotModelA().getId(), testModelDtoDRequest.getTestAnnotModelAId());
        assertEquals(model.getValue(), testModelDtoDRequest.getVal());

        assertNotNull(testModelDtoDRequest);
        assertNotNull(testModelDtoDRequest.getTestAnnotModelAId());
        assertEquals(testModelDtoDRequest.getTestAnnotModelAId(), model.getTestAnnotModelA().getId());
        assertEquals(testModelDtoDRequest.getVal(), model.getValue());
    }

    @Test
    public void test_modelMapping_commonFields_success() {
        TestAnnotModel_E_DtoRequest testAnnotModelEDtoRequest = new TestAnnotModel_E_DtoRequest();
        testAnnotModelEDtoRequest.setTestAnnotModelAId(153L);
        testAnnotModelEDtoRequest.setVal(6534D);

        TestAnnotModel_E model = bMapper.map(testAnnotModelEDtoRequest, TestAnnotModel_E.class);
        assertNotNull(model);
        assertNotNull(model.getTestAnnotModelA());
        assertEquals(model.getTestAnnotModelA().getId(), testAnnotModelEDtoRequest.getTestAnnotModelAId());
        assertEquals(model.getValue(), testAnnotModelEDtoRequest.getVal());
    }

    @Test
    public void test_DTOMapping_collectionFields_success() {
        long modelAId = 153;
        double modelEVal = 6534D;
        String modelAName = "MODEL_A_" + modelAId;

        TestAnnotModel_C modelC1 = new TestAnnotModel_C();
        modelC1.setId(55L);
        modelC1.setName("MODEL_C_NAME_1");

        TestAnnotModel_C modelC2 = new TestAnnotModel_C();
        modelC2.setId(56L);
        modelC2.setName("MODEL_C_NAME_2");

        TestAnnotModel_C modelC3 = new TestAnnotModel_C();
        modelC3.setId(57L);
        modelC3.setName("MODEL_C_NAME_3");

        TestAnnotModel_B modelB1 = new TestAnnotModel_B();
        modelB1.setId(684L);
        modelB1.setName("MODEL_B_1");
        modelB1.setValue("MODEL_B_VAL_1");

        TestAnnotModel_B modelB2 = new TestAnnotModel_B();
        modelB2.setId(684L);
        modelB2.setName("MODEL_B_2");
        modelB2.setValue("MODEL_B_VAL_2");

        TestAnnotModel_B modelB3 = new TestAnnotModel_B();
        modelB3.setId(684L);
        modelB3.setName("MODEL_B_3");
        modelB3.setValue("MODEL_B_VAL_3");

        TestAnnotModel_A testModelA = new TestAnnotModel_A();
        testModelA.setId(modelAId);
        testModelA.setModelAName(modelAName);
        testModelA.getTestAnnotModelCSet().add(modelC1);
        testModelA.getTestAnnotModelCSet().add(modelC2);
        testModelA.getTestAnnotModelCSet().add(modelC3);
        testModelA.getTestAnnotModelBList().add(modelB1);
        testModelA.getTestAnnotModelBList().add(modelB2);
        testModelA.getTestAnnotModelBList().add(modelB3);

        TestAnnotModel_E testAnnotModelE = new TestAnnotModel_E();
        testAnnotModelE.setId(modelAId);
        testAnnotModelE.setValue(modelEVal);
        testAnnotModelE.setTestAnnotModelA(testModelA);

        TestAnnotModel_E_DtoResponse testModel_E_DtoResponse = bMapper.map(testAnnotModelE, TestAnnotModel_E_DtoResponse.class);

        // Assertions
        assertNotNull(testModel_E_DtoResponse);
        assertNotNull(testModel_E_DtoResponse.getTestAnnotModelA());
        assertNotNull(testModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelBList());
        assertNotNull(testModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelCSet());

        assertEquals(testModel_E_DtoResponse.getVal(), testAnnotModelE.getValue());
        assertEquals(testModel_E_DtoResponse.getTestAnnotModelA().getModelAName(), testAnnotModelE.getTestAnnotModelA().getModelAName());
        assertEquals(testModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelBList().size(), testAnnotModelE.getTestAnnotModelA().getTestAnnotModelBList().size());
        assertEquals(testModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelCSet().size(), testAnnotModelE.getTestAnnotModelA().getTestAnnotModelCSet().size());

        for (TestAnnotModel_B_Dto testModel_B_Dto : testModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelBList()) {
            TestAnnotModel_B found = testAnnotModelE.getTestAnnotModelA().getTestAnnotModelBList().stream()
                    .filter(contact -> contact.getName().equals(testModel_B_Dto.getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
            assertEquals(found.getName(), testModel_B_Dto.getName());
            assertEquals(found.getValue(), testModel_B_Dto.getValue());
        }

        for (TestAnnotModel_C_Dto testModel_C_Dto : testModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelCSet()) {
            TestAnnotModel_C found = testAnnotModelE.getTestAnnotModelA().getTestAnnotModelCSet().stream()
                    .filter(role -> role.getName().equals(testModel_C_Dto.getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
            assertEquals(found.getName(), testModel_C_Dto.getName());
        }
    }

    @Test
    public void test_modelMapping_fromResponse_collectionFields_success() {
        long modelAId = 153;
        double modelEVal = 6534D;
        String modelAName = "MODEL_A_" + modelAId;

        TestAnnotModel_C_DtoResponse modelCDto1 = new TestAnnotModel_C_DtoResponse();
        modelCDto1.setId(55L);
        modelCDto1.setName("MODEL_C_NAME_1");

        TestAnnotModel_C_DtoResponse modelCDto2 = new TestAnnotModel_C_DtoResponse();
        modelCDto2.setId(56L);
        modelCDto2.setName("MODEL_C_NAME_2");

        TestAnnotModel_C_DtoResponse modelCDto3 = new TestAnnotModel_C_DtoResponse();
        modelCDto3.setId(57L);
        modelCDto3.setName("MODEL_C_NAME_3");

        TestAnnotModel_B_DtoResponse modelBDto1 = new TestAnnotModel_B_DtoResponse();
        modelBDto1.setName("MODEL_B_1");
        modelBDto1.setValue("MODEL_B_VAL_1");

        TestAnnotModel_B_DtoResponse modelBDto2 = new TestAnnotModel_B_DtoResponse();
        modelBDto2.setName("MODEL_B_2");
        modelBDto2.setValue("MODEL_B_VAL_2");

        TestAnnotModel_B_DtoResponse modelBDto3 = new TestAnnotModel_B_DtoResponse();
        modelBDto3.setName("MODEL_B_3");
        modelBDto3.setValue("MODEL_B_VAL_3");

        TestAnnotModel_A_DtoResponse testAnnotModel_A_DtoResponse = new TestAnnotModel_A_DtoResponse();
        testAnnotModel_A_DtoResponse.setModelAName(modelAName);
        testAnnotModel_A_DtoResponse.setBool1(true);
        testAnnotModel_A_DtoResponse.setBool2(false);
        testAnnotModel_A_DtoResponse.setTestAnnotModelBList(new ArrayList<>());
        testAnnotModel_A_DtoResponse.getTestAnnotModelBList().add(modelBDto1);
        testAnnotModel_A_DtoResponse.getTestAnnotModelBList().add(modelBDto2);
        testAnnotModel_A_DtoResponse.getTestAnnotModelBList().add(modelBDto3);
        testAnnotModel_A_DtoResponse.setTestAnnotModelCSet(new HashSet<>());
        testAnnotModel_A_DtoResponse.getTestAnnotModelCSet().add(modelCDto1);
        testAnnotModel_A_DtoResponse.getTestAnnotModelCSet().add(modelCDto2);
        testAnnotModel_A_DtoResponse.getTestAnnotModelCSet().add(modelCDto3);

        TestAnnotModel_E_DtoResponse testAnnotModel_E_DtoResponse = new TestAnnotModel_E_DtoResponse();
        testAnnotModel_E_DtoResponse.setVal(modelEVal);
        testAnnotModel_E_DtoResponse.setTestAnnotModelA(testAnnotModel_A_DtoResponse);

        TestAnnotModel_E testAnnotModelE = bMapper.map(testAnnotModel_E_DtoResponse, TestAnnotModel_E.class);

        // Assertions
        assertNotNull(testAnnotModelE);
        assertNotNull(testAnnotModelE.getTestAnnotModelA());
        assertNotNull(testAnnotModelE.getTestAnnotModelA().getTestAnnotModelBList());
        assertNotNull(testAnnotModelE.getTestAnnotModelA().getTestAnnotModelCSet());

        assertEquals(testAnnotModel_E_DtoResponse.getVal(), testAnnotModelE.getValue());
        assertEquals(testAnnotModel_E_DtoResponse.getTestAnnotModelA().getModelAName(), testAnnotModelE.getTestAnnotModelA().getModelAName());
        assertEquals(testAnnotModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelBList().size(), testAnnotModelE.getTestAnnotModelA().getTestAnnotModelBList().size());
        assertEquals(testAnnotModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelCSet().size(), testAnnotModelE.getTestAnnotModelA().getTestAnnotModelCSet().size());

        for (TestAnnotModel_B testModel_B : testAnnotModelE.getTestAnnotModelA().getTestAnnotModelBList()) {
            TestAnnotModel_B_Dto found = testAnnotModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelBList().stream()
                    .filter(contact -> contact.getName().equals(testModel_B.getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
            assertEquals(testModel_B.getName(), found.getName());
            assertEquals(testModel_B.getValue(), found.getValue());
        }

        for (TestAnnotModel_C testModel_C : testAnnotModelE.getTestAnnotModelA().getTestAnnotModelCSet()) {
            TestAnnotModel_C_Dto found = testAnnotModel_E_DtoResponse.getTestAnnotModelA().getTestAnnotModelCSet().stream()
                    .filter(role -> role.getName().equals(testModel_C.getName()))
                    .findFirst().orElse(null);
            assertNotNull(found);
            assertEquals(testModel_C.getName(), found.getName());
        }
    }
}
