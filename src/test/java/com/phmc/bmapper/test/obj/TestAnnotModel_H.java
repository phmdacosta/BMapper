package com.phmc.bmapper.test.obj;

import java.util.Objects;

public class TestAnnotModel_H {
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
        if (!(o instanceof TestAnnotModel_H)) return false;
        TestAnnotModel_H that = (TestAnnotModel_H) o;
        return isBool() == that.isBool() && Objects.equals(getStr(), that.getStr()) && Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStr(), isBool(), getNumber());
    }
}
