package com.phmc.bmapper;

import com.phmc.bmapper.resolver.CollectionInstanceResolverFactory;

import java.util.Collection;

public class CollectionMapper<FROM, TO> extends TypeMapper<FROM, TO> {

    @Override
    @SuppressWarnings("unchecked")
    protected TO map(FROM from, Class<TO> resultClass) {
        if (!(from instanceof Collection)) {
            throw new IllegalArgumentException("Source is not a Collection");
        }

        TO result = (TO) CollectionInstanceResolverFactory
                .create(from.getClass()).newInstance(from.getClass());

        if (((Collection<?>)from).isEmpty()) {
            return result;
        }

        //Do the mapping of elements
        if (result != null) {
            for (Object obj : (Collection<Object>)from) {
                ((Collection<Object>) result).add(((TypeMapper<Object,TO>)loadMapper(obj.getClass(), resultClass))
                        .doMapping(obj, resultClass));
            }
        }

        return result;
    }
}
