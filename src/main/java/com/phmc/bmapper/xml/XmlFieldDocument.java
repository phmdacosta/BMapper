package com.phmc.bmapper.xml;

public class XmlFieldDocument extends XmlClassDocument {

    private String fieldAName;
    private String fieldBName;

    public XmlFieldDocument(Class<?> classA, Class<?> classB, String fieldAName, String fieldBName) {
        super(classA, classB);
        this.fieldAName = fieldAName;
        this.fieldBName = fieldBName;
    }

    public String getFieldAName() {
        return fieldAName;
    }

    public void setFieldAName(String fieldAName) {
        this.fieldAName = fieldAName;
    }

    public String getFieldBName() {
        return fieldBName;
    }

    public void setFieldBName(String fieldBName) {
        this.fieldBName = fieldBName;
    }
}
