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

package com.cooker.zoom.helper.utils.base.quantity;

public enum Data implements Unit<Data> {
  BITS(1),
  Kb(1024, BITS),
  Mb(1024, Kb),
  Gb(1024, Mb),
  BYTES(8, BITS),
  KB(1024, BYTES),
  MB(1024, KB),
  GB(1024, MB),
  TB(1024, GB),
  PB(1024, TB);

  private final double multiplier;

  private Data(double multiplier) {
    this.multiplier = multiplier;
  }

  private Data(double multiplier, Data base) {
    this(multiplier * base.multiplier);
  }

  @Override
  public double multiplier() {
    return multiplier;
  }
}
