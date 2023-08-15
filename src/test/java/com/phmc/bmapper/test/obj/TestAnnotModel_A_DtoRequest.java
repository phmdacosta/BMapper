package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.annotation.MappingField;

import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_A.class)
public class TestAnnotModel_A_DtoRequest extends TestAnnotModel_A_Dto {
    @MappingField(name = "id")
    private Long id;
    @MappingField(name = "str1")
    private String str1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_A_DtoRequest)) return false;
        if (!super.equals(o)) return false;
        TestAnnotModel_A_DtoRequest that = (TestAnnotModel_A_DtoRequest) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getStr1(), that.getStr1())
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getStr1());
    }
}
