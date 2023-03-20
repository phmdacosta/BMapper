package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.annotation.MappingCollection;
import com.phmc.bmapper.annotation.MappingField;
import com.phmc.bmapper.annotation.MappingMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MappingClass(targetClass = TestModel.class)
public class TestViewAnnot {
    @MappingField(name = "name")
    private String viewName;
    private boolean bool;
    @MappingField(name = "child")
    private TestChildView childView;
    @MappingField(name = "child.name")
    private String childViewName;
    @MappingCollection(name = "children", resultElementClass = TestChildView.class)
    private List<TestChildView> childrenView = new ArrayList<>();
    @MappingMap(name = "childrenMap", resultValueClass = TestChildView.class)
    private Map<Integer, TestChildView> childrenViewMap = new HashMap<>();

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public TestChildView getChildView() {
        return childView;
    }

    @MappingField(name = "child")
    public void setChildView(TestChildView childView) {
        this.childView = childView;
    }

    public String getChildViewName() {
        return childViewName;
    }

    public void setChildViewName(String childViewName) {
        this.childViewName = childViewName;
    }

    public List<TestChildView> getChildrenView() {
        return childrenView;
    }

    public void setChildrenView(List<TestChildView> childrenView) {
        this.childrenView = childrenView;
    }

    public Map<Integer, TestChildView> getChildrenViewMap() {
        return childrenViewMap;
    }

    public void setChildrenViewMap(Map<Integer, TestChildView> childrenViewMap) {
        this.childrenViewMap = childrenViewMap;
    }
}
