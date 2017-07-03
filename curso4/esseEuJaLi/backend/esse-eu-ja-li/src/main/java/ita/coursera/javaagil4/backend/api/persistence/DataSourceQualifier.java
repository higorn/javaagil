package ita.coursera.javaagil4.backend.api.persistence;/*
 * File:   DataSourceQualifier.java
 *
 * Created on 01/07/17, 22:54
 */

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
public @interface DataSourceQualifier {
}
