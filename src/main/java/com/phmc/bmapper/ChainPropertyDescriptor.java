package com.phmc.bmapper;

import java.util.HashMap;
import java.util.Map;

public class ChainPropertyDescriptor {
    private Class<?> objClass;
    private final Map<Integer, PropertyDescriptor> chain;
    private int size;

    public ChainPropertyDescriptor() {
        chain = new HashMap<>();
    }

    public void setObjClass(Class<?> objClass) {
        this.objClass = objClass;
    }

    public Class<?> getObjClass() {
        return objClass;
    }

    public void add(PropertyDescriptor propertyDescriptor) {
        chain.put(++size, propertyDescriptor);
    }

    public PropertyDescriptor get(int index) {
        return chain.get(index);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return chain.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("size=");
        sb.append(size);
        if (!chain.isEmpty()) {
            sb.append(", chain=[");
            chain.forEach((key, value) -> {
                sb.append(value);
                sb.append(" ");
            });
            sb.append("]");
        }
        sb.append(" }");
        return sb.toString();
    }
}
