package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@MappingClass(targetClass = TestAnnotModel_F.class)
public class TestAnnotModel_F_Dto {
    private List<TestAnnotModel_H_Dto> unmodifiableList;
    private Set<TestAnnotModel_H_Dto> unmodifiableSet;

    public List<TestAnnotModel_H_Dto> getUnmodifiableList() {
        return unmodifiableList;
    }

    public void setUnmodifiableList(List<TestAnnotModel_H_Dto> unmodifiableList) {
        this.unmodifiableList = unmodifiableList;
    }

    public Set<TestAnnotModel_H_Dto> getUnmodifiableSet() {
        return unmodifiableSet;
    }

    public void setUnmodifiableSet(Set<TestAnnotModel_H_Dto> unmodifiableSet) {
        this.unmodifiableSet = unmodifiableSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_F_Dto)) return false;
        TestAnnotModel_F_Dto that = (TestAnnotModel_F_Dto) o;
        return Objects.equals(getUnmodifiableList(), that.getUnmodifiableList()) && Objects.equals(getUnmodifiableSet(), that.getUnmodifiableSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUnmodifiableList(), getUnmodifiableSet());
    }
}
