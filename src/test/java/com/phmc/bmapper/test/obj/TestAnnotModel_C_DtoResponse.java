package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;

import java.util.List;
import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_C.class)
public class TestAnnotModel_C_DtoResponse extends TestAnnotModel_C_Dto {
    private List<TestAnnotModel_A_DtoResponse> testAnnotModelAList;
    private List<TestAnnotModel_D_DtoResponse> testAnnotModelDList;

    public List<TestAnnotModel_A_DtoResponse> getTestAnnotModelAList() {
        return testAnnotModelAList;
    }

    public void setTestAnnotModelAList(List<TestAnnotModel_A_DtoResponse> testAnnotModelAList) {
        this.testAnnotModelAList = testAnnotModelAList;
    }

    public List<TestAnnotModel_D_DtoResponse> getTestAnnotModelDList() {
        return testAnnotModelDList;
    }

    public void setTestAnnotModelDList(List<TestAnnotModel_D_DtoResponse> testAnnotModelDList) {
        this.testAnnotModelDList = testAnnotModelDList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_C_DtoResponse)) return false;
        TestAnnotModel_C_DtoResponse that = (TestAnnotModel_C_DtoResponse) o;
        return super.equals(o)
                && Objects.equals(getTestAnnotModelAList(), that.getTestAnnotModelAList())
                && Objects.equals(getTestAnnotModelDList(), that.getTestAnnotModelDList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTestAnnotModelAList(), getTestAnnotModelDList());
    }
}
