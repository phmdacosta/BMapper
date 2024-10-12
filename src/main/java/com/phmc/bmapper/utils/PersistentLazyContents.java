package com.phmc.bmapper.utils;

import com.pedrocosta.springutils.output.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class PersistentLazyContents {
    final List<String> typeNames = Arrays.asList(
            "PersistentBag",
            "HibernateProxy");

    public boolean accept(Object obj) {
        if (obj == null) {
            return false;
        }

        Class<?> objClass = obj.getClass();
        for (String typeName : typeNames) {
            try {
                Class<?> lazyTypeClass = Class.forName(typeName);
                if (objClass.isAssignableFrom(lazyTypeClass)) {
                    return true;
                }
            } catch (ClassNotFoundException e) {
                Log.warn(PersistentLazyContents.class, String.format("Project do not support %s type", typeName));
            }
        }

        return false;
    }
}
