package com.phmc.bmapper.test.obj;

import java.util.Objects;

public class TestAnnotModel_E {
    private long id;
    private Double value;
    private TestAnnotModel_A testAnnotModelA;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public TestAnnotModel_A getTestAnnotModelA() {
        return testAnnotModelA;
    }

    public void setTestAnnotModelA(TestAnnotModel_A testAnnotModelA) {
        this.testAnnotModelA = testAnnotModelA;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TestAnnotModel_E)) return false;
        if (!super.equals(obj)) return false;
        TestAnnotModel_E testAnnotModelE = (TestAnnotModel_E) obj;
        return this.getId() == testAnnotModelE.getId()
                && this.getValue().equals(testAnnotModelE.getValue())
                && this.getTestAnnotModelA().equals(testAnnotModelE.getTestAnnotModelA());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getTestAnnotModelA());
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
