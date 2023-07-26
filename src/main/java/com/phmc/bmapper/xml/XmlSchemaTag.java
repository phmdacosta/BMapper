package com.phmc.bmapper.xml;

public enum XmlSchemaTag {
    BMAPPER("bmapper"),
    MAPPING("mapping"),
    CLASS("class"),
    FIELD("field"),
    A("a"),
    B("b"),
    SAME_FIELDS("same-fields");

    private final String tag;

    XmlSchemaTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static boolean contains(String tag) {
        for (XmlSchemaTag xmlSchemaTag : XmlSchemaTag.values()) {
            if (xmlSchemaTag.getTag().equals(tag)) {
                return true;
            }
        }
        return false;
    }
}
