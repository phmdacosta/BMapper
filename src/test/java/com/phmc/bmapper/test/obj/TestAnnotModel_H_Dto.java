package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;

import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_H.class)
public class TestAnnotModel_H_Dto {
    private String str;
    private boolean bool;
    private Double number;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_H_Dto)) return false;
        TestAnnotModel_H_Dto that = (TestAnnotModel_H_Dto) o;
        return isBool() == that.isBool() && Objects.equals(getStr(), that.getStr()) && Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStr(), isBool(), getNumber());
    }
}
