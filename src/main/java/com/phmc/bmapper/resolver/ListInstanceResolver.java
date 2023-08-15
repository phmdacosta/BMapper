package com.phmc.bmapper.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListInstanceResolver<T> implements CollectionInstanceResolver<List<T>> {
    @Override
    @SuppressWarnings("unchecked")
    public List<T> newInstance(Class<?> clazz) {
        List<T> obj;
        try {
            obj = (List<T>) clazz.getDeclaredConstructor(new Class[0]).newInstance();
            obj.clear();
        } catch (ClassCastException e) {
            obj = null;
        } catch (Exception e) {
            obj = new ArrayList<>();
        }

        return obj;
    }
}
