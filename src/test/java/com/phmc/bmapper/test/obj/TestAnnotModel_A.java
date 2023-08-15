package com.phmc.bmapper.test.obj;

import java.util.*;

public class TestAnnotModel_A extends TestAnnotModel_X implements Cloneable {
    private String modelAName;
    private String str1;
    private boolean bool1 = false;
    private boolean bool2 = false;
    private boolean bool3 = false;

    //Foreign objects
    private List<TestAnnotModel_B> testAnnotModelBList;
    private Set<TestAnnotModel_C> testAnnotModelCSet;

    public TestAnnotModel_A() {
        this.testAnnotModelBList = new ArrayList<>();
        this.testAnnotModelCSet = new HashSet<>();
    }

    public String getModelAName() {
        return modelAName;
    }

    public void setModelAName(String modelAName) {
        this.modelAName = modelAName;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public boolean isAccountNonExpired() {
        return !bool1;
    }

    public boolean isAccountNonLocked() {
        return !bool2;
    }

    public boolean isBool3() {
        return bool3;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public List<TestAnnotModel_B> getTestAnnotModelBList() {
        return testAnnotModelBList;
    }

    public void setTestAnnotModelBList(List<TestAnnotModel_B> testAnnotModelBList) {
        this.testAnnotModelBList = testAnnotModelBList;
    }

    public void addTestAnnotModelB(TestAnnotModel_B contact) {
        this.testAnnotModelBList.add(contact);
    }

    public void setTestAnnotModelCSet(Set<TestAnnotModel_C> testAnnotModelACS) {
        this.testAnnotModelCSet = testAnnotModelACS;
    }

    public Set<TestAnnotModel_C> getTestAnnotModelCSet() {
        return testAnnotModelCSet;
    }

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

    public void setBool3(boolean bool3) {
        this.bool3 = bool3;
    }

    @Override
    public TestAnnotModel_A clone() throws CloneNotSupportedException {
        TestAnnotModel_A clone = (TestAnnotModel_A) super.clone();
        clone.setTestAnnotModelBList(new ArrayList<>(this.getTestAnnotModelBList()));
        clone.setTestAnnotModelCSet(new HashSet<>(this.getTestAnnotModelCSet()));
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_A)) return false;
        if (!super.equals(o)) return false;
        TestAnnotModel_A testAnnotModelA = (TestAnnotModel_A) o;
        return isBool1() == testAnnotModelA.isBool1()
                && isBool2() == testAnnotModelA.isBool2()
                && isBool3() == testAnnotModelA.isBool3()
                && Objects.equals(getModelAName(), testAnnotModelA.getModelAName())
                && Objects.equals(getStr1(), testAnnotModelA.getStr1())
                && Objects.equals(getTestAnnotModelBList(), testAnnotModelA.getTestAnnotModelBList())
                && Objects.equals(getTestAnnotModelCSet(), testAnnotModelA.getTestAnnotModelCSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(), getModelAName(), getStr1(), isBool1(),
                isBool2(), isBool3(), getTestAnnotModelBList(), getTestAnnotModelCSet());
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", modelAName='" + modelAName + '\'' +
                ", bool1=" + bool1 +
                ", bool2=" + bool2 +
                ", bool3=" + bool3 +
                '}';
    }
}
