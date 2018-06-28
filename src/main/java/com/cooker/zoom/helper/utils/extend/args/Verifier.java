
package com.cooker.zoom.helper.utils.extend.args;

import java.lang.annotation.Annotation;

/**
 * Typedef for a constraint verifier.
 */
public interface Verifier<T> {
  void verify(String fieldName, T value, Annotation annotation) throws IllegalArgumentException;

  String toString(Class<? extends T> argType, Annotation annotation);
}
