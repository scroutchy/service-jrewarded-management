package com.scr.project.sjrm;

import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;
import org.springframework.boot.test.context.SpringBootTest;

public class TestClassOrderer implements ClassOrderer {

    @Override
    public void orderClasses(ClassOrdererContext context) {
        context.getClassDescriptors().sort((a, b) -> Integer.compare(weight(a), weight(b)));
    }

    private int weight(ClassDescriptor descriptor) {
        if (descriptor.isAnnotated(SpringBootTest.class)) {
            return 2;
        } else {
            return 1;
        }
    }
}
