package com.phmc.bmapper.test.obj;

import java.util.Objects;

public class TestAnnotModel_B implements Cloneable {

    private long id;
    private String name;
    private String value;

    //Foreign objects
    private TestAnnotModel_A testAnnotModelA;

    public long getId() {
        return id;
    }

    public TestAnnotModel_B setId(long id) {
        this.id = id;
        return this;
    }

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

    public TestAnnotModel_A getUser() {
        return testAnnotModelA;
    }

    public void setUser(TestAnnotModel_A testAnnotModelA) {
        this.testAnnotModelA = testAnnotModelA;
    }

    @Override
    public TestAnnotModel_B clone() throws CloneNotSupportedException {
        TestAnnotModel_B testAnnotModelAB = (TestAnnotModel_B) super.clone();
        testAnnotModelAB.setUser(this.getUser().clone());
        return testAnnotModelAB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestAnnotModel_B that = (TestAnnotModel_B) o;
        return id == that.id ||
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }

    @Override
    public String toString() {
        return "UserContact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
