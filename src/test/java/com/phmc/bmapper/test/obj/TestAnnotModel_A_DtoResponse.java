package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;

import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_A.class)
public class TestAnnotModel_A_DtoResponse extends TestAnnotModel_A_Dto {
    private boolean bool1;
    private boolean bool2;

    public boolean isBool1() {
        return bool1;
    }

    public void setBool1(boolean bool1) {
        this.bool1 = bool1;
    }

    public boolean isBool2() {
        return bool2;
    }

    public void setBool2(boolean bool2) {
        this.bool2 = bool2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_A_DtoResponse)) return false;
        if (!super.equals(o)) return false;
        TestAnnotModel_A_DtoResponse that = (TestAnnotModel_A_DtoResponse) o;
        return isBool1() == that.isBool1() && isBool2() == that.isBool2() && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isBool1(), isBool2());
    }
}
