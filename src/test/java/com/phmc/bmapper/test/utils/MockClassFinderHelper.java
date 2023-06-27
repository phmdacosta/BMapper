package com.phmc.bmapper.test.utils;

import com.phmc.bmapper.test.obj.TestViewAnnot;
import com.phmc.bmapper.test.obj.TestViewExtAnnot;

import java.util.ArrayList;
import java.util.List;

public class MockClassFinderHelper {
    public static List<Class<?>> mockFindAllByAssignable_full() {
        List<Class<?>> mockResult = new ArrayList<>();
        mockResult.add(TestViewAnnot.class);
        mockResult.add(TestViewExtAnnot.class);
        return mockResult;
    }
}
