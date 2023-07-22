package com.phmc.bmapper.xml;

public class XmlClassDocument {
    private Class<?> classA;
    private Class<?> classB;

    public XmlClassDocument(Class<?> classA, Class<?> classB) {
        this.classA = classA;
        this.classB = classB;
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
}
