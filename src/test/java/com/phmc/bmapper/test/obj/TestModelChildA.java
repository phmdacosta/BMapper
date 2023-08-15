package com.phmc.bmapper.test.obj;

import java.util.Objects;

public class TestModelChildA implements Cloneable {

    private long id;
    private String name;
    private String value;

    //Foreign objects
    private TestAnnotModel_A testAnnotModelA;

    public long getId() {
        return id;
    }

    public TestModelChildA setId(long id) {
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
    public TestModelChildA clone() throws CloneNotSupportedException {
        TestModelChildA userContact = (TestModelChildA) super.clone();
        userContact.setUser(this.getUser().clone());
        return userContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestModelChildA that = (TestModelChildA) o;
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
