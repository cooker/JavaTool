package com.cooker.zoom.helper.utils.extend.args.constraints;

import com.cooker.zoom.helper.utils.extend.args.Verifier;
import com.cooker.zoom.helper.utils.extend.args.VerifierFor;

import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@VerifierFor(NotEmpty.class)
public class NotEmptyStringVerifier implements Verifier<String> {
  @Override
  public void verify(String fieldName, String s, Annotation annotation) throws IllegalArgumentException {
    checkArgument(isNotEmpty(s), fieldName + "，不能为空.");
  }

  @Override
  public String toString(Class<? extends String> argType, Annotation annotation) {
    return "must be non-empty";
  }
}
