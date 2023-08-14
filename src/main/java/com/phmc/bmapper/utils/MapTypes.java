package com.phmc.bmapper.utils;

import java.util.*;

public final class MapTypes {
    private static final Set<Class<?>> types = new HashSet<>(Arrays.asList(
            Map.class,
            HashMap.class,
            SortedMap.class
    ));

    public static boolean contains(Class<?> clazz) {
        return types.contains(clazz);
    }
}
