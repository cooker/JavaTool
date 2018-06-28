package com.cooker.zoom.helper.utils.extend.args;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Annotation to register a command line argument verifier.
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface VerifierFor {
  Class<? extends Annotation> value();
}
