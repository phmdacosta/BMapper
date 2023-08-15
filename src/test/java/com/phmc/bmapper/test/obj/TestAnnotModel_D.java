package com.phmc.bmapper.test.obj;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestAnnotModel_D implements Cloneable {

    private long id;
    private String name;
    private String route;

    private List<TestAnnotModel_C> testAnnotModelACS;

    public long getId() {
        return id;
    }

    public TestAnnotModel_D setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestAnnotModel_D setName(String name) {
        this.name = name;
        return this;
    }

    public String getRoute() {
        return route;
    }

    public TestAnnotModel_D setRoute(String route) {
        this.route = route;
        return this;
    }

    public List<TestAnnotModel_C> getRoles() {
        return testAnnotModelACS;
    }

    public TestAnnotModel_D setRoles(List<TestAnnotModel_C> testAnnotModelACS) {
        this.testAnnotModelACS = testAnnotModelACS;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_D)) return false;
        TestAnnotModel_D that = (TestAnnotModel_D) o;
        return getId() == that.getId()
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getRoute(), that.getRoute())
                && Objects.equals(getRoles(), that.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRoute(), getRoles());
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public TestAnnotModel_D clone() throws CloneNotSupportedException {
        TestAnnotModel_D clone = (TestAnnotModel_D) super.clone();
        try {
            clone.setRoles(this.getRoles().stream().map(role -> {
                try {
                    return role.clone();
                } catch (CloneNotSupportedException e) {
                    return null;
                }
            }).collect(Collectors.toList()));
        } catch (Exception ignored) {
        }
        return clone;
    }
}
