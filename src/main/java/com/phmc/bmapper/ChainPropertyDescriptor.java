package com.phmc.bmapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class serves to house the entire chain of properties up to the target property.<br><br>
 * Ex: There is a DTO with the field {@code personName}, and we want to map this field to a property called
 * {@code name} inside a {@code Person} object, which consequently is contained in our model,
 * or persistence object.<br>
 * A chain of properties will be built up to the {@code name} property on {@code Person},
 * a list containing -> {@code [ person, name ]}. <br>
 * This list will then be scanned by the mapper to find the path to the {@code name} property.
 */
public class ChainPropertyDescriptor {
    private Class<?> objClass;
    private final List<PropertyDescriptor> chain;

    public ChainPropertyDescriptor() {
        chain = new ArrayList<>();
    }

    public void setObjClass(Class<?> objClass) {
        this.objClass = objClass;
    }

    public Class<?> getObjClass() {
        return objClass;
    }

    public void add(PropertyDescriptor propertyDescriptor) {
        chain.add(propertyDescriptor);
    }

    public PropertyDescriptor get(int index) {
        return chain.get(index);
    }

    public int size() {
        return chain.size();
    }

    public boolean isEmpty() {
        return chain.isEmpty();
    }

    public boolean contains(String fieldName) {
        return chain.stream().anyMatch(elem -> elem.getName().equals(fieldName));
    }

    public boolean contains(String[] chainedFieldNames) {
        if (chainedFieldNames.length == 0) {
            return false;
        }

        if (chainedFieldNames.length == 1) {
            return this.contains(chainedFieldNames[0]);
        }

        for (String fieldName : chainedFieldNames) {
            if (!this.contains(fieldName)) {
                return false;
            }
        }

        return true;
    }

    public boolean contains(PropertyDescriptor propertyDescriptor) {
        return chain.contains(propertyDescriptor);
    }

    public boolean isSameAs(ChainPropertyDescriptor other) {
        if (other == null) {
            return false;
        }

        if (this.chain.size() != other.chain.size()) {
            return false;
        }

        for(int i = 0; i < chain.size(); i++) {
            PropertyDescriptor thisPropDesc = chain.get(i);
            PropertyDescriptor otherPropDesc = other.chain.get(i);
            if (!thisPropDesc.getName().equals(otherPropDesc.getName())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("size=");
        sb.append(size());
        if (!chain.isEmpty()) {
            sb.append(", chain=[");
            chain.forEach(value -> {
                sb.append(value);
                sb.append(" ");
            });
            sb.append("]");
        }
        sb.append(" }");
        return sb.toString();
    }
}
