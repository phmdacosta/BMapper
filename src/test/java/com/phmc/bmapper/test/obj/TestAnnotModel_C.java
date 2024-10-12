package com.phmc.bmapper.test.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestAnnotModel_C implements Cloneable {

    private long id;
    private String name;

    //Foreign objects
    private List<TestAnnotModel_A> testAnnotModelAList;

    private List<TestAnnotModel_D> testAnnotModelDList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestAnnotModel_A> getTestAnnotModelAList() {
        return testAnnotModelAList;
    }

    public void setTestAnnotModelAList(List<TestAnnotModel_A> testAnnotModelAList) {
        this.testAnnotModelAList = testAnnotModelAList;
    }

    public List<TestAnnotModel_D> getTestAnnotModelDList() {
        return testAnnotModelDList;
    }

    public void setTestAnnotModelDList(List<TestAnnotModel_D> testAnnotModelDList) {
        this.testAnnotModelDList = testAnnotModelDList;
    }

    @Override
    public TestAnnotModel_C clone() throws CloneNotSupportedException {
        TestAnnotModel_C clone = (TestAnnotModel_C) super.clone();
        clone.setTestAnnotModelDList(new ArrayList<>());
        for (TestAnnotModel_D permission : this.getTestAnnotModelDList()) {
            clone.getTestAnnotModelDList().add(permission.clone());
        }
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_C)) return false;
        TestAnnotModel_C testAnnotModelAC = (TestAnnotModel_C) o;
        return getId() == testAnnotModelAC.getId()
                && Objects.equals(getName(), testAnnotModelAC.getName())
                && Objects.equals(getTestAnnotModelAList(), testAnnotModelAC.getTestAnnotModelAList())
                && Objects.equals(getTestAnnotModelDList(), testAnnotModelAC.getTestAnnotModelDList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTestAnnotModelAList(), getTestAnnotModelDList());
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
