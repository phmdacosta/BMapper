package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.annotation.MappingCollection;
import com.phmc.bmapper.annotation.MappingField;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@MappingClass(targetClass = TestAnnotModel_A.class)
public class TestAnnotModel_A_Dto {
    @MappingField(name = "modelAName")
    private String modelAName;
    @MappingCollection(name = "testAnnotModelBList", elementType = TestAnnotModel_B.class)
    private List<TestAnnotModel_B_Dto> testAnnotModelBList;
    @MappingCollection(name = "testAnnotModelCSet", elementType = TestAnnotModel_C.class)
    private Set<TestAnnotModel_C_Dto> testAnnotModelCSet;

    public String getModelAName() {
        return modelAName;
    }

    public void setModelAName(String modelAName) {
        this.modelAName = modelAName;
    }

    public List<TestAnnotModel_B_Dto> getTestAnnotModelBList() {
        return testAnnotModelBList;
    }

    public void setTestAnnotModelBList(List<TestAnnotModel_B_Dto> testAnnotModelBList) {
        this.testAnnotModelBList = testAnnotModelBList;
    }

    public Set<TestAnnotModel_C_Dto> getTestAnnotModelCSet() {
        return testAnnotModelCSet;
    }

    public void setTestAnnotModelCSet(Set<TestAnnotModel_C_Dto> testAnnotModelCSet) {
        this.testAnnotModelCSet = testAnnotModelCSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_A_Dto)) return false;
        TestAnnotModel_A_Dto testAnnotModelADto = (TestAnnotModel_A_Dto) o;
        return Objects.equals(getModelAName(), testAnnotModelADto.getModelAName())
                && Objects.equals(getTestAnnotModelBList(), testAnnotModelADto.getTestAnnotModelBList())
                && Objects.equals(getTestAnnotModelCSet(), testAnnotModelADto.getTestAnnotModelCSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModelAName(), getTestAnnotModelBList(), getTestAnnotModelCSet());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "modelAName='" + modelAName +
                "'}";
    }
}
