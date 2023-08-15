package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.annotation.MappingField;

import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_C.class)
public class TestAnnotModel_C_Dto {
    private long id;
    @MappingField(name = "name")
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_C_Dto)) return false;
        TestAnnotModel_C_Dto testAnnotModelACDto = (TestAnnotModel_C_Dto) o;
        return getId() == testAnnotModelACDto.getId() && Objects.equals(getName(), testAnnotModelACDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
