package com.phmc.bmapper.utils;

import java.util.*;

public final class CollectionTypes {
    private static final Set<Class<?>> types = new HashSet<>(Arrays.asList(
            Collection.class,
            Set.class,
            HashSet.class,
            SortedSet.class,
            TreeSet.class,
            List.class,
            ArrayList.class,
            LinkedList.class,
            AbstractCollection.class,
            Vector.class,
            Collections.class,
            Arrays.class
    ));

    public static boolean contains(Class<?> clazz) {
        return types.contains(clazz);
    }
}
