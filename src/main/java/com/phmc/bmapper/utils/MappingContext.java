package com.phmc.bmapper.utils;

import org.springframework.context.ApplicationContext;

public class MappingContext {
    private ApplicationContext context;
    private Package targetPackage;

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return this.context;
    }

    public void setTargetPackage(Package targetPackage) {
        this.targetPackage = targetPackage;
    }

    public Package getTargetPackage() {
        return targetPackage;
    }

    public void setMainClass(Class<?> mainClass) {
        this.targetPackage = mainClass.getPackage();
    }
}
