package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;

@MappingClass(targetClass = TestChildModel.class)
public class TestChildView {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
