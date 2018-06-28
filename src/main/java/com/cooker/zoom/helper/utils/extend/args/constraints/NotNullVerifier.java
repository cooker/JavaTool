// =================================================================================================
// Copyright 2011 Twitter, Inc.
// -------------------------------------------------------------------------------------------------
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this work except in compliance with the License.
// You may obtain a copy of the License in the LICENSE file, or at:
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// =================================================================================================

package com.cooker.zoom.helper.utils.extend.args.constraints;

import com.cooker.zoom.helper.utils.extend.args.Verifier;
import com.cooker.zoom.helper.utils.extend.args.VerifierFor;

import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

@VerifierFor(NotNull.class)
public class NotNullVerifier implements Verifier<Object> {

  @Override
  public void verify(String fieldName, Object value, Annotation annotation) throws IllegalArgumentException {
    checkArgument(!isNull(value), fieldName + "，不能为Null");
  }

  @Override
  public String toString(Class<?> argType, Annotation annotation) {
    return "not null";
  }

}
