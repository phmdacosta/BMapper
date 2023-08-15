package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.annotation.MappingField;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_E.class)
public class TestAnnotModel_E_DtoRequest {
    @MappingField(name = "value")
    private Double val;
    @Required
    @MappingField(name = "testAnnotModelA.id")
    private Long testAnnotModelAId;

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public Long getTestAnnotModelAId() {
        return testAnnotModelAId;
    }

    public void setTestAnnotModelAId(Long testAnnotModelAId) {
        this.testAnnotModelAId = testAnnotModelAId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_E_DtoRequest)) return false;
        TestAnnotModel_E_DtoRequest that = (TestAnnotModel_E_DtoRequest) o;
        return Objects.equals(getVal(), that.getVal()) && Objects.equals(getTestAnnotModelAId(), that.getTestAnnotModelAId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVal(), getTestAnnotModelAId());
    }

    @Override
    public String toString() {
        return "ScoreDtoRequest{" +
                "score=" + val +
                ", userId=" + testAnnotModelAId +
                '}';
    }
}
