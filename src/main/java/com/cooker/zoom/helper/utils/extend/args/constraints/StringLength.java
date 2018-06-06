package com.cooker.zoom.helper.utils.extend.args.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yu.kequn on 2018-05-29.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface StringLength {
    int max() default 20;
    int min() default 0;
    CHECK_MODE mode() default CHECK_MODE.CHECK_ALL;
    //校验方式
    enum CHECK_MODE{
        CHECK_MAX, CHECK_MIN, CHECK_ALL
    }
}
