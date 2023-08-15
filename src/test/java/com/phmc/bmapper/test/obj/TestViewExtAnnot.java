package com.phmc.bmapper.test.obj;

import com.phmc.bmapper.annotation.MappingClass;
import com.phmc.bmapper.annotation.MappingCollection;
import com.phmc.bmapper.annotation.MappingField;
import com.phmc.bmapper.annotation.MappingMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MappingClass(targetClass = TestModelExt.class)
public class TestViewExtAnnot {
    @MappingField(name = "bool")
    private boolean viewBool;
    @MappingField(name = "child")
    private TestChildView childView;
    @MappingField(name = "child.name")
    private String childViewName;
    @MappingCollection(name = "children", elementType = TestChildView.class)
    private List<TestChildView> viewChildren = new ArrayList<>();
    @MappingMap(name = "childrenMap", elementValueType = TestChildView.class)
    private Map<Integer, TestChildView> viewChildrenMap = new HashMap<>();
    @MappingField(name = "strExt")
    private String viewStrExt;

    public String getViewStrExt() {
        return viewStrExt;
    }

    public void setViewStrExt(String viewStrExt) {
        this.viewStrExt = viewStrExt;
    }

    public boolean isViewBool() {
        return viewBool;
    }

    public void setViewBool(boolean viewBool) {
        this.viewBool = viewBool;
    }

    public TestChildView getChildView() {
        return childView;
    }

    public void setChildView(TestChildView childView) {
        this.childView = childView;
    }

    public String getChildViewName() {
        return childViewName;
    }

    public void setChildViewName(String childViewName) {
        this.childViewName = childViewName;
    }

    public List<TestChildView> getViewChildren() {
        return viewChildren;
    }

    public void setViewChildren(List<TestChildView> viewChildren) {
        this.viewChildren = viewChildren;
    }

    public Map<Integer, TestChildView> getViewChildrenMap() {
        return viewChildrenMap;
    }

    public void setViewChildrenMap(Map<Integer, TestChildView> viewChildrenMap) {
        this.viewChildrenMap = viewChildrenMap;
    }
}
