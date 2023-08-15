package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;

import java.util.List;
import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_C.class)
public class TestAnnotModel_C_DtoRequest extends TestAnnotModel_C_Dto {
    private List<TestAnnotModel_A_DtoRequest> testAnnotModelAList;
    private List<TestAnnotModel_D_DtoRequest> testAnnotModelDList;

    public List<TestAnnotModel_A_DtoRequest> getTestAnnotModelAList() {
        return testAnnotModelAList;
    }

    public void setTestAnnotModelAList(List<TestAnnotModel_A_DtoRequest> testAnnotModelAList) {
        this.testAnnotModelAList = testAnnotModelAList;
    }

    public List<TestAnnotModel_D_DtoRequest> getTestAnnotModelDList() {
        return testAnnotModelDList;
    }

    public void setTestAnnotModelDList(List<TestAnnotModel_D_DtoRequest> testAnnotModelDList) {
        this.testAnnotModelDList = testAnnotModelDList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_C_DtoRequest)) return false;
        TestAnnotModel_C_DtoRequest that = (TestAnnotModel_C_DtoRequest) o;
        return super.equals(o)
                && Objects.equals(getTestAnnotModelAList(), that.getTestAnnotModelAList())
                && Objects.equals(getTestAnnotModelDList(), that.getTestAnnotModelDList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTestAnnotModelAList(), getTestAnnotModelDList());
    }
}
