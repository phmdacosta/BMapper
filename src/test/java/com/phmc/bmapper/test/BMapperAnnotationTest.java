package com.phmc.bmapper.test;

import com.phmc.bmapper.BMapper;
import com.phmc.bmapper.builder.BMapperBuilder;
import com.phmc.bmapper.test.obj.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        long model_A_ID = 568L;

        // TestAnnotModel_B DTO creation
        TestAnnotModel_B_DtoRequest model_B_Request = new TestAnnotModel_B_DtoRequest();
        model_B_Request.setName("TestAnnotModel B");
        model_B_Request.setValue("Value TestAnnotModel B");

        // TestAnnotModel_A DTO creation
        TestAnnotModel_A_DtoRequest model_A_Request = new TestAnnotModel_A_DtoRequest();
        model_A_Request.setId(model_A_ID);
        model_A_Request.setModelAName("Model A");
        model_A_Request.setStr1("Model A STR");
        model_A_Request.setTestAnnotModelBList(new ArrayList<>());
        model_A_Request.getTestAnnotModelBList().add(model_B_Request);
        model_A_Request.setTestAnnotModelCSet(new HashSet<>());

        // TestAnnotModel_D DTO creation
        TestAnnotModel_D_DtoRequest model_D_Request = new TestAnnotModel_D_DtoRequest();
        model_D_Request.setId(model_A_ID);
        model_D_Request.setName("Model D");
        model_D_Request.setRoute("ROUTE D");
        model_D_Request.setTestAnnotModelCList(new ArrayList<>());

        // TestAnnotModel_C DTO creation
        TestAnnotModel_C_DtoRequest model_C_Request = new TestAnnotModel_C_DtoRequest();
        model_C_Request.setId(model_A_ID);
        model_C_Request.setName("Model C");
        model_C_Request.setTestAnnotModelAList(new ArrayList<>());
        model_C_Request.getTestAnnotModelAList().add(model_A_Request);
        model_C_Request.setTestAnnotModelDList(new ArrayList<>());
        model_C_Request.getTestAnnotModelDList().add(model_D_Request);

        TestAnnotModel_C model_C = bMapper.map(model_C_Request, TestAnnotModel_C.class);

        // Assertions Model C
        assertNotNull(model_C);
        assertEquals(model_C_Request.getId(), model_C.getId());
        assertEquals(model_C_Request.getName(), model_C.getName());
        assertNotNull(model_C.getTestAnnotModelAList());
        assertNotEquals(0, model_C.getTestAnnotModelAList().size());
        assertNotNull(model_C.getTestAnnotModelDList());
        assertNotEquals(0, model_C_Request.getTestAnnotModelDList().size());

        // Assertions Model A
        TestAnnotModel_A model_A = model_C.getTestAnnotModelAList().iterator().next();
        assertEquals(model_A_Request.getId(), model_A.getId());
        assertEquals(model_A_Request.getModelAName(), model_A.getModelAName());
        assertEquals(model_A_Request.getStr1(), model_A.getStr1());
        assertNotNull(model_A.getTestAnnotModelBList());
        assertNotEquals(0, model_A.getTestAnnotModelBList().size());
        assertNotNull(model_A.getTestAnnotModelCSet());

        // Assertions Model B
        TestAnnotModel_B model_B = model_A.getTestAnnotModelBList().iterator().next();
        assertEquals(model_B_Request.getName(), model_B.getName());
        assertEquals(model_B_Request.getValue(), model_B.getValue());

        // Assertions Model D
        TestAnnotModel_D model_D = model_C.getTestAnnotModelDList().iterator().next();
        assertEquals(model_D_Request.getId(), model_D.getId());
        assertEquals(model_D_Request.getName(), model_D.getName());
        assertEquals(model_D_Request.getRoute(), model_D.getRoute());
        assertNotNull(model_D.getTestAnnotModelCList());
        assertNotEquals(0, model_D.getTestAnnotModelCList().size());
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

    @Test
    public void test_DTOMapping_unmodifiableCollectionFields_success() {
        TestAnnotModel_H testAnnotModelH_1 = new TestAnnotModel_H();
        testAnnotModelH_1.setStr("MODEL_H_STR_1");
        testAnnotModelH_1.setBool(true);
        testAnnotModelH_1.setNumber(5684D);

        TestAnnotModel_H testAnnotModelH_2 = new TestAnnotModel_H();
        testAnnotModelH_2.setStr("MODEL_H_STR_2");
        testAnnotModelH_2.setBool(true);
        testAnnotModelH_2.setNumber(5684D);

        TestAnnotModel_H testAnnotModelH_3 = new TestAnnotModel_H();
        testAnnotModelH_3.setStr("MODEL_H_STR_3");
        testAnnotModelH_3.setBool(true);
        testAnnotModelH_3.setNumber(5684D);

        List<TestAnnotModel_H> list = new ArrayList<>();
        list.add(testAnnotModelH_1);
        list.add(testAnnotModelH_2);
        list.add(testAnnotModelH_3);

        Set<TestAnnotModel_H> set = new HashSet<>();
        set.add(testAnnotModelH_1);
        set.add(testAnnotModelH_2);
        set.add(testAnnotModelH_3);

        TestAnnotModel_F testAnnotModelF = new TestAnnotModel_F();
        testAnnotModelF.setUnmodifiableList(Collections.unmodifiableList(list));
        testAnnotModelF.setUnmodifiableSet(Collections.unmodifiableSet(set));

        TestAnnotModel_F_Dto testAnnotModel_F_Dto = bMapper.map(testAnnotModelF, TestAnnotModel_F_Dto.class);

        assertNotNull(testAnnotModel_F_Dto);
        assertFalse(testAnnotModel_F_Dto.getUnmodifiableList().isEmpty());
        assertEquals(list.size(), testAnnotModel_F_Dto.getUnmodifiableList().size());

        assertFalse(testAnnotModel_F_Dto.getUnmodifiableSet().isEmpty());
        assertEquals(set.size(), testAnnotModel_F_Dto.getUnmodifiableSet().size());

        for (TestAnnotModel_H_Dto testModel_H_Dto : testAnnotModel_F_Dto.getUnmodifiableList()) {
            TestAnnotModel_H found = testAnnotModelF.getUnmodifiableList().stream()
                    .filter(modelH -> modelH.getStr().equals(testModel_H_Dto.getStr())
                            && modelH.getNumber().equals(testModel_H_Dto.getNumber())
                            && (modelH.isBool() && testModel_H_Dto.isBool()))
                    .findFirst().orElse(null);
            assertNotNull(found);
        }

        for (TestAnnotModel_H_Dto testModel_H_Dto : testAnnotModel_F_Dto.getUnmodifiableSet()) {
            TestAnnotModel_H found = testAnnotModelF.getUnmodifiableSet().stream()
                    .filter(modelH -> modelH.getStr().equals(testModel_H_Dto.getStr())
                            && modelH.getNumber().equals(testModel_H_Dto.getNumber())
                            && (modelH.isBool() && testModel_H_Dto.isBool()))
                    .findFirst().orElse(null);
            assertNotNull(found);
        }
    }
}
