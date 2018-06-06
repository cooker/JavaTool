package com.cooker.zoom.helper.utils.extend.args.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yu.kequn on 2018-05-29.
 * 字段联合校验
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface ValueUnion {
    String union();
    String[] value() default {};
    boolean isUnion() default false;
}
