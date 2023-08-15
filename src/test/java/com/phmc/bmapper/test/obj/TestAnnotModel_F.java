package com.phmc.bmapper.test.obj;

import java.util.*;

public class TestAnnotModel_F {
    private List<TestAnnotModel_H> unmodifiableList;
    private Set<TestAnnotModel_H> unmodifiableSet;

    public TestAnnotModel_F() {
        TestAnnotModel_H testAnnotModelH_1 = new TestAnnotModel_H();
        testAnnotModelH_1.setStr("MODEL_H_STR_1");
        testAnnotModelH_1.setBool(true);
        testAnnotModelH_1.setNumber(5684D);

        TestAnnotModel_H testAnnotModelH_2 = new TestAnnotModel_H();
        testAnnotModelH_2.setStr("MODEL_H_STR_2");
        testAnnotModelH_2.setBool(true);
        testAnnotModelH_2.setNumber(5684D);

        TestAnnotModel_H testAnnotModelH_3 = new TestAnnotModel_H();
        testAnnotModelH_3.setStr("MODEL_H_STR_3");
        testAnnotModelH_3.setBool(true);
        testAnnotModelH_3.setNumber(5684D);

        List<TestAnnotModel_H> list = new ArrayList<>();
        list.add(testAnnotModelH_1);
        list.add(testAnnotModelH_2);
        list.add(testAnnotModelH_3);

        setUnmodifiableList(Collections.unmodifiableList(list));
    }

    public List<TestAnnotModel_H> getUnmodifiableList() {
        return unmodifiableList;
    }

    public void setUnmodifiableList(List<TestAnnotModel_H> unmodifiableList) {
        this.unmodifiableList = unmodifiableList;
    }

    public Set<TestAnnotModel_H> getUnmodifiableSet() {
        return unmodifiableSet;
    }

    public void setUnmodifiableSet(Set<TestAnnotModel_H> unmodifiableSet) {
        this.unmodifiableSet = unmodifiableSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_F)) return false;
        TestAnnotModel_F that = (TestAnnotModel_F) o;
        return Objects.equals(getUnmodifiableList(), that.getUnmodifiableList()) && Objects.equals(getUnmodifiableSet(), that.getUnmodifiableSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUnmodifiableList(), getUnmodifiableSet());
    }
}
