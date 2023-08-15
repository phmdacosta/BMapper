package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.annotation.MappingField;

import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_B.class)
public class TestAnnotModel_B_Dto {
    @MappingField(name = "name")
    private String name;
    @MappingField(name = "value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_B_Dto)) return false;
        TestAnnotModel_B_Dto that = (TestAnnotModel_B_Dto) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue());
    }
}
