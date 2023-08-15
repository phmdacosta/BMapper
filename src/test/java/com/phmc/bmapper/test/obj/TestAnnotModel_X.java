package com.phmc.bmapper.test.obj;

import java.time.LocalDateTime;
import java.util.Objects;

public class TestAnnotModel_X implements Cloneable {

    private long id;
    private String firstName;
    private String lastName;
    private LocalDateTime myDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getMyDate() {
        return myDate;
    }

    public void setMyDate(LocalDateTime myDate) {
        this.myDate = myDate;
    }

    @Override
    public TestAnnotModel_X clone() throws CloneNotSupportedException {
        return (TestAnnotModel_X) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_X)) return false;
        TestAnnotModel_X testAnnotModelX = (TestAnnotModel_X) o;
        return getId() == testAnnotModelX.getId()
                && Objects.equals(getFirstName(), testAnnotModelX.getFirstName())
                && Objects.equals(getLastName(), testAnnotModelX.getLastName())
                && Objects.equals(getMyDate(), testAnnotModelX.getMyDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getMyDate());
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + myDate +
                '}';
    }
}
