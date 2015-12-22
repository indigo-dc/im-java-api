/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.upv.i3m.grycap.im.api;

/**
 * Stores the possible VM states
 */
public enum VmStates {

  PENDING("pending"), RUNNING("running"), UNCONFIGURED("unconfigured"), CONFIGURED(
      "configured"), STOPPED("stopped"), OFF("off"), FAILED("failed"), UNKNOWN("unknown");

  private final String value;

  VmStates(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  public static VmStates getEnumFromValue(String value) {
    if (value != null) {
      for (VmStates state : VmStates.values()) {
        if (value.equalsIgnoreCase(state.value)) {
          return state;
        }
      }
    }
    return null;
  }

}
