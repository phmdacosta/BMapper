package com.phmc.bmapper.test.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestModel extends TestParentModel {
    private boolean bool;
    private TestChildModel child;
    private List<TestChildModel> children = new ArrayList<>();
    private Map<Integer, TestChildModel> childrenMap = new HashMap<>();
    private String abc;

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public TestChildModel getChild() {
        return child;
    }

    public void setChild(TestChildModel child) {
        this.child = child;
    }

    public List<TestChildModel> getChildren() {
        return children;
    }

    public void setChildren(List<TestChildModel> children) {
        this.children = children;
    }

    public Map<Integer, TestChildModel> getChildrenMap() {
        return childrenMap;
    }

    public void setChildrenMap(Map<Integer, TestChildModel> childrenMap) {
        this.childrenMap = childrenMap;
    }

    public String getNamePlusChildName() {
        return this.getName() + " + " +  child.getName();
    }

    public void setCompleteName(String completeName) {
        this.setName(completeName);
    }
}
