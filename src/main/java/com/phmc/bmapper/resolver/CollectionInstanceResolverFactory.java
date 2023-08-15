package com.phmc.bmapper.resolver;

import com.pedrocosta.springutils.output.Log;
import com.phmc.bmapper.BMapper;
import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.collections.set.UnmodifiableSet;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CollectionInstanceResolverFactory {

    public static CollectionInstanceResolver<?> create(Object obj) throws InstantiationException {
        InstanceResolverType type = InstanceResolverType.get(obj);
        try {
            return type.getResolverClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InstantiationException(e.getMessage());
        }
    }

    public static CollectionInstanceResolver<?> create(Class<?> clazz) throws InstantiationException {
        InstanceResolverType type = InstanceResolverType.get(clazz);
        try {
            return type.getResolverClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InstantiationException(e.getMessage());
        }
    }

    @SuppressWarnings("rawtypes")
    private enum InstanceResolverType {
        DEFAULT(Object.class, DefaultInstanceResolver.class),
        SET(Set.class, SetInstanceResolver.class),
        LIST(List.class, ListInstanceResolver.class),
        UNMODIFIABLE_SET(UnmodifiableSet.class, SetInstanceResolver.class),
        UNMODIFIABLE_LIST(UnmodifiableList.class, ListInstanceResolver.class);

        private final Class<?> clazz;
        private final Class<? extends CollectionInstanceResolver> resolverClass;

        private InstanceResolverType(Class<?> clazz, Class<? extends CollectionInstanceResolver> resolverClass) {
            this.clazz = clazz;
            this.resolverClass = resolverClass;
        }

        public static InstanceResolverType get(Class<?> clazz) {
            return Arrays.stream(InstanceResolverType.values())
                    .filter(type ->
                            !type.equals(DEFAULT)
                                    && (Arrays.stream(clazz.getInterfaces()).anyMatch(interfaceClass -> interfaceClass.isAssignableFrom(type.clazz))
                                    || clazz.getSuperclass().isAssignableFrom(type.clazz)))
                    .findAny().orElse(DEFAULT);
        }

        public static InstanceResolverType get(Object obj) {
            for (InstanceResolverType type : InstanceResolverType.values()) {
                if (type.equals(DEFAULT)) {
                    continue;
                }

                try {
                    type.clazz.cast(obj);
                    return type;
                } catch (ClassCastException ignored) {
                }
            }
            return DEFAULT;
        }

        public Class<? extends CollectionInstanceResolver> getResolverClass() {
            return resolverClass;
        }
    }
}
