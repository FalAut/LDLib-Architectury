package com.lowdragmc.lowdraglib.gui.graphprocessor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CustomPortBehavior {
    /**
     * The field which should be handled by a custom method
     */
    String field();
}