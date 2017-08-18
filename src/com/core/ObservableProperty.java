package com.core;

import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Documented
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.TYPE})
@org.codehaus.groovy.transform.GroovyASTTransformationClass({"groovy.beans.BindableASTTransformation"})
public @interface ObservableProperty {
}
