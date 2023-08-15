package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;

import java.util.List;
import java.util.Objects;

@MappingClass(targetClass = TestAnnotModel_D.class)
public class TestAnnotModel_D_DtoRequest {
    private long id;
    private String name;
    private String route;
    private List<TestAnnotModel_C_DtoRequest> testAnnotModelCList;

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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<TestAnnotModel_C_DtoRequest> getTestAnnotModelCList() {
        return testAnnotModelCList;
    }

    public void setTestAnnotModelCList(List<TestAnnotModel_C_DtoRequest> testAnnotModelCList) {
        this.testAnnotModelCList = testAnnotModelCList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnnotModel_D_DtoRequest)) return false;
        TestAnnotModel_D_DtoRequest that = (TestAnnotModel_D_DtoRequest) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getRoute(), that.getRoute()) && Objects.equals(getTestAnnotModelCList(), that.getTestAnnotModelCList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRoute(), getTestAnnotModelCList());
    }

    @Override
    public String toString() {
        return "PermissionDtoRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
