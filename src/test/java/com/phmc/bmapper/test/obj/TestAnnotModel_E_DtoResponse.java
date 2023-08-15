package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.annotation.MappingField;

import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_E.class)
public class TestAnnotModel_E_DtoResponse {
    @MappingField(name = "value")
    private Double val;
    @MappingField(name = "testAnnotModelA")
    private TestAnnotModel_A_DtoResponse testAnnotModelA;

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public TestAnnotModel_A_DtoResponse getTestAnnotModelA() {
        return testAnnotModelA;
    }

    public void setTestAnnotModelA(TestAnnotModel_A_DtoResponse testAnnotModelA) {
        this.testAnnotModelA = testAnnotModelA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_E_DtoResponse)) return false;
        TestAnnotModel_E_DtoResponse that = (TestAnnotModel_E_DtoResponse) o;
        return Objects.equals(getVal(), that.getVal()) && Objects.equals(getTestAnnotModelA(), that.getTestAnnotModelA());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVal(), getTestAnnotModelA());
    }

    @Override
    public String toString() {
        return "ScoreDtoResponse{" +
                "score=" + val +
                ", user=" + testAnnotModelA +
                '}';
    }
}
