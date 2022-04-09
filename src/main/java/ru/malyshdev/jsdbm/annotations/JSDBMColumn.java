package ru.malyshdev.jsdbm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JSDBMColumn {
    boolean primary_key() default false;

    boolean not_null() default false;

    String column_name();
}
