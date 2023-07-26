package com.phmc.bmapper.xml;

import java.util.ArrayList;
import java.util.List;

public class XmlMappingClass {
    private Class<?> classA;
    private Class<?> classB;
    private List<XmlMappingField> mappingFields;
    private boolean hasSameFields;

    public XmlMappingClass() {
        this.mappingFields = new ArrayList<>();
    }

    public Class<?> getClassA() {
        return classA;
    }

    public void setClassA(Class<?> classA) {
        this.classA = classA;
    }

    public Class<?> getClassB() {
        return classB;
    }

    public void setClassB(Class<?> classB) {
        this.classB = classB;
    }

    public List<XmlMappingField> getMappingFields() {
        return mappingFields;
    }

    public void addMappingField(XmlMappingField xmlMappingField) {
        this.mappingFields.add(xmlMappingField);
    }

    public XmlMappingField getMappingField(int index) {
        return this.mappingFields.get(index);
    }

    public int fieldsLength() {
        return this.mappingFields.size();
    }

    public boolean hasFields() {
        return !this.mappingFields.isEmpty();
    }

    public boolean hasSameFields() {
        return hasSameFields;
    }

    public void setHasSameFields(boolean hasSameFields) {
        this.hasSameFields = hasSameFields;
    }
}
